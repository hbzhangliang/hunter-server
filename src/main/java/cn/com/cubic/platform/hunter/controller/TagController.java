package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TBizCity;
import cn.com.cubic.platform.hunter.mysql.entity.TBizTag;
import cn.com.cubic.platform.hunter.mysql.services.TBizTagService;
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
 * Created by Liang.Zhang on 2018/8/15.
 **/
@Validated
@Controller
@RequestMapping(value = "/tag",produces = "application/json; charset=utf-8")
@ResponseBody
public class TagController {

    private final static Logger log = LoggerFactory.getLogger(TagController.class);


    @Autowired
    private TBizTagService tagService;

    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<TBizTag> pageParams){
        return tagService.list(pageParams);
    }


    @RequestMapping(value = "/list-all")
    public Object listAll(@RequestBody Map<String,String> map){
        Long groupId=Long.valueOf(map.get("groupId"));
        return tagService.listAll(groupId);
    }

    @RequestMapping(value = "/save")
    public Object save(@RequestBody TBizTag bean){
        return tagService.saveOrUpdate(bean);
    }

    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return tagService.findById(id);
    }

    @RequestMapping(value = "/del")
    public Object del(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return tagService.del(delIds);
    }

    /**
     * 存在缓存
     * @return
     */
    @RequestMapping(value = "/tree")
    public Object tree(@RequestBody Map<String,String> map){
        Long groupId=Long.valueOf(map.get("groupId"));
        return tagService.tree(groupId);
    }






}
