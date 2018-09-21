package cn.com.cubic.platform.hunter.controller.aop;

import cn.com.cubic.platform.hunter.mysql.entity.TLogLogin;
import cn.com.cubic.platform.hunter.mysql.mapper.TLogLoginMapper;
import cn.com.cubic.platform.hunter.mysql.services.TLogLoginService;
import cn.com.cubic.platform.utils.IpAddressUtils;
import cn.com.cubic.platform.utils.UtilHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/9/6.
 **/
@Aspect
@Component
@Order(10)
public class AccountAspect {

    private static final Logger log = LoggerFactory.getLogger(AccountAspect.class);


    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;


    @Autowired
    private TLogLoginMapper logLoginMapper;


    @Pointcut("execution(* cn.com.cubic.platform.hunter.controller.AccountController.checkInfo(..))")
    public void pointcutLogin(){
    }

    @Around("pointcutLogin()")
    public Object aroundAddRequest(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();


        String methodName = pjp.getSignature().getName();

        Object[] args = pjp.getArgs();
        String account="";
        if(null!=args&&args.length>0){
            HashMap map=((HashMap)args[0]);
            account=map.get("account").toString();
        }
        //记录日志数据
        String ipAddress= IpAddressUtils.getRemoteAddress(request);
        String sysInfo=IpAddressUtils.getRequestSystemInfo(request);
        String browers=IpAddressUtils.getRequestBrowserInfo(request);
        this.remarkLog(account,ipAddress,sysInfo,browers);

        Object result = null;
        try {
            result = pjp.proceed(args);
        } catch (Throwable ex) {
            log.error("Exception : {}", ex);
            throw ex;
        }
        return result;
    }



    private void remarkLog(String account,String ip,String sysInfo,String browers){
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                TLogLogin logLogin=new TLogLogin();
                logLogin.setLoginAccount(account);
                logLogin.setIpAddress(ip);
                logLogin.setSysInfo(sysInfo);
                logLogin.setBrowser(browers);
                logLogin.setHostName(IpAddressUtils.getHostName(ip));
                logLogin.setMacAddress(IpAddressUtils.getMacAddress(ip));
                logLogin.setCreateTime(new Date());
                logLoginMapper.insert(logLogin);
            }
        });
    }


}
