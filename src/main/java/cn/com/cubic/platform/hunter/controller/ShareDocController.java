package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TBizShareDoc;
import cn.com.cubic.platform.hunter.mysql.services.TBizShareDocService;
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
 * Created by Liang.Zhang on 2018/8/31.
 **/
@Validated
@Controller
@RequestMapping(value = "/doc-share",produces = "application/json; charset=utf-8")
@ResponseBody
public class ShareDocController {
    private final static Logger log = LoggerFactory.getLogger(ShareDocController.class);


    @Autowired
    private TBizShareDocService shareDocService;

    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<TBizShareDoc> pageParams){
        return shareDocService.list(pageParams);
    }


    @RequestMapping(value = "/list-all")
    public Object listAll(){
        return shareDocService.listAll();
    }

    @RequestMapping(value = "/save")
    public Object save(@RequestBody TBizShareDoc bean){
        return shareDocService.saveOrUpdate(bean);
    }

    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return shareDocService.findById(id);
    }

    @RequestMapping(value = "/del")
    public Object del(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return shareDocService.del(delIds);
    }

    /**
     * docId必须穿   type可以不穿
     * @param map
     * @return
     */
    @RequestMapping(value = "/list-by-doc")
    public Object listByDoc(@RequestBody Map<String,Object> map){
        Long docId=Long.valueOf(map.get("docId").toString());
        Object shareType=map.get("shareType");
        String type=shareType==null?null:shareType.toString();
        return shareDocService.listByDoc(docId,type);
    }



}
