package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.TBizCompanyAccountRefService;
import cn.com.cubic.platform.hunter.mysql.services.TBizCompanyService;
import cn.com.cubic.platform.hunter.mysql.services.TBizShareCompanyService;
import cn.com.cubic.platform.hunter.mysql.vo.CompanyVo;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.hunter.mysql.vo.TalentVo;
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
 * Created by Liang.Zhang on 2018/9/25.
 **/
@Service
public class TBizCompanyServiceImpl extends BaseServiceImpl<TBizCompany,TBizCompanyExample> implements TBizCompanyService {


    private final static Logger log = LoggerFactory.getLogger(TBizCompanyServiceImpl.class);

    /**
     * 参数  qu全部在一起处理
     * @param map
     * @param criteria
     * @return
     * @throws Exception
     */
    private TBizCompanyExample.Criteria constructCriteria(Map<String,Object> map, TBizCompanyExample.Criteria criteria) throws Exception{
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
                            case "Long":criteria= (TBizCompanyExample.Criteria)item.invoke(criteria,Long.valueOf(value.toString()));break;
                            case "Integer":criteria= (TBizCompanyExample.Criteria)item.invoke(criteria,Integer.valueOf(value.toString()));break;
                            case "Boolean":criteria=(TBizCompanyExample.Criteria)item.invoke(criteria,Boolean.valueOf(value.toString()));break;
                            default:criteria= (TBizCompanyExample.Criteria)item.invoke(criteria,value.toString());break;
                        }
                    }
                }
            }
            else if(key.startsWith("lk_")){
                String tmp=key.substring(3);
                tmp=String.format("and%sLike",tmp);
                for(Method item:methods){
                    if(tmp.equalsIgnoreCase(item.getName())){
                        criteria= (TBizCompanyExample.Criteria)item.invoke(criteria,"%"+value+"%");
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
                        criteria = (TBizCompanyExample.Criteria) item.invoke(criteria, UtilHelper.parseDateYMD(value.toString()));
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
    private TBizCompanyExample construct(Map<String,Object> map,Boolean ownerFlag) {
        try {
            TBizCompanyExample example = new TBizCompanyExample();
            TBizCompanyExample.Criteria criteria = example.createCriteria();
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
    public PageParams<TBizCompany> list(PageParams<TBizCompany> pageParams,Boolean ownerFlag) {
        //查询参数
        TBizCompanyExample example=this.construct(pageParams.getParams(),ownerFlag);
        //排序
        String strOrder=String.format("%s %s", UtilHelper.camelToUnderline(pageParams.getOrderBy()),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public List<TBizCompany> listAll() {
        return null;
    }

    @Override
    public TBizCompany findById(Long id) {
        TBizCompanyExample example = new TBizCompanyExample();
        example.createCriteria().andIdEqualTo(id);
        List<TBizCompany> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            log.warn("company id [{}] not find",id);
            return null;
        }
        return list.get(0);
    }


    @Override
    public Boolean fakeDel(List<Long> ids) {
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        TBizCompanyExample example = new TBizCompanyExample();
        example.createCriteria().andIdIn(ids).andOwnerEqualTo(user.getId());
        TBizCompany bean=new TBizCompany();
        bean.setDelStatus(ComEnum.CompanyDelStatus.Faked.toString());
        this.updateByExampleSelective(bean,example);
        return true;
    }

    @Override
    public Boolean adminDel(List<Long> ids) {
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        TBizCompanyExample example = new TBizCompanyExample();
        example.createCriteria().andIdIn(ids).andOwnerEqualTo(user.getId());
        TBizCompany bean=new TBizCompany();
        bean.setDelStatus(ComEnum.CompanyDelStatus.Deleted.toString());
        this.updateByExampleSelective(bean,example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(CompanyVo bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        Boolean delFalg=false;
        if (null != bean.getId()) {
            TBizCompanyExample example = new TBizCompanyExample();
            example.createCriteria().andIdEqualTo(bean.getId());
            bean.setModifyBy(user.getName());
            bean.setModifyTime(dt);
            this.updateByExampleSelective(bean, example);

            //删除共享数据
            delFalg=true;

        } else {
            bean.setOwner(bean.getId());
            bean.setCreateBy(user.getName());
            bean.setDelStatus(ComEnum.TalentDelStatus.Normal.toString());
            bean.setCreateTime(dt);
            this.insert(bean);
        }

        this.createShareCompanyTx(bean,delFalg);
        return true;
    }


    private void createShareCompanyTx(CompanyVo bean,Boolean delFlag){
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if(delFlag) {
                    String sql = String.format("delete from t_biz_share_company where company_id=%s", bean.getId());
                    jdbcTemplate.batchUpdate(sql);
                }
                if(null!=bean.getShareCompanyList()) {
                    for (TBizShareCompany item : bean.getShareCompanyList()) {
                        item.setCompanyId(bean.getId());
                        shareCompanyService.insert(item);
                    }
                }

                //对 t_biz_share_talent 表的操作
                List<Long> accountIds= shareCompanyService.getAccountsBytalentid(bean.getId(),bean.getShareCompanyList());
                bizCompanyAccountRefService.updateShareData(accountIds,bean.getId());
            }
        });
    }


    @Override
    public CompanyVo findVoById(Long id) {
        TBizCompany company=this.findById(id);
        CompanyVo vo= JSONObject.parseObject(JSONObject.toJSONString(company),CompanyVo.class);
        if(null!=vo.getBusiness()) {
            vo.setTmpBusinessId(comServers.getSplitIds(vo.getBusiness()));
            vo.setTmpBusinessName(comServers.getBusinesssNames(vo.getTmpBusinessId()));
        }
        if(null!=vo.getCity()) {
            vo.setTmpCityId(comServers.getSplitIds(vo.getCity()));
            vo.setTmpCityName(comServers.getCityNames(vo.getTmpCityId()));
        }

        TBizShareCompanyExample example=new TBizShareCompanyExample();
        example.createCriteria().andCompanyIdEqualTo(id);
        vo.setShareCompanyList(shareCompanyService.selectByExample(example));
        return vo;
    }

    @Autowired
    private TBizCompanyAccountRefService bizCompanyAccountRefService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private TBizShareCompanyService shareCompanyService;

    @Autowired
    private ComServers comServers;
}
