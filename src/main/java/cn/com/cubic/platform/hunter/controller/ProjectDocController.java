package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TBizProjectDoc;
import cn.com.cubic.platform.hunter.mysql.services.TBizProjectDocService;
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
 * Created by Liang.Zhang on 2018/9/29.
 **/
@Validated
@Controller
@RequestMapping(value = "/project-doc",produces = "application/json; charset=utf-8")
@ResponseBody
public class ProjectDocController {

    @Autowired
    private TBizProjectDocService projectDocService;

    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<TBizProjectDoc> pageParams){
        return projectDocService.list(pageParams);
    }


    @RequestMapping(value = "/list-all")
    public Object listAll(){
        return projectDocService.listAll();
    }

    @RequestMapping(value = "/save")
    public Object save(@RequestBody TBizProjectDoc bean){
        return projectDocService.saveOrUpdate(bean);
    }

    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return projectDocService.findById(id);
    }


    @RequestMapping(value = "/del")
    public Object del(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return projectDocService.del(delIds);
    }


    @RequestMapping(value = "/add-share")
    public Object addShare(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("docIds");
        List<Long> docIds=new ArrayList<>(10);
        for(Object p:ids){
            docIds.add(Long.valueOf(p.toString()));
        }
        Long projectId=Long.valueOf(map.get("projectId").toString());
        projectDocService.saveOrUpdate(projectId,docIds);
        return null;
    }


    @RequestMapping(value = "/docs-byproject")
    public Object getDocsByTalentId(@RequestBody Map<String,Long> map){
        Long projectId=map.get("id");
        return projectDocService.getDocIdsByProjectId(projectId);
    }

}
