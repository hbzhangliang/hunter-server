package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TBizTag;
import cn.com.cubic.platform.hunter.mysql.entity.TSysCorp;
import cn.com.cubic.platform.hunter.mysql.services.SysCorpService;
import cn.com.cubic.platform.hunter.mysql.services.TBizTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/7/10.
 **/
@Validated
@Controller
@RequestMapping(value = "/corp",produces = "application/json; charset=utf-8")
@ResponseBody
public class CorpController {

    private final static Logger log = LoggerFactory.getLogger(CorpController.class);

    @Autowired
    private SysCorpService corpService;

    @RequestMapping(value = "/get")
    public Object get(){
        return corpService.getOne();
    }

    @RequestMapping(value = "/save")
    public Object save(@RequestBody TSysCorp bean){
        return corpService.saveOrUpdate(bean);
    }

}
