package cn.com.cubic.platform.test;

import cn.com.cubic.platform.utils.HtmlAuthor;
import cn.com.cubic.platform.utils.JSONHelper;
import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

public class TestAuthors {

    public static void main(String[] args){

        try {
            String strUrl = "http://www.kanunu8.com/files/writer/18-1.html";
            String content = JSONHelper.loadJson(strUrl);
            content = HtmlAuthor.delBlank(content);
            System.out.println(content);
            content=HtmlAuthor.getTableContent(content);
            System.out.println(content);
            List<String> tbList = HtmlAuthor.tagInfo(content);
            System.out.println(JSON.toJSONString(tbList));
            List<Map<String, String>> mapList = HtmlAuthor.tagADetail(tbList);
            System.out.println(JSON.toJSONString(mapList));
            System.out.println(JSON.toJSONString(mapList.size()));
        }
        catch (Exception e){
            e.printStackTrace();
        }



    }

}
