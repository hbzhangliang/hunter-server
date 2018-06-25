package cn.com.cubic.platform.a_book.controller;


import cn.com.cubic.platform.a_book.mysql.entity.CoreUser;
import cn.com.cubic.platform.a_book.mysql.services.CoreUserService;
import cn.com.cubic.platform.a_book.mysql.vo.PageForm;
import cn.com.cubic.platform.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Created by liang_zhang on 2017/9/25.
 */
@Validated
@Controller
@RequestMapping(value = "/test",produces = "application/json; charset=utf-8")
public class TestController {



    private static final Logger log = LoggerFactory.getLogger(TestController.class);


    /**
     * mysql 操作
     * @param pageForm
     * @return
     */

    @RequestMapping(value = "/1")
    @ResponseBody
    public Object test1(@RequestBody PageForm pageForm){
        return coreUserService.list(pageForm);

    }

    @RequestMapping(value = "/2")
    @ResponseBody
    public Object test2(@RequestBody Map<String,Object> map){
        Long id=Long.valueOf(map.get("id").toString());
        return coreUserService.findById(id);
    }

    @RequestMapping(value = "/3")
    @ResponseBody
    public Object test3(@RequestBody Map<String,Object> map){
        List<Long> ids=(List) map.get("ids");
        return coreUserService.del(ids);
    }

    @RequestMapping(value = "/4")
    @ResponseBody
    public Object test4(@RequestBody CoreUser coreUser){
        coreUserService.saveOrUpdate(coreUser);
        return coreUserService.selectByExample(null);
    }


    /**
     * redis 操作
     */

    @RequestMapping(value = "/5")
    @ResponseBody
    public Object test5(@RequestBody Map<String,String> map){
        String v=map.get("key");
        redisUtils.setObj("aa",v,"COMMON");
        return redisUtils.getObj("aa");
    }




    @Autowired
    private RedisUtils redisUtils;



    @Autowired
    private CoreUserService coreUserService;

}