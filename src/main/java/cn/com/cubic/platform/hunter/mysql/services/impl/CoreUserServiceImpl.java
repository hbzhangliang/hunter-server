package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.CoreUser;
import cn.com.cubic.platform.hunter.mysql.entity.CoreUserExample;
import cn.com.cubic.platform.hunter.mysql.mapper.CoreUserMapper;
import cn.com.cubic.platform.hunter.mysql.services.CoreUserService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.UtilHelper;
import cn.com.cubic.platform.utils.resp.HunterBaseResponse;
import cn.com.flaginfo.platform.api.common.base.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/6/22.
 **/

@Service
public class CoreUserServiceImpl extends BaseServiceImpl<CoreUser,CoreUserExample> implements CoreUserService{

    private final static Logger log = LoggerFactory.getLogger(CoreUserServiceImpl.class);


    @Override
    public CoreUserExample construct(CoreUser coreUser) {
        CoreUserExample example=new CoreUserExample();
        if(null!=coreUser){
            CoreUserExample.Criteria criteria = example.createCriteria();
            if(null!=coreUser.getId()){
                criteria.andIdEqualTo(coreUser.getId());
            }
            if(null!=coreUser.getName()) {
                criteria.andNameLike(coreUser.getName());
            }
        }
        return example;
    }

    @Override
    public PageParams<CoreUser> list(PageParams<CoreUser> pageParams) {
        //查询参数
        CoreUserExample example=this.construct(pageParams.getFilter());
        //排序
        String strOrder=String.format("%s %s", UtilHelper.camelToUnderline(pageParams.getOrderBy()),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public CoreUser findById(Long id) {
        CoreUserExample example = new CoreUserExample();
        example.createCriteria().andIdEqualTo(id);
        List<CoreUser> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        CoreUserExample example = new CoreUserExample();
        example.createCriteria().andIdIn(ids);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(CoreUser coreUser) {
        if (null != coreUser.getId()) {
            CoreUserExample example = new CoreUserExample();
            example.createCriteria().andIdEqualTo(coreUser.getId());
            this.updateByExampleSelective(coreUser, example);
        } else {
            this.insert(coreUser);
        }
        return true;
    }

    @Override
    public CoreUser selectById(Long id) {
        return coreUserMapper.selectById(id);
    }

    @Override
    public List<CoreUser> callProc() {
        return coreUserMapper.storeProc();
    }

    @Autowired
    private CoreUserMapper coreUserMapper;
}
