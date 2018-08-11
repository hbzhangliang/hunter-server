package cn.com.cubic.platform.utils;

import cn.com.cubic.platform.hunter.controller.TestController;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Liang.Zhang on 2018/8/11.
 **/

public class JsonReader {

    private static final Logger log = LoggerFactory.getLogger(JsonReader.class);

    public static JSONObject readJson(String path){
        try {
            path= ResourceUtils.getURL("classpath:").getPath()+path;
            BufferedReader br = new BufferedReader(new FileReader(path));// 读取原始json文件
            StringBuilder sb=new StringBuilder();
            String lineTxt = br.readLine();
            while (lineTxt != null) {
                sb.append(lineTxt);
                lineTxt=br.readLine();
            }
            return JSONObject.parseObject(sb.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
