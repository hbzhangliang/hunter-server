package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TLogLogin;
import cn.com.cubic.platform.hunter.mysql.entity.TSysTeam;
import cn.com.cubic.platform.hunter.mysql.services.TLogLoginService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/9/6.
 **/
@Validated
@Controller
@RequestMapping(value = "/log-login",produces = "application/json; charset=utf-8")
@ResponseBody
public class LogLoginController {

    @Autowired
    private TLogLoginService logLoginService;

    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<TLogLogin> pageParams){
        return logLoginService.list(pageParams);
    }


    @RequestMapping(value = "/list-all")
    public Object listAll(){
        return logLoginService.listAll();
    }

    @RequestMapping(value = "/save")
    public Object save(@RequestBody TLogLogin bean){
        return logLoginService.saveOrUpdate(bean);
    }

    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return logLoginService.findById(id);
    }

    @RequestMapping(value = "/del")
    public Object del(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return logLoginService.del(delIds);
    }


}
