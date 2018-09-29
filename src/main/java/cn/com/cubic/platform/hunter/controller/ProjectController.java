package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TBizProject;
import cn.com.cubic.platform.hunter.mysql.entity.TBizTalent;
import cn.com.cubic.platform.hunter.mysql.entity.VShareProject;
import cn.com.cubic.platform.hunter.mysql.entity.VShareTalent;
import cn.com.cubic.platform.hunter.mysql.services.TBizProjectService;
import cn.com.cubic.platform.hunter.mysql.services.TBizShareProjectService;
import cn.com.cubic.platform.hunter.mysql.services.VShareProjectService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.hunter.mysql.vo.ProjectVo;
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
 * Created by Liang.Zhang on 2018/9/29.
 **/
@Validated
@Controller
@RequestMapping(value = "/project",produces = "application/json; charset=utf-8")
@ResponseBody
public class ProjectController {

    private final static Logger log = LoggerFactory.getLogger(ProjectController.class);


    @Autowired
    private TBizProjectService projectService;

    @Autowired
    private VShareProjectService shareProjectService;

    /**
     * 只显示自己的
     * @param pageParams
     * @return
     */
    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<TBizProject> pageParams){
        return projectService.list(pageParams,true);
    }

    /**
     * 显示所有的
     * @param pageParams
     * @return
     */
    @RequestMapping(value = "/list-all-page")
    public Object listAllPage(@RequestBody PageParams<TBizProject> pageParams){
        return projectService.list(pageParams,false);
    }


    @RequestMapping(value = "/list-all")
    public Object listAll(){
        return projectService.listAll();
    }

    @RequestMapping(value = "/save")
    public Object save(@RequestBody ProjectVo bean){
        return projectService.saveOrUpdate(bean);
    }

    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return projectService.findById(id);
    }

    @RequestMapping(value = "/get-vo")
    public Object getVo(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return projectService.findVoById(id);
    }


    @RequestMapping(value = "/del")
    public Object del(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return projectService.fakeDel(delIds);
    }


    @RequestMapping(value = "/admin-del")
    public Object delph(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return projectService.adminDel(delIds);
    }


    @RequestMapping(value = "/list-share")
    public Object listShare(@RequestBody PageParams<VShareProject> pageParams){
        return shareProjectService.list(pageParams);
    }


}
