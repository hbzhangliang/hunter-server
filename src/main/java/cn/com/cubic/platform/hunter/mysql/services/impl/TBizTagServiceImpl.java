package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.TBizTagService;
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
 * Created by Liang.Zhang on 2018/8/15.
 **/
@Service
public class TBizTagServiceImpl  extends BaseServiceImpl<TBizTag,TBizTagExample> implements TBizTagService {

    private final static Logger log = LoggerFactory.getLogger(TBizTagServiceImpl.class);

    /**
     * 参数是groupId
     * @param pageParams
     * @return
     */
    @Override
    public PageParams<TBizTag> list(PageParams<TBizTag> pageParams) {
        //查询参数
        TBizTagExample example=new TBizTagExample();
        Object obj=pageParams.getParams().get("groupId");
        if(null!=obj){
            example.createCriteria().andGroupIdEqualTo(Long.valueOf(obj.toString()));
        }
        //排序
        String strOrder=String.format("%s %s",pageParams.getOrderBy(),pageParams.getDirection());
        example.setOrderByClause(strOrder);

        return this.listPage(example,pageParams);
    }

    @Override
    public List<TBizTag> listAll(Long groupId) {
        TBizTagExample example=new TBizTagExample();
        example.createCriteria().andGroupIdEqualTo(groupId);
        example.setOrderByClause("seq is null,seq");
        return this.selectByExample(example);
    }

    @Override
    public TBizTag findById(Long id) {
        TBizTagExample example = new TBizTagExample();
        example.createCriteria().andIdEqualTo(id);
        List<TBizTag> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TBizTagExample example = new TBizTagExample();
        List<Long> details=new ArrayList<>(20);
        for(Long item:ids){
            details.addAll(this.getChildrenIds(item));
        }
        example.createCriteria().andIdIn(details);
        this.deleteByExample(example);
        return true;
    }

    /**
     * 通过groupId删除
     * @param groupIds
     * @return
     */
    @Override
    public Boolean delbyGroupIds(List<Long> groupIds) {
        TBizTagExample example = new TBizTagExample();
        example.createCriteria().andGroupIdIn(groupIds);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TBizTag bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {
            TBizTagExample example = new TBizTagExample();
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
    public ElTreeVo tree(Long groupId, Long pId) {
        TBizTag tag=this.findById(pId);

        TBizTagExample example=new TBizTagExample();
        example.createCriteria().andGroupIdEqualTo(groupId).andParentIdEqualTo(tag.getId());
        example.setOrderByClause("seq is null,seq");
        List<TBizTag> list=this.selectByExample(example);

        if(null==list||list.isEmpty()){
            return new ElTreeVo(tag.getId(),tag.getName(),null);
        }
        else {
            List<ElTreeVo> children=new ArrayList<>(10);
            for(TBizTag item:list){
                children.add(this.tree(groupId,item.getId()));
            }
            return new ElTreeVo(tag.getId(),tag.getName(),children);
        }
    }

    @Override
    public List<ElTreeVo> tree(Long groupId) {
        TBizTagExample example=new TBizTagExample();
        example.createCriteria().andGroupIdEqualTo(groupId).andParentIdIsNull();
        example.setOrderByClause("seq is null,seq");
        List<TBizTag> list=this.selectByExample(example);
        if(null==list||list.isEmpty()){
            log.info("未查询到tag数据");
            return null;
        }
        else {
            List<ElTreeVo> result=new ArrayList<>(10);
            for(TBizTag item:list){
                result.add(this.tree(groupId,item.getId()));
            }
            return result;
        }
    }

    @Override
    public List<Long> getChildrenIds(Long pId) {
        List<Long> result=new ArrayList<>(10);
        result.add(pId);
        TBizTagExample example=new TBizTagExample();
        example.createCriteria().andParentIdEqualTo(pId);
        List<TBizTag> list=this.selectByExample(example);
        if(null==list||list.isEmpty()){
            return result;
        }
        else {
            for(TBizTag item:list){
                result.addAll(this.getChildrenIds(item.getId()));
            }
            return result;
        }
    }
}
