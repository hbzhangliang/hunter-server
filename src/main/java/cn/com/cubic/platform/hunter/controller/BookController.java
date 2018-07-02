package cn.com.cubic.platform.hunter.controller;


import cn.com.cubic.platform.hunter.services.BookApi;
import cn.com.cubic.platform.hunter.mysql.vo.PageForm;
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
@RequestMapping(value = "/book",produces = "application/json; charset=utf-8")
public class BookController {

    @RequestMapping(value = "/list",method = RequestMethod.POST, consumes = {
            "text/plain", "application/json", "text/json", "text/*",
            "application/*" })
    @ResponseBody
    public Object list(@RequestBody PageForm pageForm){
        return bookApi.bookList(pageForm);
    }



    @RequestMapping(value = "/get",method = RequestMethod.POST, consumes = {
            "text/plain", "application/json", "text/json", "text/*",
            "application/*" })
    @ResponseBody
    public Object get(@RequestBody Map<String ,String> map){
        String id=map.get("id");
        return bookApi.bookGet(id);
    }



    @Autowired
    private BookApi bookApi;

}
