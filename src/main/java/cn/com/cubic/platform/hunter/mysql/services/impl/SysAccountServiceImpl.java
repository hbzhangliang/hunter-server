package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.TBizCareerExample;
import cn.com.cubic.platform.hunter.mysql.entity.TSysAccount;
import cn.com.cubic.platform.hunter.mysql.entity.TSysAccountExample;
import cn.com.cubic.platform.hunter.mysql.services.SysAccountService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.CodeUtils;
import cn.com.cubic.platform.utils.CookieUtils;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.RedisUtils;
import cn.com.cubic.platform.utils.UtilHelper;
import cn.com.cubic.platform.utils.global.GlobalHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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


    /**
     * 参数 只能是 String，Long，Integer
     * @param map
     * @param criteria
     * @return
     * @throws Exception
     */
    private TSysAccountExample.Criteria eqCriteria(Map<String,Object> map,TSysAccountExample.Criteria criteria) throws Exception{
        Class clz=criteria.getClass();
        Method[] methods=clz.getMethods();
        for(Map.Entry<String,Object> entity:map.entrySet()){
            String key=entity.getKey();
            Object value=entity.getValue();
            if(key.startsWith("eq_")){
                String tmp=key.replace("eq_","");
                tmp="and"+tmp+"equalto";
                for(Method item:methods){
                    if(tmp.equalsIgnoreCase(item.getName())){
                        //参数类型 ,都只有一个参数
                        Class<?> cl[]=item.getParameterTypes();
                        String parasType=cl[0].getSimpleName();
                        switch (parasType){
                            case "Long":criteria= (TSysAccountExample.Criteria)item.invoke(criteria,Long.valueOf(value.toString()));break;
                            case "Integer":criteria= (TSysAccountExample.Criteria)item.invoke(criteria,Integer.valueOf(value.toString()));break;
                            default:criteria= (TSysAccountExample.Criteria)item.invoke(criteria,value.toString());break;
                        }
                    }
                }

            }
        }
        return criteria;
    }


    /**
     * 参数只能是String
     * @param map
     * @param criteria
     * @return
     * @throws Exception
     */
    private TSysAccountExample.Criteria lkCriteria(Map<String,Object> map,TSysAccountExample.Criteria criteria) throws Exception{
        Class clz=criteria.getClass();
        Method[] methods=clz.getMethods();
        for(Map.Entry<String,Object> entity:map.entrySet()){
            String key=entity.getKey();
            Object value=entity.getValue();
            if(key.startsWith("lk_")){
                String tmp=key.replace("lk_","");
                tmp="and"+tmp+"like";
                for(Method item:methods){
                    if(tmp.equalsIgnoreCase(item.getName())){
                        criteria= (TSysAccountExample.Criteria)item.invoke(criteria,"%"+value+"%");
                    }
                }
            }
        }
        return criteria;
    }



    /**
     * eq相等  lk 相似 lt 小于  gt 大于
     * @param map
     * @return
     */
    @Override
    public TSysAccountExample construct(Map<String,Object> map) {
        try {
            TSysAccountExample example = new TSysAccountExample();
            TSysAccountExample.Criteria criteria = example.createCriteria();
            criteria = this.eqCriteria(map, criteria);
            criteria = this.lkCriteria(map, criteria);
            return example;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new HunterException("查询错误");
        }
    }


    @Override
    public List<TSysAccount> listAll() {
        TSysAccountExample example=new TSysAccountExample();
        example.setOrderByClause("id desc");
        return this.selectByExample(example);
    }

    @Override
    public PageParams<TSysAccount> list(PageParams<TSysAccount> pageParams) {
        //查询参数
        TSysAccountExample example=this.construct(pageParams.getParams());
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
     * 缓存时间 一天
     * @param id
     */
    @Override
    public void tokenGenerete(Long id,HttpServletResponse response) {
        TSysAccount account=this.findById(id);
        String token=String.format("token_%s",id);
        String enToken=CodeUtils.getEncryptedToken(token);
        String redisKey="token_encode";
        redisUtils.setObj(token,account,redisKey);
        CookieUtils.writeCookie(response,this.ENCODE_TOKEN_PARAM_NAME,enToken, UtilHelper.timeUtlToInt(redisKey));
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

            this.cleanGlobal(token);

            redisUtils.delKeys(token);
            CookieUtils.writeCookie(response,this.ENCODE_TOKEN_PARAM_NAME,null);
            return true;
        }
        return false;
    }

    /**
     * 将global 的tokenSet 清除 此request的token，将requestHolder 清除
     * @param token
     */
    @Override
    public void cleanGlobal(String token) {
        GlobalHolder.getTokenSet().remove(token);
        GlobalHolder.remove();
    }
}
