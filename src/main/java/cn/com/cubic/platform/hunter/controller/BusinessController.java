package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TBizBusiness;
import cn.com.cubic.platform.hunter.mysql.entity.TBizCity;
import cn.com.cubic.platform.hunter.mysql.services.TBizBusinessService;
import cn.com.cubic.platform.hunter.mysql.services.TBizCityService;
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
 * Created by Liang.Zhang on 2018/8/13.
 **/
@Validated
@Controller
@RequestMapping(value = "/business",produces = "application/json; charset=utf-8")
@ResponseBody
public class BusinessController {

    private final static Logger log = LoggerFactory.getLogger(BusinessController.class);

    @Autowired
    private TBizBusinessService businessService;

    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<TBizBusiness> cityPageParams){
        return businessService.list(cityPageParams);
    }


    @RequestMapping(value = "/list-all")
    public Object listAll(){
        return businessService.listAll();
    }

    @RequestMapping(value = "/save")
    public Object save(@RequestBody TBizBusiness bean){
        return businessService.saveOrUpdate(bean);
    }

    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return businessService.findById(id);
    }

    @RequestMapping(value = "/del")
    public Object del(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return businessService.del(delIds);
    }

    /**
     * 存在缓存
     * @return
     */
    @RequestMapping(value = "/tree")
    public Object tree(){
        return businessService.tree();
    }




    @RequestMapping(value = "/import")
    public Object importCity(){
        businessService.importBusiness();
        return null;
    }

}
