package cn.com.cubic.platform.test;

import cn.com.cubic.platform.utils.HtmlUtils;
import cn.com.cubic.platform.utils.UtilHelper;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class TestBooks {

    private static final Logger log = LoggerFactory.getLogger(TestBooks.class);
    private static String host="http://www.kanunu8.com";

    public static void main(String[] args){
        String url ="/files/writer/7190.html";
        if (url.indexOf("kanunu") == -1) {
            url = UtilHelper.contactHostUrl(host, url);
        }
        List<Map<String, String>> tmp = HtmlUtils.getBookNameAndUrl(url);
        if(null==tmp){
            return;
        }
        log.info("generateBooks url [{}],books[{}]", url, JSONObject.toJSONString(tmp));
    }

}
