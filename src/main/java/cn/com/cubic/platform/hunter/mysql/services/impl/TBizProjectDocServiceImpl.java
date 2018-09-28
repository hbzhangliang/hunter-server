package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.TBizProjectDocService;
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
public class TBizProjectDocServiceImpl extends BaseServiceImpl<TBizProjectDoc,TBizProjectDocExample> implements TBizProjectDocService {

    private final static Logger log = LoggerFactory.getLogger(TBizProjectDocServiceImpl.class);

    @Override
    public PageParams<TBizProjectDoc> list(PageParams<TBizProjectDoc> pageParams) {
        TBizProjectDocExample example=new TBizProjectDocExample();
        //排序
        String strOrder=String.format("%s %s", UtilHelper.camelToUnderline(pageParams.getOrderBy()),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public List<TBizProjectDoc> listAll() {
        return null;
    }

    @Override
    public TBizProjectDoc findById(Long id) {
        TBizProjectDocExample example = new TBizProjectDocExample();
        example.createCriteria().andIdEqualTo(id);
        List<TBizProjectDoc> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            return null;
//            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TBizProjectDocExample example = new TBizProjectDocExample();
        example.createCriteria().andIdIn(ids);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TBizProjectDoc bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {
            TBizProjectDocExample example = new TBizProjectDocExample();
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
    public void saveOrUpdate(Long projectId, List<Long> docIds) {
//首先全部删除
        String sql=String.format("delete from t_biz_project_doc where project_id=%s",projectId);
        jdbcTemplate.update(sql);
        //然后全部新增
        for(Long item:docIds){
            TBizProjectDoc p=new TBizProjectDoc();
            p.setProjectId(projectId);
            p.setDocId(item);
            this.saveOrUpdate(p);
        }
    }

    @Override
    public List<Long> getDocIdsByProjectId(Long projectId) {
        TBizProjectDocExample example=new TBizProjectDocExample();
        example.createCriteria().andProjectIdEqualTo(projectId);
        List<TBizProjectDoc> list=this.selectByExample(example);
        List<Long> result=new ArrayList<>(10);
        if(list!=null&&list.size()>0){
            for (TBizProjectDoc item:list){
                result.add(item.getDocId());
            }
        }
        return result;
    }


    @Autowired
    private JdbcTemplate jdbcTemplate;

}
