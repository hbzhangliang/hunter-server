package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.vo.PageForm;
import cn.com.cubic.platform.hunter.services.AuthorApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Validated
@Controller
@RequestMapping(value = "/author",produces = "application/json; charset=utf-8")
public class AuthorController {

    @RequestMapping(value = "/list",method = RequestMethod.POST, consumes = {
            "text/plain", "application/json", "text/json", "text/*",
            "application/*" })
    @ResponseBody
    public Object list(@RequestBody PageForm pageForm){
        return authorApi.authorList(pageForm);
    }



    @RequestMapping(value = "/get",method = RequestMethod.POST, consumes = {
            "text/plain", "application/json", "text/json", "text/*",
            "application/*" })
    @ResponseBody
    public Object get(@RequestBody Map<String ,String> map){
        String id=map.get("id");
        return authorApi.authorGet(id);
    }



    @Autowired
    private AuthorApi authorApi;
}
