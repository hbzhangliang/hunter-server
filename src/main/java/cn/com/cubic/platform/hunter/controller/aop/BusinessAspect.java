package cn.com.cubic.platform.hunter.controller.aop;

import cn.com.cubic.platform.utils.RedisUtils;
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
 * Created by Liang.Zhang on 2018/8/13.
 **/
@Aspect
@Component
@Order(10)
public class BusinessAspect {

    private static final Logger log = LoggerFactory.getLogger(CityAspect.class);

    @Autowired
    private RedisUtils redisUtils;

    @Pointcut("execution(* cn.com.cubic.platform.hunter.mysql.services.TBizBusinessService.listAll()) ||" +
            " execution(* cn.com.cubic.platform.hunter.mysql.services.TBizBusinessService.tree())")
    public void pointcutAddRedis(){
    }

    @Pointcut("execution(* cn.com.cubic.platform.hunter.mysql.services.TBizBusinessService.saveOrUpdate(..))||" +
            "execution(* cn.com.cubic.platform.hunter.mysql.services.TBizBusinessService.del(..))")
    public void pointcutDelRedis(){
    }

    @Around("pointcutAddRedis()")
    public Object aroundAddRequest(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();

        //逻辑处理  添加缓存
        String redisKey="";
        switch (methodName){
            case "listAll":redisKey="business_listall";break;
            case "tree":redisKey="business_tree";break;
            default:break;
        }
        if (StringUtils.isNotEmpty(redisKey)) {
            Object obj=redisUtils.getObj(redisKey);
            if(null!=obj){
                log.info("get business tree from redis");
                return obj;
            }
        }


        Object[] args = pjp.getArgs();
        Object result = null;
        try {
            result = pjp.proceed(args);
        } catch (Throwable ex) {
            log.error("Exception : {}", ex);
            throw ex;
        }

        //逻辑处理
        redisUtils.setObj(redisKey,result,redisKey);

        return result;
    }


    @Around("pointcutDelRedis()")
    public Object aroundDelRequest(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        Object result = null;
        try {
            result = pjp.proceed(args);
        } catch (Throwable ex) {
            log.error("Exception : {}", ex);
            throw ex;
        }
        //删除缓存
        redisUtils.delKeys("business_listall","business_tree");

        return result;

    }

}
