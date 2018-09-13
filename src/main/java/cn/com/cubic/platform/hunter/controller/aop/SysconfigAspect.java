package cn.com.cubic.platform.hunter.controller.aop;

import cn.com.cubic.platform.utils.RedisUtils;
import cn.com.cubic.platform.utils.UtilHelper;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by Liang.Zhang on 2018/9/13.
 **/
@Aspect
@Component
@Order(10)
public class SysconfigAspect {

    private static final Logger log = LoggerFactory.getLogger(SysconfigAspect.class);

    @Autowired
    private RedisUtils redisUtils;

    @Pointcut("execution(* cn.com.cubic.platform.hunter.mysql.services.TSysConfigService.findByCode(..))")
    public void pointcutAddRedis(){
    }



    @Around("pointcutAddRedis()")
    public Object aroundAddRequest(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        String para="";
        if(null!=args&&args.length>0){
            para=args[0].toString();
        }
        Object result = null;
        //逻辑处理  添加缓存
        String redisTimeKey="sysconfig";
        String redisKey=UtilHelper.contacsString(redisTimeKey,para);

        if (StringUtils.isNotEmpty(redisKey)) {
            Object obj=redisUtils.getObj(redisKey);
            if(null!=obj){
                log.info("get sysconfig from redis");
                return obj;
            }
        }

        try {
            result = pjp.proceed(args);
        } catch (Throwable ex) {
            log.error("Exception : {}", ex);
            throw ex;
        }

        //逻辑处理
        redisUtils.setObj(redisKey,result,redisTimeKey);

        return result;
    }

}
