package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TBizTalent;
import cn.com.cubic.platform.hunter.mysql.services.TBizTalentService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.hunter.mysql.vo.TalentVo;
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
 * Created by Liang.Zhang on 2018/9/5.
 **/
@Validated
@Controller
@RequestMapping(value = "/talent",produces = "application/json; charset=utf-8")
@ResponseBody
public class TalentController {


    private final static Logger log = LoggerFactory.getLogger(TalentController.class);

    @Autowired
    private TBizTalentService talentService;

    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<TBizTalent> pageParams){
        return talentService.list(pageParams);
    }


    @RequestMapping(value = "/list-all")
    public Object listAll(){
        return talentService.listAll();
    }

    @RequestMapping(value = "/save")
    public Object save(@RequestBody TalentVo bean){
        return talentService.saveOrUpdate(bean);
    }

    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return talentService.findById(id);
    }

    @RequestMapping(value = "/get-vo")
    public Object getVo(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return talentService.findVoById(id);
    }


    @RequestMapping(value = "/del")
    public Object del(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return talentService.del(delIds);
    }


    @RequestMapping(value = "/delph")
    public Object delph(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return talentService.delPhysics(delIds);
    }



}
