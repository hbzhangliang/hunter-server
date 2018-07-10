package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.TSysCorp;
import cn.com.cubic.platform.hunter.mysql.entity.TSysCorpExample;
import cn.com.cubic.platform.hunter.mysql.services.SysCorpService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.Exception.HunterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/7/10.
 **/
@Service
public class SysCorpServiceImpl extends BaseServiceImpl<TSysCorp,TSysCorpExample> implements SysCorpService {

    private final static Logger log = LoggerFactory.getLogger(SysCorpServiceImpl.class);


    @Override
    public TSysCorpExample construct(TSysCorp corp) {
        TSysCorpExample example=new TSysCorpExample();
        if(null!=corp){
            TSysCorpExample.Criteria criteria = example.createCriteria();
            if(null!=corp.getId()){
                criteria.andIdEqualTo(corp.getId());
            }
            if(null!=corp.getName()) {
                criteria.andNameLike(corp.getName());
            }
        }
        return example;
    }

    @Override
    public PageParams<TSysCorp> list(PageParams<TSysCorp> pageParams) {
        //查询参数
        TSysCorpExample example=this.construct(pageParams.getFilter());
        //排序
        String strOrder=String.format("%s %s",pageParams.getOrderBy(),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public TSysCorp findById(Long id) {
        TSysCorpExample example = new TSysCorpExample();
        example.createCriteria().andIdEqualTo(id);
        List<TSysCorp> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TSysCorpExample example = new TSysCorpExample();
        example.createCriteria().andIdIn(ids);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TSysCorp corp) {
        if (null != corp.getId()) {
            TSysCorpExample example = new TSysCorpExample();
            example.createCriteria().andIdEqualTo(corp.getId());
            this.updateByExampleSelective(corp, example);
        } else {
            this.insert(corp);
        }
        return true;
    }
}
