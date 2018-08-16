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

import java.util.HashMap;

/**
 * Created by Liang.Zhang on 2018/8/16.
 **/
@Aspect
@Component
@Order(10)
public class DictionaryAspect {

    private static final Logger log = LoggerFactory.getLogger(DictionaryAspect.class);

    @Autowired
    private RedisUtils redisUtils;

    @Pointcut("execution(* cn.com.cubic.platform.hunter.controller.DictionaryController.listChildren(..)) ||" +
            " execution(* cn.com.cubic.platform.hunter.controller.DictionaryController.listAll())")
    public void pointcutAddRedis(){
    }

    @Pointcut("execution(* cn.com.cubic.platform.hunter.controller.DictionaryController.save(..))||" +
            "execution(* cn.com.cubic.platform.hunter.controller.DictionaryController.del(..))")
    public void pointcutDelRedis(){
    }

    @Around("pointcutAddRedis()")
    public Object aroundAddRequest(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();

        String para="";
        if(null!=args&&args.length>0){
            para=((HashMap)args[0]).get("pId").toString();
        }

        Object result = null;
        //逻辑处理  添加缓存
        String redisKey="",redisType="",redisTimeKey1="dict_children",redisTimeKey2="dict_all";
        switch (methodName){
            case "listChildren":{
                redisKey= UtilHelper.contacsString(redisTimeKey1,para);
                redisType=redisTimeKey1;
            };break;
            case "listAll":{
                redisKey= redisTimeKey2;
                redisType=redisTimeKey2;
            }break;
            default:break;
        }
        if (StringUtils.isNotEmpty(redisKey)) {
            Object obj=redisUtils.getObj(redisKey);
            if(null!=obj){
                log.info("get dict from redis");
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
        redisUtils.setObj(redisKey,result,redisType);

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
        redisUtils.delKeysPatners("0dict_children");
        redisUtils.delKeys("dict_all");
        return result;

    }

}
