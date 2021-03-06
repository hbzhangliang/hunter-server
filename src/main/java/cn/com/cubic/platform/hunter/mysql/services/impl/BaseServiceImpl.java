package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.BaseEntity;
import cn.com.cubic.platform.hunter.mysql.entity.BaseExample;
import cn.com.cubic.platform.hunter.mysql.mapper.BaseMapper;
import cn.com.cubic.platform.hunter.mysql.services.BaseService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/6/22.
 **/

@Service
public abstract class BaseServiceImpl<P extends BaseEntity,Q extends BaseExample> implements BaseService<P,Q> {

    @Autowired
    private BaseMapper<P,Q> mapper;

    @Override
    public int countByExample(Q example) {
        return mapper.countByExample(example);
    }

    @Override
    public int deleteByExample(Q example) {
        return mapper.deleteByExample(example);
    }

    @Override
    public int insert(P record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(P record) {
        return mapper.insertSelective(record);
    }

    @Override
    public List<P> selectByExampleWithRowbounds(Q example, RowBounds rowBounds) {
        return mapper.selectByExampleWithRowbounds(example,rowBounds);
    }

    @Override
    public List<P> selectByExample(Q example) {
        return mapper.selectByExample(example);
    }

    @Override
    public int updateByExampleSelective(P record, Q example) {
        return mapper.updateByExampleSelective(record,example);
    }

    @Override
    public int updateByExample(P record, Q example) {
        return mapper.updateByExample(record,example);
    }

    @Override
    public PageParams<P> listPage(Q example, PageParams<P> pageParams) {
        int currentPage=pageParams.getPage(),pageSize=pageParams.getPageSize();
        RowBounds rowBounds=new RowBounds((currentPage-1)*pageSize,pageSize);
        List<P> list=mapper.selectByExampleWithRowbounds(example,rowBounds);
        pageParams.setData(list);
        int totalRows=mapper.countByExample(example);
        int totalPage=(totalRows-1)/pageSize+1;
        pageParams.setTotalPage(totalPage);
        pageParams.setTotalRows(totalRows);
        return pageParams;
    }
}
