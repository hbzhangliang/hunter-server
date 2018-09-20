package cn.com.cubic.platform.test.myTry;

import javax.json.JsonObject;

/**
 * Created by Liang.Zhang on 2018/9/20.
 **/

public class test1 {


    public static void main(String[] args){
        System.out.println("ddddd");

        Cat cat=getInfo(new Dog());


    }




    private static  <T> Cat getInfo(T t){
        return new Cat();
    }


}
