package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.TBizCareerService;
import cn.com.cubic.platform.hunter.mysql.vo.ElTreeVo;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.JsonReader;
import cn.com.cubic.platform.utils.global.GlobalHolder;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
public class TBizCareerServiceImpl extends BaseServiceImpl<TBizCareer,TBizCareerExample> implements TBizCareerService {

    private final static Logger log = LoggerFactory.getLogger(TBizCareerServiceImpl.class);

    @Override
    public PageParams<TBizCareer> list(PageParams<TBizCareer> pageParams) {
        //查询参数
        TBizCareerExample example=new TBizCareerExample();
        //排序
        String strOrder=String.format("%s %s",pageParams.getOrderBy(),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public List<TBizCareer> listAll() {
        TBizCareerExample example=new TBizCareerExample();
        example.setOrderByClause("seq is null,seq");
        return this.selectByExample(example);
    }

    @Override
    public TBizCareer findById(Long id) {
        TBizCareerExample example = new TBizCareerExample();
        example.createCriteria().andIdEqualTo(id);
        List<TBizCareer> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TBizCareerExample example = new TBizCareerExample();
        List<Long> details=new ArrayList<>(20);
        for(Long item:ids){
            details.addAll(this.getChildrenIds(item));
        }
        example.createCriteria().andIdIn(details);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TBizCareer bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {
            TBizCareerExample example = new TBizCareerExample();
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
        TBizCareer career=this.findById(id);
        TBizCareerExample example=new TBizCareerExample();
        example.createCriteria().andParentIdEqualTo(career.getId());
        example.setOrderByClause("seq is null,seq");
        List<TBizCareer> list=this.selectByExample(example);
        if(null==list||list.isEmpty()){
            return new ElTreeVo(career.getId(),career.getName(),null);
        }
        else {
            List<ElTreeVo> children=new ArrayList<>(10);
            for(TBizCareer item:list){
                children.add(this.tree(item.getId()));
            }
            return new ElTreeVo(career.getId(),career.getName(),children);
        }
    }

    @Override
    public List<ElTreeVo> tree() {
        TBizCareerExample example=new TBizCareerExample();
        example.createCriteria().andParentIdIsNull();
        example.setOrderByClause("seq is null,seq");
        List<TBizCareer> list=this.selectByExample(example);
        if(null==list||list.isEmpty()){
            log.info("未查询到行业数据");
            return null;
        }
        else {
            List<ElTreeVo> result=new ArrayList<>(10);
            for(TBizCareer item:list){
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
    public void importCareers() {
        //删除所有
        this.deleteByExample(null);
        String path="initdata/career.json";
        JSONArray jsonArray= JsonReader.readJsonArray(path);
        if(null==jsonArray||jsonArray.isEmpty()) return;
        for(Object item:jsonArray){
            JSONObject jsb=(JSONObject)item;
            this.importCareer(jsb,null);
        }
    }


    /**
     * 递归获取行业数据
     * @param jsonObject
     * @param pId
     */
    private void importCareer(JSONObject jsonObject,Long pId){
        TBizCareer career=new TBizCareer();
        career.setName(jsonObject.getString("label"));
        career.setParentId(pId);
        this.saveOrUpdate(career);

        JSONArray jsonArray=jsonObject.getJSONArray("children");
        if(null==jsonArray||jsonArray.isEmpty()){
            return;
        }
        else {
            for(Object item:jsonArray){
                this.importCareer((JSONObject)item,career.getId());
            }
        }
    }

}
