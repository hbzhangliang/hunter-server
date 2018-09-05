package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.TBizCity;
import cn.com.cubic.platform.hunter.mysql.entity.TBizCityExample;
import cn.com.cubic.platform.hunter.mysql.entity.TSysAccount;
import cn.com.cubic.platform.hunter.mysql.services.TBizCityService;
import cn.com.cubic.platform.hunter.mysql.vo.ElTreeVo;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.JsonReader;
import cn.com.cubic.platform.utils.UtilHelper;
import cn.com.cubic.platform.utils.global.GlobalHolder;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.*;

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
        String strOrder=String.format("%s %s", UtilHelper.camelToUnderline(pageParams.getOrderBy()),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public List<TBizCity> listAll() {
        TBizCityExample example=new TBizCityExample();
        example.setOrderByClause("seq is null,seq");
        return this.selectByExample(example);
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
        List<Long> details=new ArrayList<>(20);
        for(Long item:ids){
            details.addAll(this.getChildrenIds(item));
        }
        example.createCriteria().andIdIn(details);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TBizCity bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {
            TBizCityExample example = new TBizCityExample();
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
    public ElTreeVo tree(Long id) {
        TBizCity city=this.findById(id);

        TBizCityExample example=new TBizCityExample();
        example.createCriteria().andParentIdEqualTo(city.getId());
        example.setOrderByClause("seq is null,seq");
        List<TBizCity> list=this.selectByExample(example);

        if(null==list||list.isEmpty()){
            return new ElTreeVo(city.getId(),city.getName(),null);
        }
        else {
            List<ElTreeVo> children=new ArrayList<>(10);
            for(TBizCity item:list){
                children.add(this.tree(item.getId()));
            }
            return new ElTreeVo(city.getId(),city.getName(),children);
        }
    }


    @Override
    public List<ElTreeVo> tree() {
        TBizCityExample example=new TBizCityExample();
        example.createCriteria().andParentIdIsNull();
        example.setOrderByClause("seq is null,seq");
        List<TBizCity> list=this.selectByExample(example);
        if(null==list||list.isEmpty()){
            log.info("未查询到城市数据");
            return null;
        }
        else {
            List<ElTreeVo> result=new ArrayList<>(10);
            for(TBizCity item:list){
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
    public Map<Long, String> cityMap() {
        List<TBizCity> list=this.listAll();
        Map<Long,String> result=new HashMap<>(200);
        if(list!=null&&list.size()>0){
            for(TBizCity item:list){
                result.put(item.getId(),item.getName());
            }
        }
        return result;
    }

    @Override
    public void importCitys() {
        //删除所有
        this.deleteByExample(null);

        String path="initdata/city.json";
        JSONObject jsonObject= JsonReader.readJson(path);
        if(null==jsonObject||jsonObject.isEmpty()) return;
        JSONArray jsonArray=jsonObject.getJSONArray("provinces");
        for(Object item:jsonArray){
            JSONObject jsb=(JSONObject)item;
            String provinceName=jsb.getString("provinceName");
            JSONArray jAy=jsb.getJSONArray("citys");

            //插入省份
            TBizCity city=new TBizCity();
            city.setName(provinceName);
            this.saveOrUpdate(city);

            if(null!=jAy&&jAy.size()>0){
                for(Object tmp:jAy){
                    JSONObject js=(JSONObject)tmp;
                    TBizCity city1=new TBizCity();
                    city1.setName(js.getString("citysName"));
                    city1.setParentId(city.getId());
                    this.saveOrUpdate(city1);
                }
            }
        }

    }
}
