package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.TSysDictionaryService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.global.GlobalHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/8/16.
 **/
@Service
public class TSysDictionaryServiceImpl extends BaseServiceImpl<TSysDictionary,TSysDictionaryExample> implements TSysDictionaryService {

    private final static Logger log = LoggerFactory.getLogger(TSysDictionaryServiceImpl.class);

    /**
     * 分页查询的是根
     * @param pageParams
     * @return
     */
    @Override
    public PageParams<TSysDictionary> list(PageParams<TSysDictionary> pageParams) {
        //查询参数
        TSysDictionaryExample example=new TSysDictionaryExample();
        TSysDictionaryExample.Criteria criteria=example.createCriteria();
        //查询参数 根据名称和code 查询
        TSysDictionary quearyBean=pageParams.getFilter();
        if(null!=quearyBean){
            if(StringUtils.isNotEmpty(quearyBean.getName())){
                criteria=criteria.andNameLike("%"+quearyBean.getName()+"%");
            }
            if(StringUtils.isNotEmpty(quearyBean.getCode())){
                criteria=criteria.andCodeLike("%"+quearyBean.getCode()+"%");
            }
        }
        criteria=criteria.andParentIdIsNull();
        //排序
        String strOrder=String.format("%s %s",pageParams.getOrderBy(),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    /**
     * 根据pId查询的子集
     * @param pId
     * @return
     */
    @Override
    public List<TSysDictionary> list(Long pId) {
        TSysDictionaryExample example=new TSysDictionaryExample();
        example.createCriteria().andParentIdEqualTo(pId);
        example.setOrderByClause("seq is null,seq");
        return this.selectByExample(example);
    }

    @Override
    public List<TSysDictionary> listAll() {
        TSysDictionaryExample example=new TSysDictionaryExample();
        example.createCriteria().andParentIdIsNull();
        example.setOrderByClause("seq is null,seq");
        return this.selectByExample(example);
    }

    @Override
    public TSysDictionary findById(Long id) {
        TSysDictionaryExample example = new TSysDictionaryExample();
        example.createCriteria().andIdEqualTo(id);
        List<TSysDictionary> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TSysDictionaryExample example = new TSysDictionaryExample();
        example.createCriteria().andParentIdIn(ids);
        this.deleteByExample(example);

        example.clear();
        example.createCriteria().andIdIn(ids);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TSysDictionary bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {
            TSysDictionaryExample example = new TSysDictionaryExample();
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
