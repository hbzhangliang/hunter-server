package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.TBizAttachmentService;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.global.GlobalHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/8/31.
 **/
@Service
public class TBizAttachmentServiceImpl extends BaseServiceImpl<TBizAttachment,TBizAttachmentExample> implements TBizAttachmentService {

    private final static Logger log = LoggerFactory.getLogger(TBizAttachmentServiceImpl.class);


    @Override
    public List<TBizAttachment> listAll(Long pId) {
        TBizAttachmentExample example=new TBizAttachmentExample();
        if(null!=pId){
            example.createCriteria().andPIdEqualTo(pId);
        }
        example.setOrderByClause("id asc");
        return this.selectByExample(example);
    }

    @Override
    public TBizAttachment findById(Long id) {
        TBizAttachmentExample example = new TBizAttachmentExample();
        example.createCriteria().andIdEqualTo(id);
        List<TBizAttachment> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TBizAttachmentExample example = new TBizAttachmentExample();
        example.createCriteria().andIdIn(ids);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TBizAttachment bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {
            TBizAttachmentExample example = new TBizAttachmentExample();
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
