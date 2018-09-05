package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TBizTag;
import cn.com.cubic.platform.hunter.mysql.entity.TSysDictionary;
import cn.com.cubic.platform.hunter.mysql.services.TSysDictionaryService;
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
 * Created by Liang.Zhang on 2018/8/16.
 **/
@Validated
@Controller
@RequestMapping(value = "/dict",produces = "application/json; charset=utf-8")
@ResponseBody
public class DictionaryController {

    private final static Logger log = LoggerFactory.getLogger(DictionaryController.class);

    @Autowired
    private TSysDictionaryService dictionaryService;

    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<TSysDictionary> pageParams){
        return dictionaryService.list(pageParams);
    }


    @RequestMapping(value = "/list-children")
    public Object listChildren(@RequestBody Map<String,String> map) {
        Long pId=Long.valueOf(map.get("pId"));
        return dictionaryService.list(pId);
    }


    @RequestMapping(value = "/list-children-bycode")
    public Object listChildrenByCode(@RequestBody Map<String,String> map) {
        String code=map.get("code");
        return dictionaryService.list(code);
    }



    @RequestMapping(value = "/list-all")
    public Object listAll(){
        return dictionaryService.listAll();
    }

    @RequestMapping(value = "/save")
    public Object save(@RequestBody TSysDictionary bean){
        return dictionaryService.saveOrUpdate(bean);
    }

    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return dictionaryService.findById(id);
    }

    @RequestMapping(value = "/del")
    public Object del(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return dictionaryService.del(delIds);
    }

}
