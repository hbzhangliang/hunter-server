package cn.com.cubic.platform.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlUtils {


    //完整的url
    public static List<Map<String,String>> getBookNameAndUrl(String strUrl){
        try {
            if (strUrl.indexOf("/files/") > -1) {
                String content = JSONHelper.loadJson(strUrl);
                content = HtmlUtilsFiles.delBlank(content);
                String tableContent= HtmlUtilsFiles.getTableContent(content);
                List<String> tbList = HtmlUtilsFiles.tagAcontent(tableContent);
                return HtmlUtilsFiles.tagADetail(tbList);
            }
            else if(strUrl.indexOf("/zj/") > -1){
                String content = JSONHelper.loadJson(strUrl);
                content= HtmlUtilsZj.delBlank(content);
                String tableContent= HtmlUtilsZj.getTableContent(content);
                List<String> tbList= HtmlUtilsZj.getTableTdList(tableContent);
                return HtmlUtilsZj.getTdinfoaList(tbList);
            }
            else if(strUrl.indexOf("/author/")>-1){
                String content = JSONHelper.loadJsonUTF(strUrl);
                content = HtmlUtilsAuthor.delBlank(content);
                content=HtmlUtilsAuthor.getTableContent(content);
                List<String> tbList = HtmlUtilsAuthor.tagAcontent(content);
                return HtmlUtilsAuthor.tagADetail(tbList);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



    public static List<Map<String,String>> getCapterNameAndUrl(String strUrl){
        try {
            String content = JSONHelper.loadJson(strUrl);

            if(content.indexOf(">p>")==-1) {
                String tableContent = HtmlUtilsCapter.getTableContent(content);
                List<String> tableTds = HtmlUtilsCapter.getTableTdList(tableContent);
                return HtmlUtilsCapter.getTdinfoaList(tableTds);
            }
            else {
                List<Map<String,String>> result=new ArrayList<>(1);
                Map<String,String> p=new HashMap<>(3);
                p.put("name","正文");
                p.put("content",HtmlUtilsCapter.getSubContent(content));
                result.add(p);
                return result;
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }


    public static String getCapterContent(String strUrl){
        try{
            String content = JSONHelper.loadJson(strUrl);
            return HtmlUtilsCapter.getSubContent(content);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
