package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.TBizTalentDocService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.UtilHelper;
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
 * Created by Liang.Zhang on 2018/9/20.
 **/
@Service
public class TBizTalentDocServiceImpl extends BaseServiceImpl<TBizTalentDoc,TBizTalentDocExample> implements TBizTalentDocService {

    private final static Logger log = LoggerFactory.getLogger(TBizTalentDocServiceImpl.class);

    @Override
    public PageParams<TBizTalentDoc> list(PageParams<TBizTalentDoc> pageParams) {
        TBizTalentDocExample example=new TBizTalentDocExample();
        //排序
        String strOrder=String.format("%s %s", UtilHelper.camelToUnderline(pageParams.getOrderBy()),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public List<TBizTalentDoc> listAll() {
        return this.selectByExample(null);
    }

    @Override
    public TBizTalentDoc findById(Long id) {
        TBizTalentDocExample example = new TBizTalentDocExample();
        example.createCriteria().andIdEqualTo(id);
        List<TBizTalentDoc> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TBizTalentDocExample example = new TBizTalentDocExample();
        example.createCriteria().andIdIn(ids);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TBizTalentDoc bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {
            TBizTalentDocExample example = new TBizTalentDocExample();
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
    public void saveOrUpdate(Long talentId, List<Long> docIds) {
        //首先全部删除
        String sql=String.format("delete from t_biz_talent_doc where talent_id=%s",talentId);
        jdbcTemplate.update(sql);
        //然后全部新增
        for(Long item:docIds){
           TBizTalentDoc p=new TBizTalentDoc();
           p.setTalentId(talentId);
           p.setDocId(item);
           this.saveOrUpdate(p);
        }
    }


    @Override
    public List<Long> getDocIdsByTalentId(Long talentId) {
        TBizTalentDocExample example=new TBizTalentDocExample();
        example.createCriteria().andTalentIdEqualTo(talentId);
        List<TBizTalentDoc> list=this.selectByExample(example);
        List<Long> result=new ArrayList<>(10);
        if(list!=null&&list.size()>0){
            for (TBizTalentDoc item:list){
                result.add(item.getDocId());
            }
        }
        return result;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;
}
