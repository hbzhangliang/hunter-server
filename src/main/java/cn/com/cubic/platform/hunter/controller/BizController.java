package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.CoreUser;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/7/10.
 **/
@Validated
@Controller
@RequestMapping(value = "/",produces = "application/json; charset=utf-8")
@ResponseBody
public class BizController {


    @RequestMapping(value = "/health")
    public Object health(){
        Map<String,String> map=new HashMap<>(5);
        map.put("server","hunter server");
        map.put("status","running");
        return map;
    }

}
