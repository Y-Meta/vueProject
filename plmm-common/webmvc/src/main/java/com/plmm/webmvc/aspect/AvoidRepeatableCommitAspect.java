package com.plmm.webmvc.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import com.plmm.common.utils.ResultUtil;
import com.plmm.localcache.service.ICacheContextService;
import com.plmm.webmvc.aspect.annotation.AvoidRepeatableCommit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.plmm.utils.IpUtils;
import com.plmm.utils.StringUtils;

/**
 * 重复提交aop拦截
 */
@Aspect
@Component
public class AvoidRepeatableCommitAspect {

    private static final Logger log = LoggerFactory.getLogger(AvoidRepeatableCommitAspect.class);

    @Autowired
    private ICacheContextService cacheContextService;

    /**
     * @param point
     */
    @Around("@annotation(com.plmm.webmvc.aspect.annotation.AvoidRepeatableCommit)")
    public Object around(ProceedingJoinPoint point) throws Throwable{

        HttpServletRequest request  = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = IpUtils.getIpAddr(request);
        //获取注解
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        //目标类、方法
        String className = method.getDeclaringClass().getName();
        String name = method.getName();
        String ipKey = String.format("%s#%s",className,name);
        int hashCode = Math.abs(ipKey.hashCode());
        String key = String.format("%s_%d",ip,hashCode);
        log.info("ipKey={},hashCode={},key={}",ipKey,hashCode,key);
        AvoidRepeatableCommit avoidRepeatableCommit =  method.getAnnotation(AvoidRepeatableCommit.class);
        int timeout = avoidRepeatableCommit.timeout();
        if ( timeout < 0){
            timeout = 3;
        }
        String value = cacheContextService.get(key);
        if (StringUtils.isNotBlank(value)){
//            return ResultUtil.error(MessageUtils.message("method.repeat.commit"));
            return ResultUtil.error("重复提交");
        }
        cacheContextService.set(key, "0");
        cacheContextService.expire(key, timeout);
        //执行方法
        Object object = point.proceed();
        return object;
    }

}
