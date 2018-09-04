package cn.com.cubic.platform.hunter.controller;


import cn.com.cubic.platform.hunter.mongo.models.User;
import cn.com.cubic.platform.hunter.mongo.repo.UserRepo;
import cn.com.cubic.platform.hunter.mysql.entity.CoreUser;
import cn.com.cubic.platform.hunter.mysql.entity.TSysDictionary;
import cn.com.cubic.platform.hunter.mysql.mapper.TSysDictionaryMapper;
import cn.com.cubic.platform.hunter.mysql.services.CoreUserService;
import cn.com.cubic.platform.hunter.mysql.services.TBizAttachmentService;
import cn.com.cubic.platform.hunter.mysql.services.TSysDictionaryService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.ComFiles;
import cn.com.cubic.platform.utils.RedisUtils;
import cn.com.cubic.platform.utils.XmlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
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
@ResponseBody
public class TestController extends BaseController{
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
        Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    int p=0;
                    System.out.println(15/p);
                }
                catch (Exception e){
                    e.printStackTrace();
                    log.error("run error");
                }
            }
        });
        thread.start();

        log.info(BaseController.getIp(this.request));

        return null;
    }


    @RequestMapping(value = "/7")
    public Object test7(@RequestBody Map<String,String> map){
        String strId=map.get("id");
        return coreUserService.selectById(Long.valueOf(strId));
    }


    @RequestMapping(value = "/8")
    public Object test8(){
        return coreUserService.callProc();
    }



    @RequestMapping(value = "/9")
    public Object test9(){
        return userRepo.list();
    }

    @RequestMapping(value = "/10")
    public Object test10(){
        List<User> users=new ArrayList<>(10);
        for(int i=0;i<10;i++){
            User user=new User();
            user.setName("第"+i+"条数据");
            users.add(user);
        }
        userRepo.saveBatch(users);
        return userRepo.list(new Query());
    }


    @RequestMapping(value = "/11")
    public Object test11(){
         String url= "initdata/direction.xml";
         return XmlReader.getElementList(url);
    }





    @RequestMapping(value = "/20")
    public Object test20(){
        jdbcTemplate.update("delete from t_sys_dictionary where id in (22,23,24,25)");
        return null;
    }



    @RequestMapping(value = "/22")
    public Object test22(){
        return null;
    }



    @Autowired
    private TBizAttachmentService attachmentService;


    @Autowired
    private ComFiles comFiles;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepo userRepo;


    @Autowired
    private RedisUtils redisUtils;



    @Autowired
    private CoreUserService coreUserService;

}