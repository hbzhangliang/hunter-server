package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.VProjectDocService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.UtilHelper;
import cn.com.cubic.platform.utils.global.GlobalHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/9/28.
 **/
@Service
public class VProjectDocServiceImpl extends BaseServiceImpl<VProjectDoc,VProjectDocExample> implements VProjectDocService {

    private final static Logger log = LoggerFactory.getLogger(VProjectDocServiceImpl.class);



    /**
     * 参数  qu全部在一起处理
     * @param map
     * @param criteria
     * @return
     * @throws Exception
     */
    private VProjectDocExample.Criteria constructCriteria(Map<String,Object> map, VProjectDocExample.Criteria criteria) throws Exception{
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
                            case "Long":criteria= (VProjectDocExample.Criteria)item.invoke(criteria,Long.valueOf(value.toString()));break;
                            case "Integer":criteria= (VProjectDocExample.Criteria)item.invoke(criteria,Integer.valueOf(value.toString()));break;
                            case "Boolean":criteria=(VProjectDocExample.Criteria)item.invoke(criteria,Boolean.valueOf(value.toString()));break;
                            default:criteria= (VProjectDocExample.Criteria)item.invoke(criteria,value.toString());break;
                        }
                    }
                }
            }
            else if(key.startsWith("lk_")){
                String tmp=key.substring(3);
                tmp=String.format("and%sLike",tmp);
                for(Method item:methods){
                    if(tmp.equalsIgnoreCase(item.getName())){
                        criteria= (VProjectDocExample.Criteria)item.invoke(criteria,"%"+value+"%");
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
                        criteria = (VProjectDocExample.Criteria) item.invoke(criteria, UtilHelper.parseDateYMD(value.toString()));
                    }
                }
            }
            else {
                continue;
            }
        }
        return criteria;
    }


    private VProjectDocExample construct(Map<String,Object> map,Boolean ownerFlag) {
        try {
            VProjectDocExample example = new VProjectDocExample();
            VProjectDocExample.Criteria criteria = example.createCriteria();
            criteria = this.constructCriteria(map, criteria);
            if(ownerFlag){
                //只能看到自己的
                TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
                criteria.andOwnerEqualTo(user.getId());
            }
            return example;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new HunterException("查询错误");
        }
    }


    @Override
    public PageParams<VProjectDoc> list(PageParams<VProjectDoc> pageParams) {
        //查询参数
        VProjectDocExample example=this.construct(pageParams.getParams(),false);
        //排序
        String strOrder=String.format("%s %s", UtilHelper.camelToUnderline(pageParams.getOrderBy()),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public VProjectDoc findById(Long id) {
        VProjectDocExample example = new VProjectDocExample();
        example.createCriteria().andIdEqualTo(id);
        List<VProjectDoc> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            return null;
        }
        return list.get(0);
    }
}
