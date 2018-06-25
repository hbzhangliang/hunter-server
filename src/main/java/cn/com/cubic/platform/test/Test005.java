package cn.com.cubic.platform.test;

import cn.com.cubic.platform.utils.HtmlUtilsAuthor;
import cn.com.cubic.platform.utils.JSONHelper;
import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

public class Test005 {

    public static void main(String[] args){
        try {
            String strUrl="http://love.kanunu8.com/author/yishu.html";
//            需要解析
//            String strUrl="http://www.kanunu8.com/files/writer/4461.html";
//            需要解析
//、
            String content = JSONHelper.loadJsonUTF(strUrl);
            content = HtmlUtilsAuthor.delBlank(content);
            content=HtmlUtilsAuthor.getTableContent(content);
            System.out.println(content);
            List<String> tbList = HtmlUtilsAuthor.tagAcontent(content);
            System.out.println(JSON.toJSONString(tbList));
            List<Map<String, String>> mapList = HtmlUtilsAuthor.tagADetail(tbList);
            System.out.println(JSON.toJSONString(mapList));

        }
        catch ( Exception e){
            e.printStackTrace();

        }
    }

}
