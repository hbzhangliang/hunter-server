package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TBizShareDoc;
import cn.com.cubic.platform.hunter.mysql.entity.TSysConfig;
import cn.com.cubic.platform.hunter.mysql.services.TBizShareDocService;
import cn.com.cubic.platform.hunter.mysql.services.TSysConfigService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
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
 * Created by Liang.Zhang on 2018/9/13.
 **/
@Validated
@Controller
@RequestMapping(value = "/sysconfig",produces = "application/json; charset=utf-8")
@ResponseBody
public class TSysConfigController {

    private final static Logger log = LoggerFactory.getLogger(ShareDocController.class);


    @Autowired
    private TSysConfigService sysConfigService;

    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<TSysConfig> pageParams){
        return sysConfigService.list(pageParams);
    }


    @RequestMapping(value = "/list-all")
    public Object listAll(){
        return sysConfigService.listAll();
    }

    @RequestMapping(value = "/save")
    public Object save(@RequestBody TSysConfig bean){
        return sysConfigService.saveOrUpdate(bean);
    }

    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return sysConfigService.findById(id);
    }

    @RequestMapping(value = "/get-bycode")
    public Object getByCode(@RequestBody Map<String,String> map){
        String code=map.get("code");
        return sysConfigService.findByCode(code);
    }

    @RequestMapping(value = "/del")
    public Object del(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return sysConfigService.del(delIds);
    }


}
