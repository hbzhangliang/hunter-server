package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.TSysConfigService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.UtilHelper;
import cn.com.cubic.platform.utils.global.GlobalHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/13.
 **/
@Service
public class TSysConfigServiceImpl extends BaseServiceImpl<TSysConfig,TSysConfigExample> implements TSysConfigService {

    private final static Logger log = LoggerFactory.getLogger(TSysConfigServiceImpl.class);


    @Override
    public PageParams<TSysConfig> list(PageParams<TSysConfig> pageParams) {
        //查询参数
        TSysConfigExample example=new TSysConfigExample();
        //排序
        String strOrder=String.format("%s %s", UtilHelper.camelToUnderline(pageParams.getOrderBy()),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public List<TSysConfig> listAll() {
        TSysConfigExample example=new TSysConfigExample();
        example.setOrderByClause("seq is null,seq");
        return this.selectByExample(example);
    }

    @Override
    public TSysConfig findById(Long id) {
        TSysConfigExample example = new TSysConfigExample();
        example.createCriteria().andIdEqualTo(id);
        List<TSysConfig> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public TSysConfig findByCode(String code) {
        TSysConfigExample example = new TSysConfigExample();
        example.createCriteria().andCodeEqualTo(code);
        List<TSysConfig> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TSysConfigExample example = new TSysConfigExample();
        example.createCriteria().andIdIn(ids);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TSysConfig bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {
            TSysConfigExample example = new TSysConfigExample();
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
    public Boolean saveOrUpdateNoAccount(TSysConfig bean) {
        Date dt=new Date();
        if (null != bean.getId()) {
            TSysConfigExample example = new TSysConfigExample();
            example.createCriteria().andIdEqualTo(bean.getId());
            bean.setModifyTime(dt);
            this.updateByExampleSelective(bean, example);
        } else {
            bean.setCreateTime(dt);
            this.insert(bean);
        }
        return true;
    }
}
