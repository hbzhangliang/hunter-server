package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.TBizCity;
import cn.com.cubic.platform.hunter.mysql.entity.TBizCityExample;
import cn.com.cubic.platform.hunter.mysql.services.TBizCityService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.Exception.HunterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/8/8.
 **/
@Service
public class TBizCityServiceImpl  extends BaseServiceImpl<TBizCity,TBizCityExample> implements TBizCityService {

    private final static Logger log = LoggerFactory.getLogger(TBizCityServiceImpl.class);

    @Override
    public PageParams<TBizCity> list(PageParams<TBizCity> pageParams) {
        //查询参数
        TBizCityExample example=new TBizCityExample();
        //排序
        String strOrder=String.format("%s %s",pageParams.getOrderBy(),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public TBizCity findById(Long id) {
        TBizCityExample example = new TBizCityExample();
        example.createCriteria().andIdEqualTo(id);
        List<TBizCity> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TBizCityExample example = new TBizCityExample();
        example.createCriteria().andIdIn(ids);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TBizCity bean) {
        if (null != bean.getId()) {
            TBizCityExample example = new TBizCityExample();
            example.createCriteria().andIdEqualTo(bean.getId());
            this.updateByExampleSelective(bean, example);
        } else {
            this.insert(bean);
        }
        return true;
    }

}
