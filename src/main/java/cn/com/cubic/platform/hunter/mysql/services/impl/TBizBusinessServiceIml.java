package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.TBizBusinessService;
import cn.com.cubic.platform.hunter.mysql.vo.ElTreeVo;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.global.GlobalHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/8/13.
 **/
@Service
public class TBizBusinessServiceIml  extends BaseServiceImpl<TBizBusiness,TBizBusinessExample> implements TBizBusinessService {
    private final static Logger log = LoggerFactory.getLogger(TBizBusinessServiceIml.class);

    @Override
    public PageParams<TBizBusiness> list(PageParams<TBizBusiness> pageParams) {
        //查询参数
        TBizBusinessExample example=new TBizBusinessExample();
        //排序
        String strOrder=String.format("%s %s",pageParams.getOrderBy(),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public List<TBizBusiness> listAll() {
        TBizBusinessExample example=new TBizBusinessExample();
        example.setOrderByClause("seq is null,seq");
        return this.selectByExample(example);
    }

    @Override
    public TBizBusiness findById(Long id) {
        TBizBusinessExample example = new TBizBusinessExample();
        example.createCriteria().andIdEqualTo(id);
        List<TBizBusiness> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TBizBusinessExample example = new TBizBusinessExample();
        List<Long> details=new ArrayList<>(20);
        for(Long item:ids){
            details.addAll(this.getChildrenIds(item));
        }
        example.createCriteria().andIdIn(details);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TBizBusiness bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {
            TBizBusinessExample example = new TBizBusinessExample();
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
    public ElTreeVo tree(@NotEmpty Long id) {
        TBizBusiness business=this.findById(id);
        TBizBusinessExample example=new TBizBusinessExample();
        example.createCriteria().andParentIdEqualTo(business.getId());
        example.setOrderByClause("seq is null,seq");
        List<TBizBusiness> list=this.selectByExample(example);
        if(null==list||list.isEmpty()){
            return new ElTreeVo(business.getId(),business.getName(),null);
        }
        else {
            List<ElTreeVo> children=new ArrayList<>(10);
            for(TBizBusiness item:list){
                children.add(this.tree(item.getId()));
            }
            return new ElTreeVo(business.getId(),business.getName(),children);
        }
    }

    @Override
    public List<ElTreeVo> tree() {
        TBizBusinessExample example=new TBizBusinessExample();
        example.createCriteria().andParentIdIsNull();
        example.setOrderByClause("seq is null,seq");
        List<TBizBusiness> list=this.selectByExample(example);
        if(null==list||list.isEmpty()){
            log.info("未查询到行业数据");
            return null;
        }
        else {
            List<ElTreeVo> result=new ArrayList<>(10);
            for(TBizBusiness item:list){
                result.add(this.tree(item.getId()));
            }
            return result;
        }
    }

    @Override
    public List<Long> getChildrenIds(@NotEmpty Long id) {
        ElTreeVo vo=this.tree(id);
        if(null==vo){
            log.info("未查询到数据");
            return null;
        }
        return this.getChildrenIds(vo);
    }

    public List<Long> getChildrenIds(ElTreeVo vo){
        if(null==vo) return null;
        List<Long> result=new ArrayList<>(10);
        result.add(vo.getId());
        //只返回节点，没有子节点
        if(null==vo.getChildren()||vo.getChildren().isEmpty()){
            return result;
        }
        for(ElTreeVo item:vo.getChildren()){
            result.addAll(this.getChildrenIds(item));
        }
        return result;
    }


    @Override
    public void importBusiness() {

    }
}
