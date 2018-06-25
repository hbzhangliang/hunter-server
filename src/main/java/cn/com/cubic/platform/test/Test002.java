package cn.com.cubic.platform.test;

import cn.com.cubic.platform.utils.HtmlUtilsZj;
import cn.com.cubic.platform.utils.JSONHelper;

public class Test002 {

    /**
     * 章节内容
     * @param args
     */
    public static void main(String[] args){

        try{
            String strUrl = "http://www.kanunu8.com/book/4573/62828.html";

            String content = JSONHelper.loadJson(strUrl);

            System.out.println(content);

            String ss= HtmlUtilsZj.getSubContent(content);

            System.out.println(ss);

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
