package cn.com.cubic.platform.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class UtilHelper {

    private final static char UNDERLINE='_';

    private static final PropertiesUtils redisProper = new PropertiesUtils("spring/redis-time-config.properties");


    public static void  main(String[] args){
        String a="createTime";
        System.out.println(strToLikeStr(a));
    }

    /**
     * 连接字符串  z作为redis的key值
     * @param args
     * @return
     */
    public static String contacsString(Object ...args){
        if(args!=null&&args.length>0) {
            int index=0;
            StringBuilder sb=new StringBuilder();
            for (Object p : args) {
                if (null != p&&StringUtils.isNotBlank(String.valueOf(p))) {
                    sb.append(index+String.valueOf(p));
                }
                else {
                    sb.append(index+"_");
                }
                index++;
            }
            return sb.toString();
        }
        return null;
    }


    public static TimeUnit getTimeUtil(String type){
        if(StringUtils.isEmpty(type)) return TimeUnit.SECONDS;
        TimeUnit result=null;
        switch (type){
            case "S":result =TimeUnit.SECONDS;break;
            case "M":result =TimeUnit.MINUTES;break;
            case "H":result=TimeUnit.HOURS;break;
            case "D":result=TimeUnit.DAYS;break;
            default:result=TimeUnit.SECONDS;
        }
        return result;
    }

    /**
     * 将 redis-time-config改成按秒计算的时间
     * @param str
     * @return
     */
    public static int timeUtlToInt(String str){
        int result=0;
        String[] redispropers=redisProper.getPropertiesValues(str);
        int p=Integer.parseInt(redispropers[0]);
        String type=redispropers[1];
        switch (type){
            case "S":result=p;break;
            case "M":result=60*p;break;
            case "H":result=3600*p;break;
            case "D":result=3600*24*p;break;
            default:result=p;break;
        }
        return result;
    }


    //获取当前时间的String值
    public static String getNowStrTime(){
        return sdf.format(new Date());
    }

    private  static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    private  static SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");

    private  static SimpleDateFormat sdfYMDHM = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    public static Date parseDateYMD(String str)throws ParseException{
        return sdfYMD.parse(str);
    }

    public static Date parseDateYMDHM(String str)throws ParseException{
        return sdfYMDHM.parse(str);
    }

    /**
     * 将驼峰转为下横线
     * 比如createBy  ---> create_by
     * @param param
     * @return
     */
    public static String camelToUnderline(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     * @param param
     * @return
     */
    public static String underlineToCamel(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (c==UNDERLINE){
                if (++i<len){
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }



    public static String strToLikeStr(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("%").append(str).append("%");
        return sb.toString();
    }


    /**
     * postion3，postion4，position5 ----》3，4，5
     * @param str
     * @return
     */
    public static String cleanShareType(String str){
        if(StringUtils.isNotEmpty(str)){
            for(ComEnum.ShareType item:ComEnum.ShareType.values()){
                if(str.indexOf(item.toString())>-1){
                    str=str.replaceAll(item.toString(),"");
                    break;
                }
            }
            return str;
        }
        return null;
    }

    public static String getShareType(String str){
        if(StringUtils.isNotEmpty(str)){
            for(ComEnum.ShareType item:ComEnum.ShareType.values()){
                if(str.indexOf(item.toString())>-1){
                    return item.toString();
                }
            }
            return str;
        }
        return null;
    }

}
