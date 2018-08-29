package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TBizCareer;
import cn.com.cubic.platform.hunter.mysql.entity.TSysAccount;
import cn.com.cubic.platform.hunter.mysql.services.SysAccountService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/7/10.
 **/
@Validated
@Controller
@RequestMapping(value = "/account",produces = "application/json; charset=utf-8")
@ResponseBody
public class AccountController{

    @Autowired
    private SysAccountService accountService;

    @RequestMapping(value = "/check")
    public Object check(@RequestBody Map<String,String> map, HttpServletResponse response){
        String account=map.get("account"),pwd=map.get("pwd");
        return accountService.checkLogin(account,pwd,response);
    }

    @RequestMapping(value = "/check_info")
    public Object checkInfo(@RequestBody Map<String,String> map, HttpServletResponse response){
        String account=map.get("account"),pwd=map.get("pwd");
        return accountService.checkLoginBackInfo(account,pwd,response);
    }

    @RequestMapping(value = "/account_info")
    public Object userInfo(HttpServletRequest request){
        return accountService.checkLoginInfo(request);
    }


    @RequestMapping(value = "/logout")
    public Object logout(HttpServletRequest request, HttpServletResponse response){
        return accountService.checkLogout(request,response);
    }


    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<TSysAccount> pageParams){
        return accountService.list(pageParams);
    }


    @RequestMapping(value = "/list-all")
    public Object listAll(){
        return accountService.listAll();
    }

    @RequestMapping(value = "/save")
    public Object save(@RequestBody TSysAccount bean){
        return accountService.saveOrUpdate(bean);
    }

    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return accountService.findById(id);
    }

    @RequestMapping(value = "/del")
    public Object del(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return accountService.del(delIds);
    }


}
