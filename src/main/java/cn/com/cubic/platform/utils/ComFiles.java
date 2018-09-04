package cn.com.cubic.platform.utils;

import cn.com.cubic.platform.hunter.controller.TestController;
import cn.com.cubic.platform.utils.Exception.HunterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Liang.Zhang on 2018/9/3.
 **/
@Component
public class ComFiles {
    private static final Logger log = LoggerFactory.getLogger(ComFiles.class);

    @Value("${fileTmp}")
    public String fileTmp;


    /**
     * 将文件写入到硬盘上，并返回url可供访问
     * @param file
     * @return
     */
    public String fileUpload(MultipartFile file) throws IOException{
        String fileName=file.getOriginalFilename();
        String type=fileName.substring(fileName.indexOf(".")+1);
        return this.makeFile(file.getBytes(),type);
    }


    //写入数据   type ='Doc,txt '等
    public String makeFile(byte[] bytes,String type){
        FileOutputStream fop = null;
        File file=new File(this.createFile(type));
        try{
            fop = new FileOutputStream(file);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            // get the content in bytes
            byte[] contentInBytes =bytes;

            fop.write(contentInBytes);
            fop.flush();
            fop.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file.getPath();
    }


    /**
     * 路径创建，文件 文件夹创建
     * @return
     */
    public String createFile(String type){
        Calendar calendar=Calendar.getInstance();
        String docPath=this.fileTmp+"/"+calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+'/'+calendar.get(Calendar.DATE);
        String uuId= UUID.randomUUID().toString().replace("-","");
        String filePath=docPath+"/"+uuId+"."+type.toLowerCase();
        File docFile=new File(docPath);
        File file=new File(filePath);
        if(!docFile.exists()){
            docFile.mkdirs();
        }
        if(!file.exists()){
            try {
                file.createNewFile();
            }
            catch (Exception e){
                e.printStackTrace();
                throw new HunterException("errer");
            }
        }
        return filePath;
    }



}
