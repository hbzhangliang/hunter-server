package cn.com.cubic.platform.utils.global;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Liang.Zhang on 2018/8/13.
 **/

public class GlobalHolder {

    private static Set<String> tokenSet=new HashSet<>(500);

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


    public static Set<String> getTokenSet() {
        return tokenSet;
    }

    public static void setTokenSet(Set<String> tokenSet) {
        GlobalHolder.tokenSet = tokenSet;
    }
}
