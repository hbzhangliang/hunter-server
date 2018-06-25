package cn.com.cubic.platform.test;

import cn.com.cubic.platform.utils.HtmlUtilsZj;
import cn.com.cubic.platform.utils.JSONHelper;
import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

/**
 * Created by liang_zhang on 2017/10/14.
 */
public class Test {


    /**
     * 作家 文章 列表
     * @param args
     */
    public static void main(String[] args){

        try {
//            String strUrl="http://love.kanunu8.com/author/yishu.html";
//            需要解析
//            String strUrl="http://www.kanunu8.com/files/writer/4461.html";
//            需要解析
//、
//            String strUrl = "http://www.kanunu8.com/zj/10867.html";
            String strUrl = "http://www.kanunu8.com/zj/10740.html";
            String content = JSONHelper.loadJson(strUrl);

            content= HtmlUtilsZj.delBlank(content);

            System.out.println(content);

            String tableContent= HtmlUtilsZj.getTableContent(content);

            System.out.println(tableContent);

            List<String> tbList= HtmlUtilsZj.getTableTdList(tableContent);

            System.out.println(JSON.toJSONString(tbList));

            List<Map<String,String>> mapList= HtmlUtilsZj.getTdinfoaList(tbList);

            System.out.println(JSON.toJSONString(mapList));

        }
        catch ( Exception e){
            e.printStackTrace();

        }


    }


}
