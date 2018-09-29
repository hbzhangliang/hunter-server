package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.TBizProjectAccountRefService;
import cn.com.cubic.platform.hunter.mysql.services.TBizProjectService;
import cn.com.cubic.platform.hunter.mysql.services.TBizShareProjectService;
import cn.com.cubic.platform.hunter.mysql.vo.CompanyVo;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.hunter.mysql.vo.ProjectVo;
import cn.com.cubic.platform.utils.ComEnum;
import cn.com.cubic.platform.utils.ComServers;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.UtilHelper;
import cn.com.cubic.platform.utils.global.GlobalHolder;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/9/28.
 **/
@Service
public class TBizProjectServiceImpl extends BaseServiceImpl<TBizProject,TBizProjectExample> implements TBizProjectService {

    private final static Logger log = LoggerFactory.getLogger(TBizProjectServiceImpl.class);


    /**
     * 参数  qu全部在一起处理
     * @param map
     * @param criteria
     * @return
     * @throws Exception
     */
    private TBizProjectExample.Criteria constructCriteria(Map<String,Object> map, TBizProjectExample.Criteria criteria) throws Exception{
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
                            case "Long":criteria= (TBizProjectExample.Criteria)item.invoke(criteria,Long.valueOf(value.toString()));break;
                            case "Integer":criteria= (TBizProjectExample.Criteria)item.invoke(criteria,Integer.valueOf(value.toString()));break;
                            case "Boolean":criteria=(TBizProjectExample.Criteria)item.invoke(criteria,Boolean.valueOf(value.toString()));break;
                            default:criteria= (TBizProjectExample.Criteria)item.invoke(criteria,value.toString());break;
                        }
                    }
                }
            }
            else if(key.startsWith("lk_")){
                String tmp=key.substring(3);
                tmp=String.format("and%sLike",tmp);
                for(Method item:methods){
                    if(tmp.equalsIgnoreCase(item.getName())){
                        criteria= (TBizProjectExample.Criteria)item.invoke(criteria,"%"+value+"%");
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
                        criteria = (TBizProjectExample.Criteria) item.invoke(criteria, UtilHelper.parseDateYMD(value.toString()));
                    }
                }
            }
            else {
                continue;
            }
        }
        return criteria;
    }


    /**
     * flag ==true  只能查自己的，否则全部查询
     * @param map
     * @param ownerFlag
     * @return
     */
    private TBizProjectExample construct(Map<String,Object> map,Boolean ownerFlag) {
        try {
            TBizProjectExample example = new TBizProjectExample();
            TBizProjectExample.Criteria criteria = example.createCriteria();
            criteria = this.constructCriteria(map, criteria);
            if(ownerFlag){
                //只能看到自己的
                TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
                criteria.andOwnerEqualTo(user.getId());
            }
            return example;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new HunterException("查询错误");
        }
    }


    @Override
    public PageParams<TBizProject> list(PageParams<TBizProject> pageParams, Boolean ownerFlag) {
        //查询参数
        TBizProjectExample example=this.construct(pageParams.getParams(),ownerFlag);
        //排序
        String strOrder=String.format("%s %s", UtilHelper.camelToUnderline(pageParams.getOrderBy()),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public List<TBizProject> listAll() {
        return null;
    }

    @Override
    public TBizProject findById(Long id) {
        TBizProjectExample example = new TBizProjectExample();
        example.createCriteria().andIdEqualTo(id);
        List<TBizProject> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw null;
        }
        return list.get(0);
    }

    @Override
    public Boolean fakeDel(List<Long> ids) {
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        TBizProjectExample example = new TBizProjectExample();
        example.createCriteria().andIdIn(ids).andOwnerEqualTo(user.getId());
        TBizProject bean=new TBizProject();
        bean.setDelStatus(ComEnum.TalentDelStatus.Faked.toString());
        this.updateByExampleSelective(bean,example);
        return true;
    }

    @Override
    public Boolean adminDel(List<Long> ids) {
        TBizProjectExample example = new TBizProjectExample();
        example.createCriteria().andIdIn(ids);
        TBizProject bean=new TBizProject();
        bean.setDelStatus(ComEnum.TalentDelStatus.Deleted.toString());
        this.updateByExampleSelective(bean,example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(ProjectVo bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        Boolean delFalg=false;
        if (null != bean.getId()) {
            TBizProjectExample example = new TBizProjectExample();
            example.createCriteria().andIdEqualTo(bean.getId());
            bean.setModifyBy(user.getName());
            bean.setModifyTime(dt);
            this.updateByExampleSelective(bean, example);

            //删除共享数据
            delFalg=true;
        } else {
            bean.setOwner(user.getId());
            bean.setDelStatus(ComEnum.TalentDelStatus.Normal.toString());
            bean.setCreateBy(user.getName());
            bean.setCreateTime(dt);
            this.insert(bean);
        }
        this.createProjectRecordTx(bean,delFalg);
        return true;
    }


    private void createProjectRecordTx(ProjectVo bean, Boolean delFlag){
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if(delFlag) {
                    String sql = String.format("delete from t_biz_share_project where project_id=%s", bean.getId());
                    jdbcTemplate.batchUpdate(sql);
                }
                if(null!=bean.getShareProjectList()) {
                    for (TBizShareProject item : bean.getShareProjectList()) {
                        item.setProjectId(bean.getId());
                        shareProjectService.insert(item);
                    }
                }

                //对 t_biz_share_talent 表的操作
                List<Long> accountIds= shareProjectService.getAccountsByProjectId(bean.getId(),bean.getShareProjectList());
                bizProjectAccountRefService.updateShareData(accountIds,bean.getId());
            }
        });
    }


    @Override
    public ProjectVo findVoById(Long id) {
        TBizProject project=this.findById(id);
        ProjectVo vo= JSONObject.parseObject(JSONObject.toJSONString(project),ProjectVo.class);
        if(null!=vo.getCity()){
            vo.setTmpCityId(comServers.getSplitIds(vo.getCity()));
            vo.setTmpCityName(comServers.getCityNames(vo.getTmpCityId()));
        }
        if(null!=vo.getPosition()){
            vo.setTmpCareerId(comServers.getSplitIds(vo.getPosition()));
            vo.setTmpCareerName(comServers.getCareerNames(vo.getTmpCareerId()));
        }
        TBizShareProjectExample example=new TBizShareProjectExample();
        example.createCriteria().andProjectIdEqualTo(id);
        vo.setShareProjectList(shareProjectService.selectByExample(example));
        return vo;
    }

    @Autowired
    private ComServers comServers;

    @Autowired
    private TBizProjectAccountRefService bizProjectAccountRefService;

    @Autowired
    private TBizShareProjectService shareProjectService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private JdbcTemplate jdbcTemplate;

}
