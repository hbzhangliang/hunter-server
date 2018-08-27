package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.TSysPositionService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.UtilHelper;
import cn.com.cubic.platform.utils.global.GlobalHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/8/27.
 **/
@Service
public class TSysPositionServiceImpl extends BaseServiceImpl<TSysPosition,TSysPositionExample> implements TSysPositionService {


    private final static Logger log = LoggerFactory.getLogger(TSysPositionServiceImpl.class);


    @Override
    public PageParams<TSysPosition> list(PageParams<TSysPosition> pageParams) {
        //查询参数
        TSysPositionExample example=new TSysPositionExample();
        TSysPositionExample.Criteria criteria=example.createCriteria();
        //查询参数 根据名称和code 查询
        TSysPosition quearyBean=pageParams.getFilter();
        if(null!=quearyBean){
            if(StringUtils.isNotEmpty(quearyBean.getName())){
                criteria=criteria.andNameLike("%"+quearyBean.getName()+"%");
            }
            if(StringUtils.isNotEmpty(quearyBean.getCode())){
                criteria=criteria.andCodeLike("%"+quearyBean.getCode()+"%");
            }
        }
        //排序
        String strOrder=String.format("%s %s", UtilHelper.camelToUnderline(pageParams.getOrderBy()),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public List<TSysPosition> listAll() {
        TSysPositionExample example=new TSysPositionExample();
        example.setOrderByClause("seq is null,seq");
        return this.selectByExample(example);
    }

    @Override
    public TSysPosition findById(Long id) {
        TSysPositionExample example = new TSysPositionExample();
        example.createCriteria().andIdEqualTo(id);
        List<TSysPosition> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TSysPositionExample example = new TSysPositionExample();
        example.createCriteria().andIdIn(ids);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TSysPosition bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {
            TSysPositionExample example = new TSysPositionExample();
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
