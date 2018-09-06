package cn.com.cubic.platform.test;

import cn.com.cubic.platform.hunter.mysql.entity.TSysAccount;
import cn.com.cubic.platform.hunter.mysql.entity.TSysAccountExample;
import cn.com.cubic.platform.hunter.mysql.services.SysAccountService;
import cn.com.cubic.platform.utils.ComEnum;
import cn.com.cubic.platform.utils.UtilHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liang_zhang on 2017/10/14.
 */
public class Test {


    public static void main(String[] args){


        String aa="position3,position5,team10,allall";

        for(String item:aa.split(",")){
            System.out.println(UtilHelper.cleanShareType(item));
        }


    }


}
