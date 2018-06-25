package cn.com.cubic.platform.test;

import cn.com.cubic.platform.utils.HtmlUtils;

import java.util.List;
import java.util.Map;

public class TestCaptt {


    public static void main(String[] args){
//        String tmpUrl="http://www.kanunu8.com/book3/6865/index.html";
//        String tmpUrl="http://www.kanunu8.com/book/3870/40537.html";
//        String tmpUrl="http://www.kanunu8.com/book/3497/index.html";
        //http://www.kanunu8.com/book3/7111/index.html
        String tmpUrl="http://www.kanunu8.com/book/5613/index.html";
        List<Map<String, String>> tmp =HtmlUtils.getCapterNameAndUrl(tmpUrl);

        System.out.println(tmp);
    }

}
