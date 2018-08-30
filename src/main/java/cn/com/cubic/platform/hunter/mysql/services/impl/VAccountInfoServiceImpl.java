package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.VAccountInfo;
import cn.com.cubic.platform.hunter.mysql.entity.VAccountInfoExample;
import cn.com.cubic.platform.hunter.mysql.services.VAccountInfoService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.UtilHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/8/29.
 **/
@Service
public class VAccountInfoServiceImpl extends BaseServiceImpl<VAccountInfo,VAccountInfoExample> implements VAccountInfoService {

    private final static Logger log = LoggerFactory.getLogger(VAccountInfoServiceImpl.class);



    /**
     * 参数 只能是 String，Long，Integer
     * @param map
     * @param criteria
     * @return
     * @throws Exception
     */
    private VAccountInfoExample.Criteria eqCriteria(Map<String,Object> map,VAccountInfoExample.Criteria criteria) throws Exception{
        Class clz=criteria.getClass();
        Method[] methods=clz.getMethods();
        for(Map.Entry<String,Object> entity:map.entrySet()){
            String key=entity.getKey();
            Object value=entity.getValue();
            if(value==null) continue;
            if(key.startsWith("eq_")){
                String tmp=key.replace("eq_","");
                tmp=String.format("and%sEqualto",tmp);
                for(Method item:methods){
                    if(tmp.equalsIgnoreCase(item.getName())){
                        //参数类型 ,都只有一个参数
                        Class<?> cl[]=item.getParameterTypes();
                        String parasType=cl[0].getSimpleName();
                        switch (parasType){
                            case "Long":criteria= (VAccountInfoExample.Criteria)item.invoke(criteria,Long.valueOf(value.toString()));break;
                            case "Integer":criteria= (VAccountInfoExample.Criteria)item.invoke(criteria,Integer.valueOf(value.toString()));break;
                            default:criteria= (VAccountInfoExample.Criteria)item.invoke(criteria,value.toString());break;
                        }
                    }
                }

            }
        }
        return criteria;
    }


    /**
     * 参数只能是String
     * @param map
     * @param criteria
     * @return
     * @throws Exception
     */
    private VAccountInfoExample.Criteria lkCriteria(Map<String,Object> map,VAccountInfoExample.Criteria criteria) throws Exception{
        Class clz=criteria.getClass();
        Method[] methods=clz.getMethods();
        for(Map.Entry<String,Object> entity:map.entrySet()){
            String key=entity.getKey();
            Object value=entity.getValue();
            if(value==null) continue;
            if(key.startsWith("lk_")){
                String tmp=key.replace("lk_","");
                tmp=String.format("and%sLike",tmp);
                for(Method item:methods){
                    if(tmp.equalsIgnoreCase(item.getName())){
                        criteria= (VAccountInfoExample.Criteria)item.invoke(criteria,"%"+value+"%");
                    }
                }
            }
        }
        return criteria;
    }

    /**
     * 只能是时间类型
     * @param map
     * @param criteria
     * @return
     * @throws Exception
     */
    private VAccountInfoExample.Criteria btCriteria(Map<String,Object> map,VAccountInfoExample.Criteria criteria) throws Exception{
        Class clz=criteria.getClass();
        Method[] methods=clz.getMethods();
        for(Map.Entry<String,Object> entity:map.entrySet()){
            String key=entity.getKey();
            Object value=entity.getValue();
            if(value==null) continue;
            String[] sValue=value.toString().split(",");
            if(key.startsWith("bt_")){
                String tmp=key.replace("bt_","");
                String tmp1=String.format("and%sGreaterThanOrEqualTo",tmp);
                String tmp2=String.format("and%sLessThan",tmp);

                for(Method item:methods){
                    if(tmp1.equalsIgnoreCase(item.getName())){
                        //有开始时间
                        if(StringUtils.isNotBlank(sValue[0])){
                            criteria= (VAccountInfoExample.Criteria)item.invoke(criteria, UtilHelper.parseDateYMD(sValue[0]));
                        }
                    }
                    if(tmp2.equalsIgnoreCase(item.getName())){
                        if(sValue.length==2&&StringUtils.isNotBlank(sValue[1])){
                            criteria= (VAccountInfoExample.Criteria)item.invoke(criteria,UtilHelper.parseDateYMD(sValue[1]));
                        }
                    }
                }
            }
        }
        return criteria;
    }



    private VAccountInfoExample construct(Map<String,Object> map) {
        try {
            VAccountInfoExample example = new VAccountInfoExample();
            VAccountInfoExample.Criteria criteria = example.createCriteria();
            criteria = this.eqCriteria(map, criteria);
            criteria = this.lkCriteria(map, criteria);
            criteria=this.btCriteria(map,criteria);
            return example;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new HunterException("查询错误");
        }
    }


    @Override
    public PageParams<VAccountInfo> list(PageParams<VAccountInfo> pageParams) {
        //查询参数
        VAccountInfoExample example=this.construct(pageParams.getParams());
        //排序
        String strOrder=String.format("%s %s",pageParams.getOrderBy(),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public List<VAccountInfo> listAll() {
        VAccountInfoExample example=new VAccountInfoExample();
        example.setOrderByClause("id desc");
        return this.selectByExample(example);
    }

    @Override
    public VAccountInfo findById(Long id) {
        VAccountInfoExample example = new VAccountInfoExample();
        example.createCriteria().andIdEqualTo(id);
        List<VAccountInfo> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }
}
