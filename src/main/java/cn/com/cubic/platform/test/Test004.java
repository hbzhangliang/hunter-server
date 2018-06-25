package cn.com.cubic.platform.test;

import cn.com.cubic.platform.utils.HtmlUtilsFiles;
import cn.com.cubic.platform.utils.JSONHelper;
import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

public class Test004 {

    public static void main(String[] args){
        try {
            String strUrl="http://www.kanunu8.com//files/writer/199.html";
            String content = JSONHelper.loadJson(strUrl);
            content = HtmlUtilsFiles.delBlank(content);
            System.out.println(content);
            content=HtmlUtilsFiles.getTableContent(content);

            List<String> tbList = HtmlUtilsFiles.tagAcontent(content);
            System.out.println(JSON.toJSONString(tbList));
            List<Map<String, String>> mapList = HtmlUtilsFiles.tagADetail(tbList);
            System.out.println(JSON.toJSONString(mapList));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
