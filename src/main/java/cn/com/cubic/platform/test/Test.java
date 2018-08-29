package cn.com.cubic.platform.test;

import cn.com.cubic.platform.hunter.mysql.entity.TSysAccount;
import cn.com.cubic.platform.hunter.mysql.entity.TSysAccountExample;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by liang_zhang on 2017/10/14.
 */
public class Test {


    public static void main(String[] args) throws Exception{


        /*
        TSysAccount account=new TSysAccount();

        Class c=account.getClass();
        Field[] fields=c.getDeclaredFields();
        for(Field item:fields){
            System.out.println(item.getName());
        }

        Method[] methods=c.getMethods();
        for(Method item:methods){
            System.out.println(item.getName());
        }



        String className="cn.com.cubic.platform.hunter.mysql.entity.TSysAccount";
        String methodName="getName";
        Class clz=Class.forName(className);
        Object obj=clz.newInstance();

        Method m=obj.getClass().getDeclaredMethod(methodName,null);
        String result=(String) m.invoke(obj,null);
        System.out.println("vvvv------");
        System.out.println(result);
*/


        TSysAccountExample.Criteria criteria=new TSysAccountExample().createCriteria();
        Class c=criteria.getClass();
        Field[] fields=c.getDeclaredFields();
        for(Field item:fields){
            System.out.println(item.getName());
        }



        String strEq="id";
        Method[] methods=c.getMethods();
        for(Method item:methods){
            Class[] paramTypes = item.getParameterTypes();
            for (Class class2 : paramTypes) {
                System.out.print(class2.getSimpleName());

                Type type=class2.getGenericSuperclass();
                System.out.println(type);

            }
            System.out.println();
//            System.out.println(item.getGenericParameterTypes());

        }




    }


}
