package cn.com.cubic.platform.hunter.controller;

import cn.com.cubic.platform.hunter.mysql.entity.TBizAttachment;
import cn.com.cubic.platform.hunter.mysql.services.TBizAttachmentService;
import cn.com.cubic.platform.utils.ComFiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/8/31.
 **/
@Validated
@Controller
@RequestMapping(value = "/attachment")
@ResponseBody
public class AttachmentController {
    private final static Logger log = LoggerFactory.getLogger(AttachmentController.class);


    @Autowired
    private TBizAttachmentService attachmentService;


    @Autowired
    private ComFiles comFiles;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object fileUpload(@RequestBody MultipartFile file, HttpServletRequest request) throws Exception{
        TBizAttachment attachment=new TBizAttachment();
        attachment.setName("111");
        attachment.setType("ddd");
        attachment.setUrl(comFiles.fileUpload(file));
        return attachmentService.saveOrUpdate(attachment);
    }

    @RequestMapping(value = "/list-all")
    public Object listAll(@RequestBody Map<String,Long> map){
        Long pId=map.get("pId");
        return attachmentService.listAll(pId);
    }

    @RequestMapping(value = "/save")
    public Object save(@RequestBody TBizAttachment bean){
        return attachmentService.saveOrUpdate(bean);
    }

    @RequestMapping(value = "/get")
    public Object get(@RequestBody Map<String,Long> map){
        Long id=map.get("id");
        return attachmentService.findById(id);
    }

    @RequestMapping(value = "/del")
    public Object del(@RequestBody Map<String,Object> map){
        List<Object> ids=(List) map.get("ids");
        List<Long> delIds=new ArrayList<>(10);
        for(Object p:ids){
            delIds.add(Long.valueOf(p.toString()));
        }
        return attachmentService.del(delIds);
    }


}
