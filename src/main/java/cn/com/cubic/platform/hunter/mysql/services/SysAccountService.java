package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TSysAccount;
import cn.com.cubic.platform.hunter.mysql.entity.TSysAccountExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/7/10.
 **/

public interface SysAccountService  extends BaseService<TSysAccount,TSysAccountExample>{

    TSysAccountExample construct(TSysAccount account);

    List<TSysAccount> listAll();

    PageParams<TSysAccount> list(PageParams<TSysAccount> pageParams);

    TSysAccount findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TSysAccount account);


    //登录接口
    Boolean checkLogin(String account,String pwd,HttpServletResponse response);

    TSysAccount checkLoginBackInfo(String account, String pwd, HttpServletResponse response);

    Boolean checkLogout(HttpServletRequest request,HttpServletResponse response);

    void tokenGenerete(Long id, HttpServletResponse response);

    TSysAccount checkLoginInfo(HttpServletRequest request);

}
