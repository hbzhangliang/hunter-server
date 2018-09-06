package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.TBizShareTalentService;
import cn.com.cubic.platform.hunter.mysql.services.TBizTalentService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.hunter.mysql.vo.TalentVo;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.UtilHelper;
import cn.com.cubic.platform.utils.global.GlobalHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/9/5.
 **/
@Service
public class TBizTalentServiceImpl extends BaseServiceImpl<TBizTalent,TBizTalentExample> implements TBizTalentService {

    private final static Logger log = LoggerFactory.getLogger(TBizTalentServiceImpl.class);

    /**
     * 参数  qu全部在一起处理
     * @param map
     * @param criteria
     * @return
     * @throws Exception
     */
    private TBizTalentExample.Criteria constructCriteria(Map<String,Object> map, TBizTalentExample.Criteria criteria) throws Exception{
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
                            case "Long":criteria= (TBizTalentExample.Criteria)item.invoke(criteria,Long.valueOf(value.toString()));break;
                            case "Integer":criteria= (TBizTalentExample.Criteria)item.invoke(criteria,Integer.valueOf(value.toString()));break;
                            default:criteria= (TBizTalentExample.Criteria)item.invoke(criteria,value.toString());break;
                        }
                    }
                }
            }
            else if(key.startsWith("lk_")){
                String tmp=key.substring(3);
                tmp=String.format("and%sLike",tmp);
                for(Method item:methods){
                    if(tmp.equalsIgnoreCase(item.getName())){
                        criteria= (TBizTalentExample.Criteria)item.invoke(criteria,"%"+value+"%");
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
                        criteria = (TBizTalentExample.Criteria) item.invoke(criteria, UtilHelper.parseDateYMD(value.toString()));
                    }
                }
            }
            else {
                continue;
            }
        }
        return criteria;
    }


    private TBizTalentExample construct(Map<String,Object> map) {
        try {
            TBizTalentExample example = new TBizTalentExample();
            TBizTalentExample.Criteria criteria = example.createCriteria();
            criteria = this.constructCriteria(map, criteria);
            return example;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new HunterException("查询错误");
        }
    }

    @Override
    public PageParams<TBizTalent> list(PageParams<TBizTalent> pageParams) {
        //查询参数
        TBizTalentExample example=this.construct(pageParams.getParams());
        //排序
        String strOrder=String.format("%s %s",UtilHelper.camelToUnderline(pageParams.getOrderBy()),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public List<TBizTalent> listAll() {
        TBizTalentExample example=new TBizTalentExample();
        example.setOrderByClause("seq is null,seq");
        return this.selectByExample(example);
    }

    @Override
    public TBizTalent findById(Long id) {
        TBizTalentExample example = new TBizTalentExample();
        example.createCriteria().andIdEqualTo(id);
        List<TBizTalent> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TBizTalentExample example = new TBizTalentExample();
        example.createCriteria().andIdIn(ids);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TalentVo bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {
            TBizTalentExample example = new TBizTalentExample();
            example.createCriteria().andIdEqualTo(bean.getId());
            bean.setModifyBy(user.getName());
            bean.setModifyTime(dt);
            this.updateByExampleSelective(bean, example);

            //删除共享数据
            String sql=String.format("delete from t_biz_share_talent where talent_id=%s",bean.getId());
            jdbcTemplate.update(sql);

        } else {
            bean.setCreateBy(user.getName());
            bean.setCreateTime(dt);
            this.insert(bean);
        }

        //添加数据
        if(StringUtils.isNotEmpty(bean.getShareValue())){
            for(int i=0;i<bean.getShareValue().split(",").length;i++){
                String value=bean.getShareValue().split(",")[i];
                TBizShareTalent shareTalent=new TBizShareTalent();
                shareTalent.setTalentId(bean.getId());
                shareTalent.setShareType(UtilHelper.getShareType(value));
                shareTalent.setShareValue(UtilHelper.cleanShareType(value));
                shareTalent.setShareLabel(bean.getShareLabel().split(",")[i]);
                shareTalentService.saveOrUpdate(shareTalent);
            }
        }
        return true;
    }






    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    private TBizShareTalentService shareTalentService;
}
