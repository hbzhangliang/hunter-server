package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TBizCompanyDoc;
import cn.com.cubic.platform.hunter.mysql.services.TBizCompanyDocService;
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
 * Created by Liang.Zhang on 2018/9/28.
 **/
@Validated
@Controller
@RequestMapping(value = "/company-doc",produces = "application/json; charset=utf-8")
@ResponseBody
public class CompanyDocController {

    @Autowired
    private TBizCompanyDocService companyDocService;

    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<TBizCompanyDoc> pageParams){
        return companyDocService.list(pageParams);
    }


    @RequestMapping(value = "/list-all")
    public Object listAll(){
        return companyDocService.listAll();
    }

    @RequestMapping(value = "/save")
    public Object save(@RequestBody TBizCompanyDoc bean){
        return companyDocService.saveOrUpdate(bean);
    }

    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return companyDocService.findById(id);
    }


    @RequestMapping(value = "/del")
    public Object del(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return companyDocService.del(delIds);
    }


    @RequestMapping(value = "/add-share")
    public Object addShare(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("docIds");
        List<Long> docIds=new ArrayList<>(10);
        for(Object p:ids){
            docIds.add(Long.valueOf(p.toString()));
        }
        Long talentid=Long.valueOf(map.get("companyId").toString());
        companyDocService.saveOrUpdate(talentid,docIds);
        return null;
    }


    @RequestMapping(value = "/docs-bycompany")
    public Object getDocsByTalentId(@RequestBody Map<String,Long> map){
        Long talentId=map.get("id");
        return companyDocService.getDocIdsByCompanyId(talentId);
    }

}
