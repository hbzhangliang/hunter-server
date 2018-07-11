package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.TSysAccount;
import cn.com.cubic.platform.hunter.mysql.entity.TSysAccountExample;
import cn.com.cubic.platform.hunter.mysql.services.SysAccountService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.CodeUtils;
import cn.com.cubic.platform.utils.Exception.HunterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/7/10.
 **/
@Service
public class SysAccountServiceImpl extends BaseServiceImpl<TSysAccount,TSysAccountExample> implements SysAccountService {

    private final static Logger log = LoggerFactory.getLogger(SysAccountServiceImpl.class);

    @Override
    public TSysAccountExample construct(TSysAccount account) {
        TSysAccountExample example=new TSysAccountExample();
        if(null!=account){
            TSysAccountExample.Criteria criteria = example.createCriteria();
            if(null!=account.getId()){
                criteria.andIdEqualTo(account.getId());
            }
            if(null!=account.getName()) {
                criteria.andNameLike(account.getName());
            }
        }
        return example;
    }

    @Override
    public PageParams<TSysAccount> list(PageParams<TSysAccount> pageParams) {
        //查询参数
        TSysAccountExample example=this.construct(pageParams.getFilter());
        //排序
        String strOrder=String.format("%s %s",pageParams.getOrderBy(),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public TSysAccount findById(Long id) {
        TSysAccountExample example = new TSysAccountExample();
        example.createCriteria().andIdEqualTo(id);
        List<TSysAccount> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    @Override
    public Boolean del(List<Long> ids) {
        TSysAccountExample example = new TSysAccountExample();
        example.createCriteria().andIdIn(ids);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TSysAccount account) {
        if (null != account.getId()) {
            TSysAccountExample example = new TSysAccountExample();
            example.createCriteria().andIdEqualTo(account.getId());
            this.updateByExampleSelective(account, example);
        } else {
            this.insert(account);
        }
        return true;
    }


    @Override
    public Boolean checkLogin(String account, String pwd) {
        log.info("check login by account[{}],pwd[{}]",account,pwd);
        TSysAccountExample example=new TSysAccountExample();
        pwd= CodeUtils.getEncryptedCode(pwd);
        example.createCriteria().andAccountEqualTo(account).andPwdEqualTo(pwd);
        List<TSysAccount> list=this.selectByExample(example);
        if(null!=list&&list.size()==1){
            log.info("check login by account[{}],pwd[{}],login sucess",account,pwd);
            return true;
        }
        log.error("check login by account[{}],pwd[{}],login fail",account,pwd);
        return false;
    }
}
