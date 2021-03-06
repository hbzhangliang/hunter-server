package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.SysAccountService;
import cn.com.cubic.platform.hunter.mysql.services.TBizShareCompanyService;
import cn.com.cubic.platform.hunter.mysql.services.TBizShareTalentService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
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
 * Created by Liang.Zhang on 2018/9/26.
 **/
@Service
public class TBizShareCompanyServiceImpl  extends BaseServiceImpl<TBizShareCompany,TBizShareCompanyExample> implements TBizShareCompanyService {

    private final static Logger log = LoggerFactory.getLogger(TBizShareCompanyServiceImpl.class);


    /**
     * 参数  qu全部在一起处理
     * @param map
     * @param criteria
     * @return
     * @throws Exception
     */
    private TBizShareCompanyExample.Criteria constructCriteria(Map<String,Object> map, TBizShareCompanyExample.Criteria criteria) throws Exception{
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
                            case "Long":criteria= (TBizShareCompanyExample.Criteria)item.invoke(criteria,Long.valueOf(value.toString()));break;
                            case "Integer":criteria= (TBizShareCompanyExample.Criteria)item.invoke(criteria,Integer.valueOf(value.toString()));break;
                            default:criteria= (TBizShareCompanyExample.Criteria)item.invoke(criteria,value.toString());break;
                        }
                    }
                }
            }
            else if(key.startsWith("lk_")){
                String tmp=key.substring(3);
                tmp=String.format("and%sLike",tmp);
                for(Method item:methods){
                    if(tmp.equalsIgnoreCase(item.getName())){
                        criteria= (TBizShareCompanyExample.Criteria)item.invoke(criteria,"%"+value+"%");
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
                        criteria = (TBizShareCompanyExample.Criteria) item.invoke(criteria, UtilHelper.parseDateYMD(value.toString()));
                    }
                }
            }
            else {
                continue;
            }
        }
        return criteria;
    }

    private TBizShareCompanyExample construct(Map<String,Object> map) {
        try {
            TBizShareCompanyExample example = new TBizShareCompanyExample();
            TBizShareCompanyExample.Criteria criteria = example.createCriteria();
            criteria = this.constructCriteria(map, criteria);
            return example;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new HunterException("查询错误");
        }
    }

    @Override
    public PageParams<TBizShareCompany> list(PageParams<TBizShareCompany> pageParams) {
        //查询参数
        TBizShareCompanyExample example=this.construct(pageParams.getParams());
        //排序
        String strOrder=String.format("%s %s", UtilHelper.camelToUnderline(pageParams.getOrderBy()),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public List<TBizShareCompany> listAll() {
        return null;
    }

    @Override
    public TBizShareCompany findById(Long id) {
        TBizShareCompanyExample example = new TBizShareCompanyExample();
        example.createCriteria().andIdEqualTo(id);
        List<TBizShareCompany> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TBizShareCompanyExample example = new TBizShareCompanyExample();
        example.createCriteria().andIdIn(ids);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TBizShareCompany bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {
            TBizShareCompanyExample example = new TBizShareCompanyExample();
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


    @Override
    public List<Long> getAccountsBytalentid(Long talentId, List<TBizShareCompany> list) {
        //查出全部成员
        List<TSysAccount> accountList=sysAccountService.listAll();
        Set<Long> result=new HashSet<>(10);
        if(list!=null&&list.size()>0){
            for(TBizShareCompany item:list){
                switch (ComEnum.ShareType.getShareType(item.getShareType())){
                    case account:{
                        for(TSysAccount p:accountList){
                            if(p.getId().toString().equals(item.getShareValue())) {
                                result.add(p.getId());
                            }
                        }
                    }break;
                    case team:{
                        for(TSysAccount p:accountList){
                            if(p.getTeamId()!=null&&p.getTeamId().toString().equals(item.getShareValue())) {
                                result.add(p.getId());
                            }
                        }
                    }break;
                    case position:{
                        for(TSysAccount p:accountList){
                            if(p.getPositionId()!=null&&p.getPositionId().toString().equals(item.getShareValue())) {
                                result.add(p.getId());
                            }
                        }
                    }break;
                    case all:{
                        for(TSysAccount p:accountList){
                            result.add(p.getId());
                        }
                    }break;
                    default:break;
                }
            }
        }
        return new ArrayList<>(result);
    }


    @Autowired
    private SysAccountService sysAccountService;

}
