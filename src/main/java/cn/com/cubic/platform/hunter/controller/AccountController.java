package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.services.SysAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping(value = "/listall")
    public Object list(){
        return accountService.listAll();
    }

}
