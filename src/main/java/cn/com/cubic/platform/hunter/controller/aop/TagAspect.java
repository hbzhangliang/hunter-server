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

    @Pointcut("execution(* cn.com.cubic.platform.hunter.controller.TagController.listAll(..)) ||" +
            " execution(* cn.com.cubic.platform.hunter.controller.TagController.tree(..))||" +
            "execution(* cn.com.cubic.platform.hunter.controller.TagController.treeByCode(..))")
    public void pointcutAddRedis(){
    }

    @Pointcut("execution(* cn.com.cubic.platform.hunter.controller.TagController.save(..))||" +
            "execution(* cn.com.cubic.platform.hunter.controller.TagController.del(..))")
    public void pointcutDelRedis(){
    }

    @Around("pointcutAddRedis()")
    public Object aroundAddRequest(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();

        String para="";
        if(null!=args&&args.length>0){
            HashMap map=((HashMap)args[0]);
            //
            Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                para+=entry.getValue().toString();
            }
        }

        Object result = null;
        //逻辑处理  添加缓存
        String redisKey="",redisType="",redisTimeKey1="tag_listall",redisTimeKey2="tag_tree",redisTimeKey3="tag_tree_bycode";
        switch (methodName){
            case "listAll":{
                redisKey= UtilHelper.contacsString(redisTimeKey1,para);
                redisType=redisTimeKey1;
            };break;
            case "treeByCode":{
                redisKey= UtilHelper.contacsString(redisTimeKey3,para);
                redisType=redisTimeKey3;
            };break;
            case "tree":{
                redisKey= UtilHelper.contacsString(redisTimeKey2,para);
                redisType=redisTimeKey2;
            }break;
            default:break;
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
