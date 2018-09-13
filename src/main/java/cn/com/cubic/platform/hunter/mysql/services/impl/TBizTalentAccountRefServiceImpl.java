package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
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
 * Created by Liang.Zhang on 2018/9/12.
 **/
@Service
public class TBizTalentAccountRefServiceImpl  extends BaseServiceImpl<TBizTalentAccountRef,TBizTalentAccountRefExample> implements TBizTalentAccountRefService {


    private final static Logger log = LoggerFactory.getLogger(TBizTalentAccountRefServiceImpl.class);

    @Override
    public TBizTalentAccountRef findById(Long id) {
        TBizTalentAccountRefExample example = new TBizTalentAccountRefExample();
        example.createCriteria().andIdEqualTo(id);
        List<TBizTalentAccountRef> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TBizTalentAccountRefExample example = new TBizTalentAccountRefExample();
        List<Long> details=new ArrayList<>(20);
        example.createCriteria().andIdIn(details);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TBizTalentAccountRef bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {
            TBizTalentAccountRefExample example = new TBizTalentAccountRefExample();
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
     * 根据 id 处理数据
     * @param accountId
     * @param talentId
     */
    @Override
    public void updateShareData(Long accountId, Long talentId) {
        TBizTalentAccountRefExample example=new TBizTalentAccountRefExample();
        example.createCriteria().andAccountIdEqualTo(accountId).andTalentIdEqualTo(talentId);
        List<TBizTalentAccountRef> list=this.selectByExample(example);
        //如果不存在 添加
        if(list==null||list.size()<1){
            TBizTalentAccountRef bean=new TBizTalentAccountRef();
            bean.setAccountId(accountId);
            bean.setTalentId(talentId);
            bean.setFlag(true);
            this.saveOrUpdate(bean);
        }
        else { //否则，做更新处理
            TBizTalentAccountRef bean=list.get(0);
            bean.setFlag(true);
            this.saveOrUpdate(bean);
        }
    }


    @Override
    public void updateShareData(List<Long> accountIds, Long talentId) {
        //首先把存在 的数据全部变成 flag=false
        String sql=String.format("update t_biz_talent_acount_ref set flag=false where talent_id=%s",talentId);
        jdbcTemplate.execute(sql);
        if(null!=accountIds&&accountIds.size()>0){
            for(Long item:accountIds){
                this.updateShareData(item,talentId);
            }
        }
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

}
