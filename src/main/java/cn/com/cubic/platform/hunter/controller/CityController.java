package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TBizCity;
import cn.com.cubic.platform.hunter.mysql.services.TBizCityService;
import cn.com.cubic.platform.hunter.mysql.services.impl.TBizCityServiceImpl;
import cn.com.cubic.platform.hunter.mysql.vo.CityTreeVo;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Iterator;
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

    private final static Logger log = LoggerFactory.getLogger(CityController.class);

    @Autowired
    private TBizCityService cityService;

    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<TBizCity> cityPageParams){
        return cityService.list(cityPageParams);
    }


    @RequestMapping(value = "/list-all")
    public Object listAll(){
        return cityService.listAll();
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
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return cityService.del(delIds);
    }

    /**
     * 存在缓存
     * @return
     */
    @RequestMapping(value = "/tree")
    public Object tree(){
        return cityService.tree();
    }



}
