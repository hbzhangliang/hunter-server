package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.*;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.hunter.mysql.vo.TalentVo;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.UtilHelper;
import cn.com.cubic.platform.utils.global.GlobalHolder;
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
 * Created by Liang.Zhang on 2018/9/5.
 **/
@Service
public class TBizTalentServiceImpl extends BaseServiceImpl<TBizTalent,TBizTalentExample> implements TBizTalentService {

    private final static Logger log = LoggerFactory.getLogger(TBizTalentServiceImpl.class);


    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;



    /**
     * 参数  qu全部在一起处理
     * @param map
     * @param criteria
     * @return
     * @throws Exception
     */
    private TBizTalentExample.Criteria constructCriteria(Map<String,Object> map, TBizTalentExample.Criteria criteria) throws Exception{
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
                            case "Long":criteria= (TBizTalentExample.Criteria)item.invoke(criteria,Long.valueOf(value.toString()));break;
                            case "Integer":criteria= (TBizTalentExample.Criteria)item.invoke(criteria,Integer.valueOf(value.toString()));break;
                            default:criteria= (TBizTalentExample.Criteria)item.invoke(criteria,value.toString());break;
                        }
                    }
                }
            }
            else if(key.startsWith("lk_")){
                String tmp=key.substring(3);
                tmp=String.format("and%sLike",tmp);
                for(Method item:methods){
                    if(tmp.equalsIgnoreCase(item.getName())){
                        criteria= (TBizTalentExample.Criteria)item.invoke(criteria,"%"+value+"%");
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
                        criteria = (TBizTalentExample.Criteria) item.invoke(criteria, UtilHelper.parseDateYMD(value.toString()));
                    }
                }
            }
            else {
                continue;
            }
        }
        return criteria;
    }


    private TBizTalentExample construct(Map<String,Object> map) {
        try {
            TBizTalentExample example = new TBizTalentExample();
            TBizTalentExample.Criteria criteria = example.createCriteria();
            criteria = this.constructCriteria(map, criteria);
            return example;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new HunterException("查询错误");
        }
    }

    @Override
    public PageParams<TBizTalent> list(PageParams<TBizTalent> pageParams) {
        //查询参数
        TBizTalentExample example=this.construct(pageParams.getParams());
        //排序
        String strOrder=String.format("%s %s",UtilHelper.camelToUnderline(pageParams.getOrderBy()),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public List<TBizTalent> listAll() {
        TBizTalentExample example=new TBizTalentExample();
        example.setOrderByClause("seq is null,seq");
        return this.selectByExample(example);
    }

    @Override
    public TBizTalent findById(Long id) {
        TBizTalentExample example = new TBizTalentExample();
        example.createCriteria().andIdEqualTo(id);
        List<TBizTalent> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TBizTalentExample example = new TBizTalentExample();
        example.createCriteria().andIdIn(ids);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TalentVo bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        Boolean delFalg=false;
        if (null != bean.getId()) {
            TBizTalentExample example = new TBizTalentExample();
            example.createCriteria().andIdEqualTo(bean.getId());
            bean.setModifyBy(user.getName());
            bean.setModifyTime(dt);
            this.updateByExampleSelective(bean, example);

            //删除共享数据
            delFalg=true;
        } else {
            bean.setCreateBy(user.getName());
            bean.setCreateTime(dt);
            this.insert(bean);
        }
        this.createTalentRecordTx(bean,delFalg);
        return true;
    }


    /**
     * 共享数据添加，  工作经历，教育经历，学校经历，项目经历 添加
     * @param bean
     */
    private void createTalentRecordTx(TalentVo bean,Boolean delFlag){
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if(delFlag) {
                    //首先删除数据
                    String[] sqls = new String[5];
                    sqls[0] = String.format("delete from t_biz_share_talent where talent_id=%s", bean.getId());
                    sqls[1] = String.format("delete from t_biz_record_work where talent_id=%s", bean.getId());
                    sqls[2] = String.format("delete from t_biz_record_project where talent_id=%s", bean.getId());
                    sqls[3] = String.format("delete from t_biz_record_language where talent_id=%s", bean.getId());
                    sqls[4] = String.format("delete from t_biz_record_educate where talent_id=%s", bean.getId());
                    jdbcTemplate.batchUpdate(sqls);
                }
                if(null!=bean.getShareTalentList()) {
                    for (TBizShareTalent item : bean.getShareTalentList()) {
                        shareTalentService.saveOrUpdate(item);
                    }
                }
                if(null!=bean.getRecordWorkList()) {
                    for (TBizRecordWork item : bean.getRecordWorkList()) {
                        recordWorkService.saveOrUpdate(item);
                    }
                }
                if(null!=bean.getRecordProjectList()) {
                    for (TBizRecordProject item : bean.getRecordProjectList()) {
                        recordProjectService.saveOrUpdate(item);
                    }
                }
                if(null!=bean.getRecordEducationList()) {
                    for (TBizRecordEducation item : bean.getRecordEducationList()) {
                        recordEducationService.saveOrUpdate(item);
                    }
                }
                if(null!=bean.getRecordLanguageList()) {
                    for (TBizRecordLanguage item : bean.getRecordLanguageList()) {
                        recordLanguageService.saveOrUpdate(item);
                    }
                }
            }
        });
    }


    @Override
    public TalentVo findVoById(Long id) {
        TalentVo vo=(TalentVo) this.findById(id);
        TBizShareTalentExample example1=new TBizShareTalentExample();
        example1.createCriteria().andTalentIdEqualTo(id);
        example1.setOrderByClause("seq");
        vo.setShareTalentList(shareTalentService.selectByExample(example1));
        TBizRecordWorkExample example2=new TBizRecordWorkExample();
        example2.createCriteria().andIdEqualTo(id);
        example2.setOrderByClause("seq");
        vo.setRecordWorkList(recordWorkService.selectByExample(example2));
        TBizRecordProjectExample example3=new TBizRecordProjectExample();
        example3.createCriteria().andIdEqualTo(id);
        example3.setOrderByClause("seq");
        vo.setRecordProjectList(recordProjectService.selectByExample(example3));
        TBizRecordEducationExample example4=new TBizRecordEducationExample();
        example4.createCriteria().andIdEqualTo(id);
        example4.setOrderByClause("seq");
        vo.setRecordEducationList(recordEducationService.selectByExample(example4));
        TBizRecordLanguageExample example5=new TBizRecordLanguageExample();
        example5.createCriteria().andIdEqualTo(id);
        example5.setOrderByClause("seq");
        vo.setRecordLanguageList(recordLanguageService.selectByExample(example5));
        return vo;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    private TBizShareTalentService shareTalentService;
    @Autowired
    private TBizRecordWorkService recordWorkService;
    @Autowired
    private TBizRecordProjectService recordProjectService;
    @Autowired
    private TBizRecordEducationService recordEducationService;
    @Autowired
    private TBizRecordLanguageService recordLanguageService;


}
