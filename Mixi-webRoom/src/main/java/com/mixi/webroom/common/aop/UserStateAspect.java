package com.mixi.webroom.common.aop;

import com.mixi.common.constant.enums.UserStateEnum;
import com.mixi.webroom.common.annotation.UserState;
import com.mixi.webroom.common.enums.ResultEnums;
import com.mixi.webroom.common.exception.ServerException;
import com.mixi.webroom.config.RedisKeyConfig;
import com.mixi.webroom.utils.RedisUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @Author：XiaoChun
 * @Date：2024/7/4 上午11:50
 */
@Aspect
@Component
@Order(1)
public class UserStateAspect {
    private final Logger logger = Logger.getLogger(UserStateAspect.class.getName());

    @Resource
    RedisUtil redisUtil;

    @Pointcut(value = "@annotation(com.mixi.webroom.common.annotation.UserState)")
    public void pointCut() {

    }

    /**
     *前置通知，在切点执行之前执行的操作
     *
     */
    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        try {
            String uid  = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getHeader("uid");
            UserStateEnum userStateEnum = Objects.requireNonNull(AnnotationUtils.findAnnotation(((MethodSignature) joinPoint.getSignature()).getMethod(), UserState.class)).value();
            if(userStateEnum.getUserState() == redisUtil.getCacheObject(RedisKeyConfig.userNettyState(uid))){
                throw new ServerException(ResultEnums.USER_STATE_ERROR);
            }
        } catch (Exception e){
            logger.warning("UserStateAspect before error");
            e.printStackTrace();
        }
    }
}
