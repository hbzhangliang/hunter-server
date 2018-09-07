package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TBizRecordWork;
import cn.com.cubic.platform.hunter.mysql.services.TBizRecordWorkService;
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
@RequestMapping(value = "/record-work",produces = "application/json; charset=utf-8")
@ResponseBody
public class RecordWorkController {

    private final static Logger log = LoggerFactory.getLogger(RecordWorkController.class);

    @Autowired
    private TBizRecordWorkService recordWorkService;

    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<TBizRecordWork> pageParams){
        return recordWorkService.list(pageParams);
    }


    @RequestMapping(value = "/list-all")
    public Object listAll(){
        return recordWorkService.listAll();
    }

    @RequestMapping(value = "/save")
    public Object save(@RequestBody TBizRecordWork bean){
        return recordWorkService.saveOrUpdate(bean);
    }

    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return recordWorkService.findById(id);
    }

    @RequestMapping(value = "/del")
    public Object del(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return recordWorkService.del(delIds);
    }

}
