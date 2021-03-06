package cn.com.cubic.platform.hunter.controller.aop;

import cn.com.cubic.platform.utils.RedisUtils;
import cn.com.cubic.platform.utils.UtilHelper;
import com.sun.xml.internal.rngom.util.Utf16;
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
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/8/15.
 **/
@Aspect
@Component
@Order(10)
public class TagAspect {

    private static final Logger log = LoggerFactory.getLogger(TagAspect.class);

    @Autowired
    private RedisUtils redisUtils;

    @Pointcut("execution(* cn.com.cubic.platform.hunter.mysql.services.TBizTagService.listAll(..)) ||" +
            " execution(* cn.com.cubic.platform.hunter.mysql.services.TBizTagService.tree(Long))||" +
            "execution(* cn.com.cubic.platform.hunter.mysql.services.TBizTagService.tree(String))")
    public void pointcutAddRedis(){
    }

    @Pointcut("execution(* cn.com.cubic.platform.hunter.mysql.services.TBizTagService.saveOrUpdate(..))||" +
            "execution(* cn.com.cubic.platform.hunter.mysql.services.TBizTagService.del(..))")
    public void pointcutDelRedis(){
    }

    @Around("pointcutAddRedis()")
    public Object aroundAddRequest(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        String para="";
        String type="";
        if(null!=args&&args.length>0){
            type=args[0].getClass().getName();
            para=args[0].toString();
        }
        Object result = null;
        //逻辑处理  添加缓存
        String redisKey="",redisType="",redisTimeKey1="tag_listall",redisTimeKey2="tag_tree",redisTimeKey3="tag_tree_bycode";
        if(methodName.equals("tree")){
            if(type.indexOf("String")>-1){
                redisKey= UtilHelper.contacsString(redisTimeKey3,para);
                redisType=redisTimeKey3;
            }
            else {
                redisKey= UtilHelper.contacsString(redisTimeKey2,para);
                redisType=redisTimeKey2;
            }
        }
        else {
            redisKey= UtilHelper.contacsString(redisTimeKey1,para);
            redisType=redisTimeKey1;
        }

        if (StringUtils.isNotEmpty(redisKey)) {
            Object obj=redisUtils.getObj(redisKey);
            if(null!=obj){
                log.info("get tag tree from redis");
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
//        redisUtils.delKeys("tag_listall","tag_tree");
        redisUtils.delKeysPatners("0tag_listall","0tag_tree","0tag_tree_bycode");

        return result;

    }

}
