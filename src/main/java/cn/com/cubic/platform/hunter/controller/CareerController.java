package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TBizCareer;
import cn.com.cubic.platform.hunter.mysql.services.TBizCareerService;
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
@RequestMapping(value = "/career",produces = "application/json; charset=utf-8")
@ResponseBody
public class CareerController {
    private final static Logger log = LoggerFactory.getLogger(CareerController.class);

    @Autowired
    private TBizCareerService careerService;

    @RequestMapping(value = "/list")
    public Object list(@RequestBody PageParams<TBizCareer> pageParams){
        return careerService.list(pageParams);
    }


    @RequestMapping(value = "/list-all")
    public Object listAll(){
        return careerService.listAll();
    }

    @RequestMapping(value = "/save")
    public Object save(@RequestBody TBizCareer bean){
        return careerService.saveOrUpdate(bean);
    }

    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return careerService.findById(id);
    }

    @RequestMapping(value = "/del")
    public Object del(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return careerService.del(delIds);
    }

    /**
     * 存在缓存
     * @return
     */
    @RequestMapping(value = "/tree")
    public Object tree(){
        return careerService.tree();
    }




    @RequestMapping(value = "/import")
    public Object importCity(){
        careerService.importCareers();
        return null;
    }
}
