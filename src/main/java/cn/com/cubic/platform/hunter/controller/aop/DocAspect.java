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
 * Created by Liang.Zhang on 2018/9/3.
 **/
@Aspect
@Component
@Order(10)
public class DocAspect {

    private static final Logger log = LoggerFactory.getLogger(DocAspect.class);

    @Autowired
    private RedisUtils redisUtils;


    @Pointcut("execution(* cn.com.cubic.platform.hunter.controller.DocController.allTree(..)) ")
    public void pointcutAddRedis(){
    }


    @Around("pointcutAddRedis()")
    public Object aroundAddRequest(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();

        //逻辑处理  添加缓存
        String redisKey="share_tree";
        if (StringUtils.isNotEmpty(redisKey)) {
            Object obj=redisUtils.getObj(redisKey);
            if(null!=obj){
                log.info("get share_tree tree from redis");
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


}
