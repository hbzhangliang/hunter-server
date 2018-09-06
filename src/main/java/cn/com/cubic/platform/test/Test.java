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
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liang_zhang on 2017/10/14.
 */
public class Test {


    public static void main(String[] args)throws Exception{

//获取本机的InetAddress实例
        InetAddress address =InetAddress.getLocalHost();
        address.getHostName();//获取计算机名
        address.getHostAddress();//获取IP地址
        byte[] bytes = address.getAddress();//获取字节数组形式的IP地址,以点分隔的四部分



        int i=10;

//获取其他主机的InetAddress实例
//        InetAddress address2 =InetAddress.getByName("其他主机名");
//        InetAddress address3 =InetAddress.getByName("IP地址");
//
    }


}
