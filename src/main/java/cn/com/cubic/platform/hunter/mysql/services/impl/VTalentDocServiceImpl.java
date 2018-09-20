package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.VShareTalentService;
import cn.com.cubic.platform.hunter.mysql.services.VTalentDocService;
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
 * Created by Liang.Zhang on 2018/9/20.
 **/
@Service
public class VTalentDocServiceImpl extends BaseServiceImpl<VTalentDoc,VTalentDocExample> implements VTalentDocService {

    private final static Logger log = LoggerFactory.getLogger(VTalentDocServiceImpl.class);



    /**
     * 参数  qu全部在一起处理
     * @param map
     * @param criteria
     * @return
     * @throws Exception
     */
    private VTalentDocExample.Criteria constructCriteria(Map<String,Object> map, VTalentDocExample.Criteria criteria) throws Exception{
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
                            case "Long":criteria= (VTalentDocExample.Criteria)item.invoke(criteria,Long.valueOf(value.toString()));break;
                            case "Integer":criteria= (VTalentDocExample.Criteria)item.invoke(criteria,Integer.valueOf(value.toString()));break;
                            case "Boolean":criteria=(VTalentDocExample.Criteria)item.invoke(criteria,Boolean.valueOf(value.toString()));break;
                            default:criteria= (VTalentDocExample.Criteria)item.invoke(criteria,value.toString());break;
                        }
                    }
                }
            }
            else if(key.startsWith("lk_")){
                String tmp=key.substring(3);
                tmp=String.format("and%sLike",tmp);
                for(Method item:methods){
                    if(tmp.equalsIgnoreCase(item.getName())){
                        criteria= (VTalentDocExample.Criteria)item.invoke(criteria,"%"+value+"%");
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
                        criteria = (VTalentDocExample.Criteria) item.invoke(criteria, UtilHelper.parseDateYMD(value.toString()));
                    }
                }
            }
            else {
                continue;
            }
        }
        return criteria;
    }


    private VTalentDocExample construct(Map<String,Object> map,Boolean ownerFlag) {
        try {
            VTalentDocExample example = new VTalentDocExample();
            VTalentDocExample.Criteria criteria = example.createCriteria();
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
    public PageParams<VTalentDoc> list(PageParams<VTalentDoc> pageParams) {
        //查询参数
        VTalentDocExample example=this.construct(pageParams.getParams(),true);
        //排序
        String strOrder=String.format("%s %s", UtilHelper.camelToUnderline(pageParams.getOrderBy()),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public VTalentDoc findById(Long id) {
        VTalentDocExample example = new VTalentDocExample();
        example.createCriteria().andIdEqualTo(id);
        List<VTalentDoc> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }
}
