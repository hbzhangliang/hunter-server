package cn.com.cubic.platform.hunter.controller.aop;

import cn.com.flaginfo.platform.api.common.base.BaseResponse;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BaseAspect {

    private static final Logger log = LoggerFactory.getLogger(BaseAspect.class);

    @Pointcut("execution(* cn.com.cubic.platform.hunter.controller.*Controller.*(..))")
    public void pointcut(){
    }


    @Around("pointcut()")
    public Object aroundRequest(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        long startTime = System.currentTimeMillis();
        String methodInfo="";
        if(null!=args&&args.length>0){
//            methodInfo= String.format("method:[%s],args:[%s]",methodName, JSONObject.toJSONString(args[0]));
        }
        log.info("Before request url,{}", methodInfo);
        Object result = null;
        try {
            result = pjp.proceed(args);
        } catch (Throwable ex) {
            log.error("Exception : {}", ex);
            throw ex;
        }
        long tokeTime = System.currentTimeMillis() - startTime;
        log.info("After request,{},完成, 花费总时间：[{}] ms \n request result:[{}]", methodInfo, tokeTime, result);
        //结果转为json字符串
        return new BaseResponse<>(null,result);
    }


}
