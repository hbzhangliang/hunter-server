package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.VCompanyDoc;
import cn.com.cubic.platform.hunter.mysql.entity.VTalentDoc;
import cn.com.cubic.platform.hunter.mysql.services.VCompanyDocService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/9/28.
 **/
@Validated
@Controller
@RequestMapping(value = "/vcompanydoc",produces = "application/json; charset=utf-8")
@ResponseBody
public class VCompanyDocController {

    @Autowired
    private VCompanyDocService vCompanyDocService;

    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<VCompanyDoc> pageParams){
        return vCompanyDocService.list(pageParams);
    }


    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return vCompanyDocService.findById(id);
    }


}
