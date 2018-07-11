package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TSysAccount;
import cn.com.cubic.platform.hunter.mysql.entity.TSysAccountExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.http.HttpResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    void tokenGenerete(Long id, HttpServletResponse response);
}
