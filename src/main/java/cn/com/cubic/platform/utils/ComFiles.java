package cn.com.cubic.platform.utils;

import cn.com.cubic.platform.hunter.controller.TestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Liang.Zhang on 2018/9/3.
 **/
@Component
public class ComFiles {

    private static final Logger log = LoggerFactory.getLogger(ComFiles.class);

    @Value("${sso.loginurl}")
    public String loginUrl;




    /**
     * 将文件写入到硬盘上，并返回url可供访问
     * @param file
     * @return
     */
    public String fileUpload(MultipartFile file){
        Date date=new Date();

        return null;

    }

    public void makeFile(){
        Calendar calendar=Calendar.getInstance();
        String path=this.loginUrl+"/"+calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH)+'/'+calendar.get(Calendar.DATE);

        log.info("ttttt");
        log.info(path);

//        File file=new File(path);
//        if(!file.exists()){
//            file.mkdirs();
//        }


    }




}
