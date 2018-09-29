package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.TBizProjectAccountRefService;
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
 * Created by Liang.Zhang on 2018/9/28.
 **/
@Service
public class TBizProjectAccountRefServiceImpl extends BaseServiceImpl<TBizProjectAccountRef,TBizProjectAccountRefExample> implements TBizProjectAccountRefService{

    private final static Logger log = LoggerFactory.getLogger(TBizProjectAccountRefServiceImpl.class);

    @Override
    public TBizProjectAccountRef findById(Long id) {
        TBizProjectAccountRefExample example = new TBizProjectAccountRefExample();
        example.createCriteria().andIdEqualTo(id);
        List<TBizProjectAccountRef> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TBizProjectAccountRefExample example = new TBizProjectAccountRefExample();
        List<Long> details=new ArrayList<>(20);
        example.createCriteria().andIdIn(details);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TBizProjectAccountRef bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {
            TBizProjectAccountRefExample example = new TBizProjectAccountRefExample();
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
    private void saveOrUpdateBean(TBizProjectAccountRef bean){
        Date dt=new Date();
        if (null != bean.getId()) {
            TBizProjectAccountRefExample example = new TBizProjectAccountRefExample();
            example.createCriteria().andIdEqualTo(bean.getId());
            bean.setModifyTime(dt);
            this.updateByExampleSelective(bean, example);
        } else {
            bean.setCreateTime(dt);
            this.insert(bean);
        }
    }


    @Override
    public void updateShareData(Long accountId, Long projectId) {
        TBizProjectAccountRefExample example=new TBizProjectAccountRefExample();
        example.createCriteria().andAccountIdEqualTo(accountId).andProjectIdEqualTo(projectId);
        List<TBizProjectAccountRef> list=this.selectByExample(example);
        //如果不存在 添加
        if(list==null||list.size()<1){
            TBizProjectAccountRef bean=new TBizProjectAccountRef();
            bean.setAccountId(accountId);
            bean.setProjectId(projectId);
            bean.setFlag(true);
            this.saveOrUpdateBean(bean);
        }
        else { //否则，做更新处理
            TBizProjectAccountRef bean=list.get(0);
            bean.setFlag(true);
            this.saveOrUpdateBean(bean);
        }
    }

    @Override
    public void updateShareData(List<Long> accountIds, Long projectId) {
        //首先把存在 的数据全部变成 flag=false
        String sql=String.format("update t_biz_project_acount_ref set flag=false where project_id=%s",projectId);
        jdbcTemplate.execute(sql);
        if(null!=accountIds&&accountIds.size()>0){
            for(Long item:accountIds){
                this.updateShareData(item,projectId);
            }
        }
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;
}
