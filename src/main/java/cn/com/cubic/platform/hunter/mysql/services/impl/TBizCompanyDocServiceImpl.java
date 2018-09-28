package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.TBizCompanyDocService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
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
 * Created by Liang.Zhang on 2018/9/28.
 **/
@Service
public class TBizCompanyDocServiceImpl  extends BaseServiceImpl<TBizCompanyDoc,TBizCompanyDocExample> implements TBizCompanyDocService {

    private final static Logger log = LoggerFactory.getLogger(TBizCompanyDocServiceImpl.class);

    @Override
    public PageParams<TBizCompanyDoc> list(PageParams<TBizCompanyDoc> pageParams) {
        TBizCompanyDocExample example=new TBizCompanyDocExample();
        //排序
        String strOrder=String.format("%s %s", UtilHelper.camelToUnderline(pageParams.getOrderBy()),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public List<TBizCompanyDoc> listAll() {
        return null;
    }

    @Override
    public TBizCompanyDoc findById(Long id) {
        TBizCompanyDocExample example = new TBizCompanyDocExample();
        example.createCriteria().andIdEqualTo(id);
        List<TBizCompanyDoc> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            return null;
//            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TBizCompanyDocExample example = new TBizCompanyDocExample();
        example.createCriteria().andIdIn(ids);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TBizCompanyDoc bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {
            TBizCompanyDocExample example = new TBizCompanyDocExample();
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
    public void saveOrUpdate(Long companyId, List<Long> docIds) {
        //首先全部删除
        String sql=String.format("delete from t_biz_company_doc where company_id=%s",companyId);
        jdbcTemplate.update(sql);
        //然后全部新增
        for(Long item:docIds){
            TBizCompanyDoc p=new TBizCompanyDoc();
            p.setCompanyId(companyId);
            p.setDocId(item);
            this.saveOrUpdate(p);
        }
    }

    @Override
    public List<Long> getDocIdsByCompanyId(Long companyId) {
        TBizCompanyDocExample example=new TBizCompanyDocExample();
        example.createCriteria().andCompanyIdEqualTo(companyId);
        List<TBizCompanyDoc> list=this.selectByExample(example);
        List<Long> result=new ArrayList<>(10);
        if(list!=null&&list.size()>0){
            for (TBizCompanyDoc item:list){
                result.add(item.getDocId());
            }
        }
        return result;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;
}
