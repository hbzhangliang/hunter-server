package cn.com.cubic.platform.test;

import cn.com.cubic.platform.hunter.mysql.entity.TSysAccount;
import cn.com.cubic.platform.hunter.mysql.entity.TSysAccountExample;

import java.lang.reflect.Field;

/**
 * Created by liang_zhang on 2017/10/14.
 */
public class Test {


    public static void main(String[] args){


        TSysAccount account=new TSysAccount();

        Class c=account.getClass();
        Field[] fields=c.getDeclaredFields();
        for(Field item:fields){
            System.out.println(item.getName());
        }


        TSysAccountExample example=new TSysAccountExample();



    }


}
