package cn.com.cubic.platform.hunter.controller;


import cn.com.cubic.platform.hunter.mysql.entity.BaseEntity;
import cn.com.cubic.platform.hunter.mysql.entity.CoreUser;
import cn.com.cubic.platform.hunter.mysql.services.CoreUserService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;


/**
 * Created by liang_zhang on 2017/9/25.
 */
@Validated
@Controller
@RequestMapping(value = "/test",produces = "application/json; charset=utf-8")
@ResponseBody
public class TestController {



    private static final Logger log = LoggerFactory.getLogger(TestController.class);


    /**
     * mysql 操作
     * @param
     * @return
     */
    @RequestMapping(value = "/1")
    public Object test1(@RequestBody PageParams<CoreUser> pageParams){
        return  coreUserService.list(pageParams);
    }

    @RequestMapping(value = "/2")
    public Object test2(@RequestBody Map<String,Object> map){
        Long id=Long.valueOf(map.get("id").toString());
        return coreUserService.findById(id);
    }

    @RequestMapping(value = "/3")
    public Object test3(@RequestBody Map<String,Object> map){
        List<Long> ids=(List) map.get("ids");
        return coreUserService.del(ids);
    }

    @RequestMapping(value = "/4")
    public Object test4(@RequestBody CoreUser coreUser){
        coreUserService.saveOrUpdate(coreUser);
        return coreUser;
    }


    /**
     * redis 操作
     */

    @RequestMapping(value = "/5")
    public Object test5(@RequestBody Map<String,String> map){
        String v=map.get("key");
        redisUtils.setObj("aa",v,"COMMON");
        return redisUtils.getObj("aa");
    }


    @RequestMapping(value = "/6")
    public Object test6(){
        return new ModelAndView("index")
                .addObject("flag","1");
    }





    @Autowired
    private RedisUtils redisUtils;



    @Autowired
    private CoreUserService coreUserService;

}