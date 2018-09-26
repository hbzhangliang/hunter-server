package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.TBizCompanyAccountRefService;
import cn.com.cubic.platform.hunter.mysql.services.TBizTalentAccountRefService;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.global.GlobalHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/26.
 **/
@Service
public class TBizCompanyAccountRefServiceImpl  extends BaseServiceImpl<TBizCompanyAccountRef,TBizCompanyAccountRefExample> implements TBizCompanyAccountRefService {

    private final static Logger log = LoggerFactory.getLogger(TBizCompanyAccountRefServiceImpl.class);

    @Override
    public TBizCompanyAccountRef findById(Long id) {
        TBizCompanyAccountRefExample example = new TBizCompanyAccountRefExample();
        example.createCriteria().andIdEqualTo(id);
        List<TBizCompanyAccountRef> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TBizCompanyAccountRefExample example = new TBizCompanyAccountRefExample();
        List<Long> details=new ArrayList<>(20);
        example.createCriteria().andIdIn(details);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TBizCompanyAccountRef bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {
            TBizCompanyAccountRefExample example = new TBizCompanyAccountRefExample();
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


    //在线程中获取不到global值
    private void saveOrUpdateBean(TBizCompanyAccountRef bean){
        Date dt=new Date();
        if (null != bean.getId()) {
            TBizCompanyAccountRefExample example = new TBizCompanyAccountRefExample();
            example.createCriteria().andIdEqualTo(bean.getId());
            bean.setModifyTime(dt);
            this.updateByExampleSelective(bean, example);
        } else {
            bean.setCreateTime(dt);
            this.insert(bean);
        }
    }



    @Override
    public void updateShareData(Long accountId, Long companyId) {
        TBizCompanyAccountRefExample example=new TBizCompanyAccountRefExample();
        example.createCriteria().andAccountIdEqualTo(accountId).andCompanyIdEqualTo(companyId);
        List<TBizCompanyAccountRef> list=this.selectByExample(example);
        //如果不存在 添加
        if(list==null||list.size()<1){
            TBizCompanyAccountRef bean=new TBizCompanyAccountRef();
            bean.setAccountId(accountId);
            bean.setCompanyId(companyId);
            bean.setFlag(true);
            this.saveOrUpdateBean(bean);
        }
        else { //否则，做更新处理
            TBizCompanyAccountRef bean=list.get(0);
            bean.setFlag(true);
            this.saveOrUpdateBean(bean);
        }
    }

    @Override
    public void updateShareData(List<Long> accountIds, Long companyId) {
        //首先把存在 的数据全部变成 flag=false
        String sql=String.format("update t_biz_company_acount_ref set flag=false where company_id=%s",companyId);
        jdbcTemplate.execute(sql);
        if(null!=accountIds&&accountIds.size()>0){
            for(Long item:accountIds){
                this.updateShareData(item,companyId);
            }
        }
    }


    @Autowired
    private JdbcTemplate jdbcTemplate;

}
