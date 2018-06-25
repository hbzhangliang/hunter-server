package cn.com.cubic.platform.test;

import cn.com.cubic.platform.utils.HtmlUtils;

import java.util.List;
import java.util.Map;

public class TestCapter {

    public static void main(String[] args){
        try {
//            String strUrl = "http://www.kanunu8.com/files/yqxs/201107/3284.html";

            String strUrl="http://www.kanunu8.com/files/chinese/201106/3181.html";

            List<Map<String,String>> result= HtmlUtils.getCapterNameAndUrl(strUrl);

            System.out.println(result);




        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
