package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.TLogLoginService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.UtilHelper;
import cn.com.cubic.platform.utils.global.GlobalHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/9/6.
 **/
@Service
public class TLogLoginServiceImpl  extends BaseServiceImpl<TLogLogin,TLogLoginExample> implements TLogLoginService {

    private final static Logger log = LoggerFactory.getLogger(TLogLoginServiceImpl.class);



    /**
     * 参数  qu全部在一起处理
     * @param map
     * @param criteria
     * @return
     * @throws Exception
     */
    private TLogLoginExample.Criteria constructCriteria(Map<String,Object> map, TLogLoginExample.Criteria criteria) throws Exception{
        Class clz=criteria.getClass();
        Method[] methods=clz.getMethods();
        for(Map.Entry<String,Object> entity:map.entrySet()){
            String key=entity.getKey();
            Object value=entity.getValue();
            if(value==null|| StringUtils.isBlank(value.toString())) continue;
            if(key.startsWith("eq_")){
                String tmp=key.substring(3);
                tmp=String.format("and%sEqualto",tmp);
                for(Method item:methods){
                    if(tmp.equalsIgnoreCase(item.getName())){
                        //参数类型 ,都只有一个参数
                        Class<?> cl[]=item.getParameterTypes();
                        String parasType=cl[0].getSimpleName();
                        switch (parasType){
                            case "Long":criteria= (TLogLoginExample.Criteria)item.invoke(criteria,Long.valueOf(value.toString()));break;
                            case "Integer":criteria= (TLogLoginExample.Criteria)item.invoke(criteria,Integer.valueOf(value.toString()));break;
                            default:criteria= (TLogLoginExample.Criteria)item.invoke(criteria,value.toString());break;
                        }
                    }
                }
            }
            else if(key.startsWith("lk_")){
                String tmp=key.substring(3);
                tmp=String.format("and%sLike",tmp);
                for(Method item:methods){
                    if(tmp.equalsIgnoreCase(item.getName())){
                        criteria= (TLogLoginExample.Criteria)item.invoke(criteria,"%"+value+"%");
                    }
                }
            }
            else if(key.startsWith("bt_")){
                String tmp=key.substring(3);
                if(key.endsWith("1")){
                    tmp=tmp.substring(0,tmp.length()-1);
                    tmp=String.format("and%sGreaterThanOrEqualTo",tmp);
                }
                else {
                    tmp=tmp.substring(0,tmp.length()-1);
                    tmp=String.format("and%sLessThan",tmp);
                }

                for(Method item:methods){
                    if(tmp.equalsIgnoreCase(item.getName())) {
                        criteria = (TLogLoginExample.Criteria) item.invoke(criteria, UtilHelper.parseDateYMD(value.toString()));
                    }
                }
            }
            else {
                continue;
            }
        }
        return criteria;
    }


    private TLogLoginExample construct(Map<String,Object> map) {
        try {
            TLogLoginExample example = new TLogLoginExample();
            TLogLoginExample.Criteria criteria = example.createCriteria();
            criteria = this.constructCriteria(map, criteria);
            return example;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new HunterException("查询错误");
        }
    }


    @Override
    public PageParams<TLogLogin> list(PageParams<TLogLogin> pageParams) {
        //查询参数
        TLogLoginExample example=this.construct(pageParams.getParams());
        //排序
        String strOrder=String.format("%s %s", UtilHelper.camelToUnderline(pageParams.getOrderBy()),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public List<TLogLogin> listAll() {
        TLogLoginExample example=new TLogLoginExample();
        example.setOrderByClause("seq is null,seq");
        return this.selectByExample(example);
    }

    @Override
    public TLogLogin findById(Long id) {
        TLogLoginExample example = new TLogLoginExample();
        example.createCriteria().andIdEqualTo(id);
        List<TLogLogin> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TLogLoginExample example = new TLogLoginExample();
        example.createCriteria().andIdIn(ids);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TLogLogin bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {
            TLogLoginExample example = new TLogLoginExample();
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
}
