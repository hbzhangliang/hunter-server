package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TBizShareDoc;
import cn.com.cubic.platform.hunter.mysql.entity.TBizShareTalent;
import cn.com.cubic.platform.hunter.mysql.entity.TBizTalent;
import cn.com.cubic.platform.hunter.mysql.services.TBizShareTalentService;
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
 * Created by Liang.Zhang on 2018/9/6.
 **/
@Validated
@Controller
@RequestMapping(value = "/talent-share",produces = "application/json; charset=utf-8")
@ResponseBody
public class ShareTalentController {

    private final static Logger log = LoggerFactory.getLogger(ShareTalentController.class);


    @Autowired
    private TBizShareTalentService shareTalentService;

    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<TBizShareTalent> pageParams){
        return shareTalentService.list(pageParams);
    }


    @RequestMapping(value = "/list-all")
    public Object listAll(){
        return shareTalentService.listAll();
    }

    @RequestMapping(value = "/save")
    public Object save(@RequestBody TBizShareTalent bean){
        return shareTalentService.saveOrUpdate(bean);
    }

    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return shareTalentService.findById(id);
    }

    @RequestMapping(value = "/del")
    public Object del(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return shareTalentService.del(delIds);
    }



}
