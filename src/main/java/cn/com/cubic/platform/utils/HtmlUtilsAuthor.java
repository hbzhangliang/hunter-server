package cn.com.cubic.platform.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlUtilsAuthor {

    private static final Logger log = LoggerFactory.getLogger(HtmlUtilsAuthor.class);

    private String host="http://www.kanunu8.com";

    //作者文章列表

    /**
     *
     * <atarget="_blank"href="/book3/7191/159360.html">悼魏东</a>
     *
     * 删除空格
     * @param content
     * @return
     */
    public static String delBlank(String content){
        return content.replace(" ","");
    }


    public static String getTableContent(String content){
        String pStart="<tablebgcolor",pEnd="table>";
        int p1=content.indexOf(pStart)-1,p2=content.indexOf(pEnd,p1)+6;
        return content.substring(p1,p2);
    }


    public static List<String> tagAcontent(String content){
        content=delBlank(content).toLowerCase();
        String p="<ahref",q="</a>";

        List<String> result=new ArrayList<>(100);
        int startPosition=content.indexOf(p),endPosition=content.indexOf(q,startPosition)+4;
        while (startPosition!=-1&&endPosition!=-1&&startPosition<endPosition){
            String pd=content.substring(startPosition,endPosition);
            result.add(pd);
            content=content.substring(endPosition);
            startPosition=content.indexOf(p);
            endPosition=content.indexOf(q,startPosition)+4;
        }

        return result;
    }

    /**
     * <atarget="_blank"href="/book3/7191/159360.html">悼魏东</a>
     * @param tags
     * @return
     */
    public static List<Map<String,String>> tagADetail(List<String> tags){
        List<Map<String,String>> result=new ArrayList<>(100);
        int startP=0,endP=0;
        for(String item:tags){
            Map<String,String> tmp=new HashMap<>(2);
            if(item.indexOf("href")==-1){
                continue;
            }
            startP=item.indexOf("href=")+6;
            endP=item.indexOf("\"",startP);
            tmp.put("url",item.substring(startP,endP));


            if(item.indexOf("fontcolor")>-1){
                //存在
                int k=item.indexOf("fontcolor");

                startP = item.indexOf(">",k) + 1;
                endP = item.indexOf("</font>");
            }
            else {
                startP = item.indexOf(">") + 1;
                endP = item.indexOf("</a>");
            }
            tmp.put("name", item.substring(startP, endP).replace("《", "").replace("》", ""));
            result.add(tmp);
        }
        return result;
    }


}
