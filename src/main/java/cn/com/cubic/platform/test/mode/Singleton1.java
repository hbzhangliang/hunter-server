package cn.com.cubic.platform.test.mode;

/**
 * Created by Liang.Zhang on 2018/8/3.
 **/
public class Singleton1 {
    private static final Singleton1 uniqueInstance=new Singleton1();
    private Singleton1(){}
    public static Singleton1 getUniqueInstance(){
        return uniqueInstance;
    }
}
