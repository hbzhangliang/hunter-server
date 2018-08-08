package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.TBizCity;
import cn.com.cubic.platform.hunter.mysql.entity.TBizCityExample;
import cn.com.cubic.platform.hunter.mysql.services.TBizCityService;
import cn.com.cubic.platform.hunter.mysql.vo.CityTreeVo;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.Exception.HunterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    @Override
    public CityTreeVo tree(Long id) {
        TBizCity city=this.findById(id);

        TBizCityExample example=new TBizCityExample();
        example.createCriteria().andParentIdEqualTo(city.getId());
        List<TBizCity> list=this.selectByExample(example);

        if(null==list||list.isEmpty()){
            return new CityTreeVo(city.getId(),city.getName(),null);
        }
        else {
            List<CityTreeVo> children=new ArrayList<>(10);
            for(TBizCity item:list){
                children.add(this.tree(item.getId()));
            }
            return new CityTreeVo(city.getId(),city.getName(),children);
        }
    }


    @Override
    public List<CityTreeVo> tree() {
        TBizCityExample example=new TBizCityExample();
        example.createCriteria().andParentIdIsNull();
        List<TBizCity> list=this.selectByExample(example);
        if(null==list||list.isEmpty()){
            log.info("未查询到城市数据");
            return null;
        }
        else {
            List<CityTreeVo> result=new ArrayList<>(10);
            for(TBizCity item:list){
                result.add(this.tree(item.getId()));
            }
            return result;
        }

    }
}
