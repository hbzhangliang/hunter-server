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
import java.net.URL;
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



        //创建一个URL的实例
        URL baidu =new URL("http://www.baidu.com");
        URL url =new URL(baidu,"/index.html?username=tom#test");//？表示参数，#表示锚点
        url.getProtocol();//获取协议
        url.getHost();//获取主机
        url.getPort();//如果没有指定端口号，根据协议不同使用默认端口。此时getPort()方法的返回值为 -1
        url.getPath();//获取文件路径
        url.getFile();//文件名，包括文件路径+参数
        url.getRef();//相对路径，就是锚点，即#号后面的内容
        url.getQuery();//查询字符串，即参数

         i=10;


//获取其他主机的InetAddress实例
//        InetAddress address2 =InetAddress.getByName("其他主机名");
//        InetAddress address3 =InetAddress.getByName("IP地址");
//
    }


}
