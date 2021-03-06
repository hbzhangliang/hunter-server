package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.*;
import cn.com.cubic.platform.hunter.mysql.vo.*;
import cn.com.cubic.platform.utils.ComEnum;
import cn.com.cubic.platform.utils.ComTrans;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.UtilHelper;
import cn.com.cubic.platform.utils.global.GlobalHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Liang.Zhang on 2018/8/31.
 **/
@Service
public class TBizDocServiceImpl extends BaseServiceImpl<TBizDoc,TBizDocExample> implements TBizDocService {

    private final static Logger log = LoggerFactory.getLogger(TBizDocServiceImpl.class);

    /**
     * 参数  qu全部在一起处理
     * @param map
     * @param criteria
     * @return
     * @throws Exception
     */
    private TBizDocExample.Criteria constructCriteria(Map<String,Object> map,TBizDocExample.Criteria criteria) throws Exception{
        Class clz=criteria.getClass();
        Method[] methods=clz.getMethods();
        for(Map.Entry<String,Object> entity:map.entrySet()){
            String key=entity.getKey();
            Object value=entity.getValue();
            if(value==null|| StringUtils.isBlank(value.toString())) continue;
            if(key.startsWith("eq_")){
                String tmp=key.substring(3);
                tmp=String.format("and%sEqualto",tmp);
                for(Method item:methods){
                    if(tmp.equalsIgnoreCase(item.getName())){
                        //参数类型 ,都只有一个参数
                        Class<?> cl[]=item.getParameterTypes();
                        String parasType=cl[0].getSimpleName();
                        switch (parasType){
                            case "Long":criteria= (TBizDocExample.Criteria)item.invoke(criteria,Long.valueOf(value.toString()));break;
                            case "Integer":criteria= (TBizDocExample.Criteria)item.invoke(criteria,Integer.valueOf(value.toString()));break;
                            default:criteria= (TBizDocExample.Criteria)item.invoke(criteria,value.toString());break;
                        }
                    }
                }
            }
            else if(key.startsWith("lk_")){
                String tmp=key.substring(3);
                tmp=String.format("and%sLike",tmp);
                for(Method item:methods){
                    if(tmp.equalsIgnoreCase(item.getName())){
                        criteria= (TBizDocExample.Criteria)item.invoke(criteria,"%"+value+"%");
                    }
                }
            }
            else if(key.startsWith("bt_")){
                String tmp=key.substring(3);
                if(key.endsWith("1")){
                    tmp=tmp.substring(0,tmp.length()-1);
                    tmp=String.format("and%sGreaterThanOrEqualTo",tmp);
                }
                else {
                    tmp=tmp.substring(0,tmp.length()-1);
                    tmp=String.format("and%sLessThan",tmp);
                }

                for(Method item:methods){
                    if(tmp.equalsIgnoreCase(item.getName())) {
                        criteria = (TBizDocExample.Criteria) item.invoke(criteria, UtilHelper.parseDateYMD(value.toString()));
                    }
                }
            }
            else {
                continue;
            }
        }
        return criteria;
    }

    private TBizDocExample construct(Map<String,Object> map) {
        try {
            TBizDocExample example = new TBizDocExample();
            TBizDocExample.Criteria criteria = example.createCriteria();
            criteria = this.constructCriteria(map, criteria);
            return example;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new HunterException("查询错误");
        }
    }

