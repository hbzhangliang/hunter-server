package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TBizCity;
import cn.com.cubic.platform.hunter.mysql.services.TBizCityService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/8/8.
 **/
@Validated
@Controller
@RequestMapping(value = "/city",produces = "application/json; charset=utf-8")
@ResponseBody
public class CityController {

    @Autowired
    private TBizCityService cityService;

    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<TBizCity> cityPageParams){
        return cityService.list(cityPageParams);
    }

    @RequestMapping(value = "/save")
    public Object save(@RequestBody TBizCity bean){
        return cityService.saveOrUpdate(bean);
    }

    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return cityService.findById(id);
    }

    @RequestMapping(value = "/del")
    public Object del(@RequestBody Map<String,Object> map){
        List<Long> ids=(List) map.get("ids");
        return cityService.del(ids);
    }

    @RequestMapping(value = "/tree")
    public Object tree(){
        return cityService.tree();
    }



}
