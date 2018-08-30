package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TSysAccount;
import cn.com.cubic.platform.hunter.mysql.entity.VAccountInfo;
import cn.com.cubic.platform.hunter.mysql.services.SysAccountService;
import cn.com.cubic.platform.hunter.mysql.services.VAccountInfoService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/8/29.
 **/
@Validated
@Controller
@RequestMapping(value = "/vaccount",produces = "application/json; charset=utf-8")
@ResponseBody
public class VAccountController {


    @Autowired
    private VAccountInfoService vAccountInfoService;

    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<VAccountInfo> pageParams){
        return vAccountInfoService.list(pageParams);
    }


    @RequestMapping(value = "/list-all")
    public Object listAll(){
        return vAccountInfoService.listAll();
    }


    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return vAccountInfoService.findById(id);
    }



}
