package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.services.TBizBusinessService;
import cn.com.cubic.platform.hunter.mysql.services.TBizCareerService;
import cn.com.cubic.platform.hunter.mysql.services.TBizCityService;
import cn.com.cubic.platform.hunter.mysql.services.TBizDocService;
import cn.com.cubic.platform.utils.ComEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/9/10.
 **/
@Validated
@Controller
@RequestMapping(value = "/util",produces = "application/json; charset=utf-8")
@ResponseBody
public class UtilController {

    private final static Logger log = LoggerFactory.getLogger(UtilController.class);

    @Autowired
    private TBizBusinessService businessService;

    @Autowired
    private TBizCareerService careerService;

    @Autowired
    private TBizCityService cityService;

    @Autowired
    private TBizDocService docService;

    /**
     * 综合树的获取
     * BusinessTree
     * CareerTree
     * CityTree
     * ShareTree
     *
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/tree")
    public Object listAll(@RequestBody Map<String,Object> map){
        List<String> tree=(List<String>) map.get("codes");
        Map<String,Object> result=new HashMap<>(10);
        for(String p:tree){
            switch (ComEnum.TreeType.getTreeType(p)){
                case BusinessTree:result.put(ComEnum.TreeType.BusinessTree.toString(),businessService.tree()); break;
                case CareerTree:result.put(ComEnum.TreeType.CareerTree.toString(),careerService.tree());break;
                case CityTree:result.put(ComEnum.TreeType.CityTree.toString(),cityService.tree());break;
                case ShareTree:result.put(ComEnum.TreeType.ShareTree.toString(),docService.allShareTree());break;
                default:break;
            }
        }
        return result;
    }


}
