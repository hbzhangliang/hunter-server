package cn.com.cubic.platform.test.myTry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Liang.Zhang on 2018/8/2.
 **/

public class TxtReader {

    /**
     * 读取txt文件的内容
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }


    public static void main(String[] args){
        String path=TxtReader.class.getClass().getResource("/").getPath();
        File file=new File(path+"heart/kindheated.txt");
        String strInfo=txt2String(file);
        String startStr="nickname",endStr="money-comment";
        List<Map<String,Object>> list=new ArrayList<>(200);
        int startPt=0,endPt=0;
        while (strInfo.indexOf(startStr)>-1&&strInfo.indexOf(endStr)>-1){ //存在数据
            Map<String,Object> map=new HashMap<>(2);
            startPt=strInfo.indexOf(startStr)+10;
            endPt=strInfo.substring(startPt).indexOf("</span>");
            String name=strInfo.substring(startPt,startPt+endPt).trim();
            map.put("name",name);

            startPt=strInfo.substring(startPt+endPt).indexOf(endStr)+16+startPt+endPt;
            endPt=strInfo.substring(startPt).indexOf("</span>");
            String money=strInfo.substring(startPt,startPt+endPt);
            map.put("money",strToInt(money));
            list.add(map);
            strInfo=strInfo.substring(startPt+endPt);
            if(name.indexOf("FOX")>-1){
                break;
            }

        }
        //排序功能
        Collections.sort(list,new Comparator<Map<String,Object>>(){
            @Override
            public int compare(Map<String,Object> o1, Map<String,Object> o2) {
                Integer i1=strToInt(o1.get("money").toString());
                Integer i2=strToInt(o2.get("money").toString());
                return -i1.compareTo(i2);
//                return o1.get("name").compareTo(o2.get("name"));
            }
        });
        for(Map<String,Object>item:list){
            System.out.println(item.get("name")+":"+item.get("money"));
        }
    }


    public static int strToInt(String str) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return Integer.valueOf(m.replaceAll("").trim());
    }


}
