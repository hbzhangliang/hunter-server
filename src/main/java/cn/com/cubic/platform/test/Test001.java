package cn.com.cubic.platform.test;

import cn.com.cubic.platform.utils.HtmlUtilsZj;
import cn.com.cubic.platform.utils.JSONHelper;
import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

public class Test001 {


    /**
     * 小说  章节列表
     * @param args
     */
    public static void main(String[] args){
        try {
//            String strUrl = "http://www.kanunu8.com/book/4573";

            String  strUrl="http://www.kanunu8.com/book3/7182/";

            String content = JSONHelper.loadJson(strUrl);

            System.out.println(content);


            String tableContent= HtmlUtilsZj.getTableContent(content);

            System.out.println(JSON.toJSONString(tableContent));


            List<String> tableTds= HtmlUtilsZj.getTableTdList(tableContent);

            System.out.println(JSON.toJSONString(tableTds));

            List<Map<String,String>> mapList= HtmlUtilsZj.getTdinfoaList(tableTds);

            System.out.println(JSON.toJSONString(mapList));


        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

}
