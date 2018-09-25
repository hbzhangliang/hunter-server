package cn.com.cubic.platform.test;

import cn.com.cubic.platform.hunter.mysql.entity.TSysAccount;
import cn.com.cubic.platform.hunter.mysql.entity.TSysAccountExample;
import cn.com.cubic.platform.hunter.mysql.services.SysAccountService;
import cn.com.cubic.platform.utils.ComEnum;
import cn.com.cubic.platform.utils.IpAddressUtils;
import cn.com.cubic.platform.utils.UtilHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by liang_zhang on 2017/10/14.
 */
public class Test {


    public static void main(String[] args) {

        System.out.println(ComEnum.CorpType.Develop.getDesc());
    }


}
