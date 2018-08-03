package cn.com.cubic.platform.test.mode;

/**
 * Created by Liang.Zhang on 2018/8/3.
 **/
public class Singleton {
    private static Singleton uniqueInstance;
    private Singleton(){}
    public static synchronized Singleton getInstance(){
        if(null==uniqueInstance){
            uniqueInstance= new Singleton();
        }
        return uniqueInstance;
    }
}
