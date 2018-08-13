package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.TSysAccount;
import cn.com.cubic.platform.hunter.mysql.entity.TSysAccountExample;
import cn.com.cubic.platform.hunter.mysql.services.SysAccountService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.CodeUtils;
import cn.com.cubic.platform.utils.CookieUtils;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.RedisUtils;
import cn.com.cubic.platform.utils.global.GlobalHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/7/10.
 **/
@Service
public class SysAccountServiceImpl extends BaseServiceImpl<TSysAccount,TSysAccountExample> implements SysAccountService {

    private final static Logger log = LoggerFactory.getLogger(SysAccountServiceImpl.class);

    //加密后的
    private static final String ENCODE_TOKEN_PARAM_NAME="encode_token_cookie";

    @Autowired
    private RedisUtils redisUtils;

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
    public List<TSysAccount> listAll() {
        return this.selectByExample(new TSysAccountExample());
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
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != account.getId()) {
            TSysAccountExample example = new TSysAccountExample();
            example.createCriteria().andIdEqualTo(account.getId());
            account.setModifyBy(user.getName());
            account.setModifyTime(dt);
            this.updateByExampleSelective(account, example);
        } else {
            account.setCreateBy(user.getName());
            account.setCreateTime(dt);
            this.insert(account);
        }
        return true;
    }


    @Override
    public Boolean checkLogin(String account, String pwd,HttpServletResponse response) {
        log.info("check login by account[{}],pwd[{}]",account,pwd);
        TSysAccountExample example=new TSysAccountExample();
        pwd= CodeUtils.getEncryptedCode(pwd);

        example.createCriteria().andAccountEqualTo(account).andPwdEqualTo(pwd);
        List<TSysAccount> list=this.selectByExample(example);
        if(null!=list&&list.size()==1){
            log.info("check login by account[{}],pwd[{}],login sucess",account,pwd);

            this.tokenGenerete(list.get(0).getId(),response);

            return true;
        }
        log.error("check login by account[{}],pwd[{}],login fail",account,pwd);
        return false;
    }


    @Override
    public TSysAccount checkLoginBackInfo(String account, String pwd, HttpServletResponse response) {
        log.info("check login by account[{}],pwd[{}]",account,pwd);
        TSysAccountExample example=new TSysAccountExample();
        pwd= CodeUtils.getEncryptedCode(pwd);
        example.createCriteria().andAccountEqualTo(account).andPwdEqualTo(pwd);
        List<TSysAccount> list=this.selectByExample(example);
        if(null!=list&&list.size()==1){
            log.info("check login by account[{}],pwd[{}],login sucess",account,pwd);
            this.tokenGenerete(list.get(0).getId(),response);
            return list.get(0);
        }
        log.error("check login by account[{}],pwd[{}],login fail",account,pwd);
        return null;
    }

    /**
     * 根据账号id  获取账号信息，生成token，写入 cookie
     * @param id
     */
    @Override
    public void tokenGenerete(Long id,HttpServletResponse response) {
        TSysAccount account=this.findById(id);
        String token=String.format("token_%s",id);
        String enToken=CodeUtils.getEncryptedToken(token);
        String redisKey="token_encode";
        redisUtils.setObj(token,account,redisKey);
        CookieUtils.writeCookie(response,this.ENCODE_TOKEN_PARAM_NAME,enToken);
    }

    @Override
    public TSysAccount checkLoginInfo(HttpServletRequest request) {
        String encodeToken= CookieUtils.getCookie(request,ENCODE_TOKEN_PARAM_NAME);
        if(StringUtils.isNotBlank(encodeToken)) {
            String token = CodeUtils.getDecodedToken(encodeToken);
            log.info("获取到用户token:[{}]",token);
            return (TSysAccount)redisUtils.getObj(token);
        }
        log.warn("未获取到用户token");
        return null;
    }

    @Override
    public Boolean checkLogout(HttpServletRequest request, HttpServletResponse response) {
        String encodeToken= CookieUtils.getCookie(request,ENCODE_TOKEN_PARAM_NAME);
        if(StringUtils.isNotBlank(encodeToken)){
            String token= CodeUtils.getDecodedToken(encodeToken);
            redisUtils.delKeys(token);
            CookieUtils.writeCookie(response,this.ENCODE_TOKEN_PARAM_NAME,null);
            return true;
        }
        return false;
    }
}
