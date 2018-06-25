package cn.com.cubic.platform.test;

import cn.com.cubic.platform.utils.HtmlUtilsZj;
import cn.com.cubic.platform.utils.JSONHelper;
import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

public class Test003 {


    public static void main(String[] args){
        try {
            ////1-----9
            ////
            String strUrl = "http://www.kanunu8.com/author1.html";
            String content = JSONHelper.loadJson(strUrl);

            System.out.println(content);
            List<Map<String,String>> list= HtmlUtilsZj.getAuthList(content);

            System.out.println(JSON.toJSONString(list));
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

}
