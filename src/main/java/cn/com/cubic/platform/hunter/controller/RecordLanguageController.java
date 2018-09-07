package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TBizRecordLanguage;
import cn.com.cubic.platform.hunter.mysql.services.TBizRecordLanguageService;
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
 * Created by Liang.Zhang on 2018/9/7.
 **/
@Validated
@Controller
@RequestMapping(value = "/record-language",produces = "application/json; charset=utf-8")
@ResponseBody
public class RecordLanguageController {

    private final static Logger log = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private TBizRecordLanguageService recordLanguageService;

    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<TBizRecordLanguage> pageParams){
        return recordLanguageService.list(pageParams);
    }


    @RequestMapping(value = "/list-all")
    public Object listAll(){
        return recordLanguageService.listAll();
    }

    @RequestMapping(value = "/save")
    public Object save(@RequestBody TBizRecordLanguage bean){
        return recordLanguageService.saveOrUpdate(bean);
    }

    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return recordLanguageService.findById(id);
    }

    @RequestMapping(value = "/del")
    public Object del(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return recordLanguageService.del(delIds);
    }

}
