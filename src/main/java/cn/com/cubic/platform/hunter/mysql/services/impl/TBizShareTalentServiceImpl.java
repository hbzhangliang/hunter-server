package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.SysAccountService;
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
 * Created by Liang.Zhang on 2018/9/6.
 **/
@Service
public class TBizShareTalentServiceImpl extends BaseServiceImpl<TBizShareTalent,TBizShareTalentExample> implements TBizShareTalentService {

    private final static Logger log = LoggerFactory.getLogger(TBizShareTalentServiceImpl.class);


    /**
     * 参数  qu全部在一起处理
     * @param map
     * @param criteria
     * @return
     * @throws Exception
     */
    private TBizShareTalentExample.Criteria constructCriteria(Map<String,Object> map, TBizShareTalentExample.Criteria criteria) throws Exception{
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
                            case "Long":criteria= (TBizShareTalentExample.Criteria)item.invoke(criteria,Long.valueOf(value.toString()));break;
                            case "Integer":criteria= (TBizShareTalentExample.Criteria)item.invoke(criteria,Integer.valueOf(value.toString()));break;
                            default:criteria= (TBizShareTalentExample.Criteria)item.invoke(criteria,value.toString());break;
                        }
                    }
                }
            }
            else if(key.startsWith("lk_")){
                String tmp=key.substring(3);
                tmp=String.format("and%sLike",tmp);
                for(Method item:methods){
                    if(tmp.equalsIgnoreCase(item.getName())){
                        criteria= (TBizShareTalentExample.Criteria)item.invoke(criteria,"%"+value+"%");
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
                        criteria = (TBizShareTalentExample.Criteria) item.invoke(criteria, UtilHelper.parseDateYMD(value.toString()));
                    }
                }
            }
            else {
                continue;
            }
        }
        return criteria;
    }

    private TBizShareTalentExample construct(Map<String,Object> map) {
        try {
            TBizShareTalentExample example = new TBizShareTalentExample();
            TBizShareTalentExample.Criteria criteria = example.createCriteria();
            criteria = this.constructCriteria(map, criteria);
            return example;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new HunterException("查询错误");
        }
    }

    @Override
    public PageParams<TBizShareTalent> list(PageParams<TBizShareTalent> pageParams) {
        //查询参数
        TBizShareTalentExample example=this.construct(pageParams.getParams());
        //排序
        String strOrder=String.format("%s %s",UtilHelper.camelToUnderline(pageParams.getOrderBy()),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public List<TBizShareTalent> listAll() {
        TBizShareTalentExample example=new TBizShareTalentExample();
        example.setOrderByClause("id desc");
        return this.selectByExample(example);
    }

    @Override
    public TBizShareTalent findById(Long id) {
        TBizShareTalentExample example = new TBizShareTalentExample();
        example.createCriteria().andIdEqualTo(id);
        List<TBizShareTalent> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TBizShareTalentExample example = new TBizShareTalentExample();
        example.createCriteria().andIdIn(ids);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TBizShareTalent bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {
            TBizShareTalentExample example = new TBizShareTalentExample();
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
     * 查看  此talent在 此account是否可见
     * @param accountId
     * @param talentId
     * @return
     */
    @Override
    public Boolean checkShare(Long accountId, Long talentId) {
        TBizShareTalentExample example = new TBizShareTalentExample();
        example.createCriteria().andTalentIdEqualTo(talentId);
        List<TBizShareTalent> list = this.selectByExample(example);
        TSysAccount account=sysAccountService.findById(accountId);
        Boolean flag = false;
        if (list != null && list.size() > 0) {
            for (TBizShareTalent item : list) {
                if (item.getShareType().equals(ComEnum.ShareType.all.toString())||
                        (item.getShareType().equals(ComEnum.ShareType.account.toString())&&item.getShareValue().equals(accountId.toString()))||
                        (item.getShareType().equals(ComEnum.ShareType.position.toString())&&account.getPositionId()!=null&&item.getShareValue().equals(account.getPositionId().toString()))||
                        (item.getShareType().equals(ComEnum.ShareType.team.toString())&&account.getTeamId()!=null&&item.getShareValue().equals(account.getTeamId().toString()))
                        ){
                    flag=true;
                    break;
                }

            }
        }
        return flag;
    }


    @Override
    public Boolean checkShare(Long accountId, List<TBizShareTalent> list) {
        TSysAccount account=sysAccountService.findById(accountId);
        Boolean flag = false;
        if (list != null && list.size() > 0) {
            for (TBizShareTalent item : list) {
                if (item.getShareType().equals(ComEnum.ShareType.all.toString())||
                        (item.getShareType().equals(ComEnum.ShareType.account.toString())&&item.getShareValue().equals(accountId.toString()))||
                        (item.getShareType().equals(ComEnum.ShareType.position.toString())&&account.getPositionId()!=null&&item.getShareValue().equals(account.getPositionId().toString()))||
                        (item.getShareType().equals(ComEnum.ShareType.team.toString())&&account.getTeamId()!=null&&item.getShareValue().equals(account.getTeamId().toString()))
                        ){
                    flag=true;
                    break;
                }
            }
        }
        return flag;
    }


    @Override
    public List<Long> getAccountsBytalentid(Long talentId) {
        TBizShareTalentExample example=new TBizShareTalentExample();
        example.createCriteria().andTalentIdEqualTo(talentId);
        List<TBizShareTalent> list=this.selectByExample(example);
        //查出全部成员
        List<TSysAccount> accountList=sysAccountService.listAll();
        Set<Long> result=new HashSet<>(10);
        if(list!=null&&list.size()>0){
            for(TBizShareTalent item:list){
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


    @Override
    public List<Long> getAccountsBytalentid(Long talentId, List<TBizShareTalent> list) {
        //查出全部成员
        List<TSysAccount> accountList=sysAccountService.listAll();
        Set<Long> result=new HashSet<>(10);
        if(list!=null&&list.size()>0){
            for(TBizShareTalent item:list){
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
