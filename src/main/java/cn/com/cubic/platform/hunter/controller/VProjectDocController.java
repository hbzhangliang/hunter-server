package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.VProjectDoc;
import cn.com.cubic.platform.hunter.mysql.services.VProjectDocService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/9/29.
 **/
@Validated
@Controller
@RequestMapping(value = "/vprojectdoc",produces = "application/json; charset=utf-8")
@ResponseBody
public class VProjectDocController {


    @Autowired
    private VProjectDocService vProjectDocService;

    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<VProjectDoc> pageParams){
        return vProjectDocService.list(pageParams);
    }


    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return vProjectDocService.findById(id);
    }

}



