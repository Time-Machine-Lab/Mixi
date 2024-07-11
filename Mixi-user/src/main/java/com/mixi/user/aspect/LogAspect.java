package com.mixi.user.aspect;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mixi.user.aspect.annotation.SystemLog;
import com.mixi.user.utils.logUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.mixi.user.constants.CommonConstant.LOG_LIMIT_NUM;


/**
 * 日志打印
 */

@Component
@Aspect
@Slf4j
public class LogAspect {
    @Pointcut("@annotation(com.mixi.user.aspect.annotation.SystemLog)")
    public void pt(){

    }

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis(); // 获取程序开始时间
        Object ret;
        try {
            handleBefore(joinPoint);
            ret = joinPoint.proceed();
            handleAfter(ret);
        } finally {
            long endTime = System.currentTimeMillis(); // 获取程序结束时间
            long totalTime = endTime - startTime; // 计算总运行时间
            log.info("run time :" + totalTime + "ms");
            log.info("=======End==================================="+System.lineSeparator());
        }

        return ret;
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();


        //获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);
        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}",systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}",request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}",joinPoint.getSignature().getDeclaringTypeName(),((MethodSignature) joinPoint.getSignature()).getName() );
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Response       : {}", toSafeJsonString(joinPoint.getArgs()));
        // 结束后换行
        log.info("=================" + System.lineSeparator());

    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature =(MethodSignature) joinPoint.getSignature();
        SystemLog annotation = methodSignature.getMethod().getAnnotation(SystemLog.class);
        return annotation;
    }

    private void handleAfter(Object ret) {
        log.info("Response       : {}",  toSafeJsonString(new Object[]{ret}));
    }

    public static String toSafeJsonString(Object[] args) {
        // 使用 SerializerFeature 控制序列化行为
        String jsonString = JSON.toJSONString(args,
                SerializerFeature.WriteSlashAsSpecial,  // 将斜杠'/'转义为 '\/' 形式
                SerializerFeature.DisableCircularReferenceDetect);
        // 使用流操作截取前 100 个字符，并返回截取后的字符串
        String res = logUtils.cleanMsg(jsonString).chars()
                .limit(LOG_LIMIT_NUM)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return res;
    }
}