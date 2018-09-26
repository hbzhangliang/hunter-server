package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TBizCompany;
import cn.com.cubic.platform.hunter.mysql.entity.TBizTalent;
import cn.com.cubic.platform.hunter.mysql.entity.VShareCompany;
import cn.com.cubic.platform.hunter.mysql.entity.VShareTalent;
import cn.com.cubic.platform.hunter.mysql.services.TBizCompanyService;
import cn.com.cubic.platform.hunter.mysql.services.TBizTalentService;
import cn.com.cubic.platform.hunter.mysql.services.VShareCompanyService;
import cn.com.cubic.platform.hunter.mysql.services.VShareTalentService;
import cn.com.cubic.platform.hunter.mysql.vo.CompanyVo;
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
 * Created by Liang.Zhang on 2018/9/25.
 **/
@Validated
@Controller
@RequestMapping(value = "/company",produces = "application/json; charset=utf-8")
@ResponseBody
public class CompanyController {

    private final static Logger log = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private TBizCompanyService companyService;

    @Autowired
    private VShareCompanyService shareCompanyService;
    /**
     * 只显示自己的
     * @param pageParams
     * @return
     */
    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<TBizCompany> pageParams){
        return companyService.list(pageParams,true);
    }

    /**
     * 显示所有的
     * @param pageParams
     * @return
     */
    @RequestMapping(value = "/list-all-page")
    public Object listAllPage(@RequestBody PageParams<TBizCompany> pageParams){
        return companyService.list(pageParams,false);
    }


    @RequestMapping(value = "/list-all")
    public Object listAll(){
        return companyService.listAll();
    }

    @RequestMapping(value = "/save")
    public Object save(@RequestBody CompanyVo bean){
        return companyService.saveOrUpdate(bean);
    }

    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return companyService.findById(id);
    }

    @RequestMapping(value = "/get-vo")
    public Object getVo(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return companyService.findVoById(id);
    }


    @RequestMapping(value = "/del")
    public Object del(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return companyService.fakeDel(delIds);
    }


    @RequestMapping(value = "/admin-del")
    public Object delph(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return companyService.adminDel(delIds);
    }
    @RequestMapping(value = "/list-share")
    public Object listShare(@RequestBody PageParams<VShareCompany> pageParams){
        return shareCompanyService.list(pageParams);
    }
}
