package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.SysAccountService;
import cn.com.cubic.platform.hunter.mysql.services.TBizDocService;
import cn.com.cubic.platform.hunter.mysql.services.TSysPositionService;
import cn.com.cubic.platform.hunter.mysql.services.TSysTeamService;
import cn.com.cubic.platform.hunter.mysql.vo.ElTreeVo;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.hunter.mysql.vo.SelTreeVo;
import cn.com.cubic.platform.utils.ComEnum;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.UtilHelper;
import cn.com.cubic.platform.utils.global.GlobalHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        String strOrder=String.format("%s %s",pageParams.getOrderBy(),pageParams.getDirection());
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
        SelTreeVo accountSelTree=new SelTreeVo(ComEnum.ShareType.account.toString(),ComEnum.ShareType.account.getDesc(),new ArrayList<>(10));
        for(TSysAccount item:accountService.listAll()){
            accountSelTree.getChildren().add(new SelTreeVo(item.getId().toString(),item.getName(),null));
        }

        //职位
        SelTreeVo positionSelTree=new SelTreeVo(ComEnum.ShareType.position.toString(),ComEnum.ShareType.position.getDesc(),new ArrayList<>(10));
        for(TSysPosition item:positionService.listAll()){
            positionSelTree.getChildren().add(new SelTreeVo(item.getId().toString(),item.getName(),null));
        }

        //团队
        SelTreeVo teamSelTree=new SelTreeVo(ComEnum.ShareType.team.toString(),ComEnum.ShareType.team.getDesc(),new ArrayList<>(10));
        for(ElTreeVo item:teamService.tree()){
            teamSelTree.getChildren().add(this.transFor(item));
        }

        //所有人
        SelTreeVo allSelTree=new SelTreeVo(ComEnum.ShareType.all.toString(),ComEnum.ShareType.all.getDesc(),null);

        result.add(accountSelTree);
        result.add(positionSelTree);
        result.add(teamSelTree);
        result.add(allSelTree);
        return result;
    }



    private SelTreeVo transFor(ElTreeVo treeVo){
        if(null==treeVo) return null;

        if(treeVo.getChildren()==null){
            return new SelTreeVo(treeVo.getId().toString(),treeVo.getName(),null);
        }
        else {
           SelTreeVo vo=new SelTreeVo(treeVo.getId().toString(),treeVo.getName(),new ArrayList<>(10));
           for(ElTreeVo item:treeVo.getChildren()){
               SelTreeVo tmp=this.transFor(item);
               if(null!=tmp){
                   vo.getChildren().add(tmp);
               }
           }
           return vo;
        }
    }


    @Autowired
    private SysAccountService accountService;

    @Autowired
    private TSysPositionService positionService;

    @Autowired
    private TSysTeamService teamService;
}
