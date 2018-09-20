package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TBizTalentDoc;
import cn.com.cubic.platform.hunter.mysql.entity.TSysConfig;
import cn.com.cubic.platform.hunter.mysql.services.TBizTalentDocService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
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
 * Created by Liang.Zhang on 2018/9/20.
 **/
@Validated
@Controller
@RequestMapping(value = "/talent-doc",produces = "application/json; charset=utf-8")
@ResponseBody
public class TalentDocController {

    @Autowired
    private TBizTalentDocService talentDocService;

    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<TBizTalentDoc> pageParams){
        return talentDocService.list(pageParams);
    }


    @RequestMapping(value = "/list-all")
    public Object listAll(){
        return talentDocService.listAll();
    }

    @RequestMapping(value = "/save")
    public Object save(@RequestBody TBizTalentDoc bean){
        return talentDocService.saveOrUpdate(bean);
    }

    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return talentDocService.findById(id);
    }


    @RequestMapping(value = "/del")
    public Object del(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return talentDocService.del(delIds);
    }


    @RequestMapping(value = "/add-share")
    public Object addShare(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("docIds");
        List<Long> docIds=new ArrayList<>(10);
        for(Object p:ids){
            docIds.add(Long.valueOf(p.toString()));
        }
        Long talentid=Long.valueOf(map.get("talentId").toString());
        talentDocService.saveOrUpdate(talentid,docIds);
        return null;
    }


    @RequestMapping(value = "/docs-bytalent")
    public Object getDocsByTalentId(@RequestBody Map<String,Long> map){
        Long talentId=map.get("id");
        return talentDocService.getDocIdsByTalentId(talentId);
    }


}
