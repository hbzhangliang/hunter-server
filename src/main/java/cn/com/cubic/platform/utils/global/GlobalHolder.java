package cn.com.cubic.platform.utils.global;

import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/8/13.
 **/

public class GlobalHolder {

    private static final ThreadLocal<Map<String,Object>> requestHolder = new ThreadLocal<>();

    public static Map<String,Object> get(){
        return requestHolder.get();
    }

    public static void set(Map<String,Object> map){
        requestHolder.set(map);
    }

    public static void remove(){
        requestHolder.remove();
    }

}