    @Override
    public PageParams<TBizDoc> list(PageParams<TBizDoc> pageParams) {
        //查询参数
        TBizDocExample example=this.construct(pageParams.getParams());
        //排序
        String strOrder=String.format("%s %s",UtilHelper.camelToUnderline(pageParams.getOrderBy()),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public List<TBizDoc> listAll() {
        TBizDocExample example=new TBizDocExample();
        example.setOrderByClause("id desc");
        return this.selectByExample(example);
    }

    @Override
    public TBizDoc findById(Long id) {
        TBizDocExample example = new TBizDocExample();
        example.createCriteria().andIdEqualTo(id);
        List<TBizDoc> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }


    @Override
    public DocVo findDocVo(Long id) {
        TBizShareDocExample example=new TBizShareDocExample();
        example.createCriteria().andDocIdEqualTo(id);
        List<TBizShareDoc> docList=shareDocService.selectByExample(example);
        List<Map<String,String>> share=new ArrayList<>(5);
        if(null!=docList&&!docList.isEmpty()){
            for(TBizShareDoc doc:docList){
                Map<String,String> tmp=new HashMap<>(2);
                tmp.put("value",doc.getShareValue());
                tmp.put("label",doc.getShareLabel());
                share.add(tmp);
            }
        }
        return ComTrans.transDocVo(this.findById(id),share);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TBizDocExample example = new TBizDocExample();
        example.createCriteria().andIdIn(ids);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TBizDoc bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {
            TBizDocExample example = new TBizDocExample();
            example.createCriteria().andIdEqualTo(bean.getId());
            bean.setModifyBy(user.getName());
            bean.setModifyTime(dt);
            this.updateByExampleSelective(bean, example);
        } else {
            bean.setCreateBy(user.getName());
            bean.setCreateTime(dt);
            this.insert(bean);
        }
        return true;
    }


    /**
     * 包含权限数据的保存
     * @param bean
     * @return
     */
    @Override
    public Boolean saveTx(DocVo bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {//更新操作
            TBizDocExample example = new TBizDocExample();
            example.createCriteria().andIdEqualTo(bean.getId());
            bean.setModifyBy(user.getName());
            bean.setModifyTime(dt);
            this.updateByExampleSelective(bean, example);

            String sql=String.format("delete from t_biz_share_doc where doc_id=%s",bean.getId());
            jdbcTemplate.update(sql);
        } else { //插入操作
            bean.setCreateBy(user.getName());
            bean.setCreateTime(dt);
            this.insert(bean);
        }
        //权限添加
        if(null!=bean.getShare()&&bean.getShare().size()>0){
            for(Map<String,String> map:bean.getShare()){
                TBizShareDoc p=new TBizShareDoc();
                String vlue=map.get("value");
                p.setDocId(bean.getId());
                p.setShareType(vlue.split(",")[0]);
                //不是all的时候才有值
                if(!vlue.startsWith(ComEnum.ShareType.all.toString())) {
                    p.setCode(Long.valueOf(vlue.substring(vlue.lastIndexOf(",") + 1)));
                }
                p.setShareValue(vlue);
                p.setShareLabel(map.get("label"));
                p.setCreateBy(user.getName());
                p.setCreateTime(dt);
                shareDocService.insert(p);
            }
        }
        return true;
    }

    /**
     * 如果 没传参数，没有数据
     * @param types
     * @return
     */
    @Override
    public List<ElTreeVo> tree(List<String> types) {
        TBizDocExample example=new TBizDocExample();
        if(null!=types&&types.size()>0){
            example.createCriteria().andTypeIn(types);
        }
        else {
            example.createCriteria().andIdIsNull();
        }
        example.setOrderByClause("id desc");
        List<TBizDoc> list = this.selectByExample(example);
        if (null != list && !list.isEmpty()) {
            List<ElTreeVo> treeVos = new ArrayList<>(10);
            for (TBizDoc item : list) {
                treeVos.add(new ElTreeVo(item.getId(), item.getName(), null));
            }
            return treeVos;
        }
        return null;
    }


    @Override
    public List<SelTreeVo> allTree() {
        List<SelTreeVo> result=new ArrayList<>(5);

        //个人  没有从属关系
        SelTreeVo accountSelTree=new SelTreeVo(ComEnum.ShareType.account.toString(),ComEnum.ShareType.account.getDesc(),0,new ArrayList<>(10));
        for(TSysAccount item:accountService.listAll()){
            accountSelTree.getChildren().add(new SelTreeVo(item.getId().toString(),item.getName(),1,null));
        }

        //职位
        SelTreeVo positionSelTree=new SelTreeVo(ComEnum.ShareType.position.toString(),ComEnum.ShareType.position.getDesc(),0,new ArrayList<>(10));
        for(TSysPosition item:positionService.listAll()){
            positionSelTree.getChildren().add(new SelTreeVo(item.getId().toString(),item.getName(),1,null));
        }

        //团队
        SelTreeVo teamSelTree=new SelTreeVo(ComEnum.ShareType.team.toString(),ComEnum.ShareType.team.getDesc(),0,new ArrayList<>(10));
        for(ElTreeVo item:teamService.tree()){
            teamSelTree.getChildren().add(this.transFor(item,1));
        }

        //所有人
        SelTreeVo allSelTree=new SelTreeVo(ComEnum.ShareType.all.toString(),ComEnum.ShareType.all.getDesc(),0,new ArrayList<>(1));
        allSelTree.getChildren().add(new SelTreeVo(ComEnum.ShareType.all.toString(),ComEnum.ShareType.all.getDesc(),1,null));

        result.add(accountSelTree);
        result.add(positionSelTree);
        result.add(teamSelTree);
        result.add(allSelTree);
        return result;
    }



    private SelTreeVo transFor(ElTreeVo treeVo,int level){
        if(null==treeVo) return null;

        if(treeVo.getChildren()==null){
            return new SelTreeVo(treeVo.getId().toString(),treeVo.getName(),level,null);
        }
        else {
           SelTreeVo vo=new SelTreeVo(treeVo.getId().toString(),treeVo.getName(),level,new ArrayList<>(10));
           for(ElTreeVo item:treeVo.getChildren()){
               SelTreeVo tmp=this.transFor(item,level+1);
               if(null!=tmp){
                   vo.getChildren().add(tmp);
               }
           }
           return vo;
        }
    }



    private SelTreeVo transForT(ElTreeVo treeVo,int level){
        if(null==treeVo) return null;

        if(treeVo.getChildren()==null){
            return new SelTreeVo(ComEnum.ShareType.team.toString()+treeVo.getId().toString(),treeVo.getName(),level,null);
        }
        else {
            SelTreeVo vo=new SelTreeVo(ComEnum.ShareType.team.toString()+treeVo.getId().toString(),treeVo.getName(),level,new ArrayList<>(10));
            for(ElTreeVo item:treeVo.getChildren()){
                SelTreeVo tmp=this.transForT(item,level+1);
                if(null!=tmp){
                    vo.getChildren().add(tmp);
                }
            }
            return vo;
        }
    }


    /**
     * 避免id重复 供el tree使用
     * 避免id重复 供el tree使用
     * @return
     */
    @Override
    public List<SelTreeVo> allShareTree() {
        List<SelTreeVo> result=new ArrayList<>(5);

        //个人  没有从属关系
        SelTreeVo accountSelTree=new SelTreeVo(ComEnum.ShareType.account.toString(),ComEnum.ShareType.account.getDesc(),0,new ArrayList<>(10));
        for(TSysAccount item:accountService.listAll()){
            accountSelTree.getChildren().add(new SelTreeVo(ComEnum.ShareType.account.toString()+item.getId().toString(),item.getName(),1,null));
        }

        //职位
        SelTreeVo positionSelTree=new SelTreeVo(ComEnum.ShareType.position.toString(),ComEnum.ShareType.position.getDesc(),0,new ArrayList<>(10));
        for(TSysPosition item:positionService.listAll()){
            positionSelTree.getChildren().add(new SelTreeVo(ComEnum.ShareType.position.toString()+item.getId().toString(),item.getName(),1,null));
        }

        //团队
        SelTreeVo teamSelTree=new SelTreeVo(ComEnum.ShareType.team.toString(),ComEnum.ShareType.team.getDesc(),0,new ArrayList<>(10));
        for(ElTreeVo item:teamService.tree()){
            teamSelTree.getChildren().add(this.transForT(item,1));
        }

        //所有人
        SelTreeVo allSelTree=new SelTreeVo(ComEnum.ShareType.all.toString(),ComEnum.ShareType.all.getDesc(),0,new ArrayList<>(1));
        allSelTree.getChildren().add(new SelTreeVo(ComEnum.ShareType.all.toString()+ComEnum.ShareType.all.toString(),ComEnum.ShareType.all.getDesc(),1,null));

        result.add(accountSelTree);
        result.add(positionSelTree);
        result.add(teamSelTree);
        result.add(allSelTree);
        return result;
    }


    @Override
    public List<TBizDoc> docListByOwner(Long ownerId) {
        TBizDocExample example=new TBizDocExample();
        //类型是人，并且owner是自己
        example.createCriteria().andShareTypeEqualTo(false).andOwnerEqualTo(ownerId);
        return this.selectByExample(example);
    }


    @Override
    public List<TBizDoc> docListByOwner(Long ownerId, String type) {
        TBizDocExample example=new TBizDocExample();
        //类型是人，并且owner是自己
        example.createCriteria().andShareTypeEqualTo(false).andTypeEqualTo(type).andOwnerEqualTo(ownerId);
        return this.selectByExample(example);
    }

    @Override
    public List<TBizDoc> docListByShare(Long accountId) {
        TSysAccount account=accountService.findById(accountId);
        TBizDocExample example=new TBizDocExample();
        example.createCriteria().andShareTypeEqualTo(true);
        List<TBizDoc> list=this.selectByExample(example);
        List<TBizDoc> result=new ArrayList<>(10);
        if(list!=null&&list.size()>0){
            for(TBizDoc item:list){
                if (this.checkShare(item, account)) {
                    result.add(item);
                }
            }
        }
        return result;
    }


    @Override
    public List<TBizDoc> docListByShare(Long accountId, String type) {
        TSysAccount account=accountService.findById(accountId);
        TBizDocExample example=new TBizDocExample();
        example.createCriteria().andShareTypeEqualTo(true).andTypeEqualTo(type);
        List<TBizDoc> list=this.selectByExample(example);
        List<TBizDoc> result=new ArrayList<>(10);
        if(list!=null&&list.size()>0){
            for(TBizDoc item:list){
                if (this.checkShare(item, account)) {
                    result.add(item);
                }
            }
        }
        return result;
    }

    private Boolean checkShare(TBizDoc doc, TSysAccount account){
        TBizShareDocExample example=new TBizShareDocExample();
        example.createCriteria().andDocIdEqualTo(doc.getId());
        List<TBizShareDoc> list=shareDocService.selectByExample(example);
        Boolean flag=false;
        if(list!=null&&list.size()>0){
            for(TBizShareDoc item:list){
                  //如果是所有类型
                if(item.getShareType().equals(ComEnum.ShareType.all.toString())){
                    flag=true;
                    break;
                }
                if(item.getShareType().equals(ComEnum.ShareType.account.toString())&&item.getCode().equals(account.getId())){
                    flag=true;
                    break;
                }
                if(item.getShareType().equals(ComEnum.ShareType.position.toString())&&null!=account.getPositionId()&&account.getPositionId().equals(item.getCode())){
                    flag=true;
                    break;
                }
                if(item.getShareType().equals(ComEnum.ShareType.team.toString())&&null!=account.getTeamId()&&account.getTeamId().equals(item.getCode())){
                    flag=true;
                    break;
                }
            }
        }
        return flag;
    }


    @Override
    public MenuVo listMenu(Long accountId) {
        List<TBizDoc> selfList=this.docListByOwner(accountId);
        List<TBizDoc> shareList=this.docListByShare(accountId);

        //我的文件夹
        MenuVo myMenuVo=new MenuVo("2-1","el-icon-menu","我的文件夹",new ArrayList<>(10));
        if(selfList!=null&&selfList.size()>0){
            for(TBizDoc item:selfList){

            }
        }

        return null;

    }


    @Override
    public List<ElTreeVo> listDocByAccountId(Long accountId,String type) {
        List<TBizDoc> selfList=this.docListByOwner(accountId,type);
        List<TBizDoc> shareList=this.docListByShare(accountId,type);

        List<ElTreeVo> selfEltreeList=new ArrayList<>(5);
        if(selfList!=null&&selfList.size()>0){
            for(TBizDoc item:selfList){
                selfEltreeList.add(new ElTreeVo(item.getId(),item.getName(),null));
            }
        }
        ElTreeVo self=new ElTreeVo(100000L,"我的文档",selfEltreeList);


        List<ElTreeVo> shareEltreeList=new ArrayList<>(5);
        if(shareList!=null&&shareList.size()>0){
            for(TBizDoc item:shareList){
                shareEltreeList.add(new ElTreeVo(item.getId(),item.getName(),null));
            }
        }
        ElTreeVo share=new ElTreeVo(200000L,"共享的目录",shareEltreeList);

        List<ElTreeVo> result=new ArrayList<>(2);
        result.add(self);
        result.add(share);
        return result;
    }

    @Autowired
    private TBizShareDocService shareDocService;

    @Autowired
    private SysAccountService accountService;

    @Autowired
    private TSysPositionService positionService;

    @Autowired
    private TSysTeamService teamService;

    @Autowired
    private JdbcTemplate jdbcTemplate;
}
