package com.infrastructure.core.auth;

import com.infrastructure.core.auth.annotation.AfterHandler;
import com.infrastructure.core.auth.annotation.AuthStrategyType;
import com.infrastructure.core.auth.annotation.BeforeHandler;
import com.infrastructure.core.auth.handler.DefaultHandler;
import com.mixi.common.annotation.auth.AuthType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述: 权限策略工厂
 * @author suifeng
 * 日期: 2024/7/12 
 */
@Component
@Slf4j
public class AuthStrategyFactory implements ApplicationContextAware {

    private final Map<AuthType, AuthStrategy> authStrategyMap = new EnumMap<>(AuthType.class);
    private final Map<String, AuthStrategy> beforeHandlerMap = new HashMap<>();
    private final Map<String, AuthStrategy> afterHandlerMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        Map<String, Object> beforeHandlerBeans = applicationContext.getBeansWithAnnotation(BeforeHandler.class);
        for (Object bean : beforeHandlerBeans.values()) {
            BeforeHandler annotation = bean.getClass().getAnnotation(BeforeHandler.class);
            beforeHandlerMap.put(annotation.value(), (AuthStrategy) bean);
        }
        log.info("Before handlers initialized: {}", beforeHandlerMap.keySet());


        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(AuthStrategyType.class);
        for (Object bean : beansWithAnnotation.values()) {
            AuthStrategyType annotation = bean.getClass().getAnnotation(AuthStrategyType.class);
            authStrategyMap.put(annotation.value(), (AuthStrategy) bean);
        }
        log.info("AuthStrategyFactory initialized with strategies: {}", authStrategyMap.keySet());

        Map<String, Object> afterHandlerBeans = applicationContext.getBeansWithAnnotation(AfterHandler.class);
        for (Object bean : afterHandlerBeans.values()) {
            AfterHandler annotation = bean.getClass().getAnnotation(AfterHandler.class);
            afterHandlerMap.put(annotation.value(), (AuthStrategy) bean);
        }
        log.info("After handlers initialized: {}", afterHandlerMap.keySet());
    }

    /**
     *  根据权限类型获取对应的策略实现
     */
    public AuthStrategy getStrategy(AuthType authType) {
        AuthStrategy strategy = authStrategyMap.get(authType);
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy found for auth type: " + authType);
        }
        return strategy;
    }

    /**
     *  获取前置处理器
     */
    public AuthStrategy getBeforeHandler(String handlerName) {
        AuthStrategy handler = beforeHandlerMap.get(handlerName);
        if (handler == null) {
            log.info("No before handler found for name: {}, executing default behavior (NIL)", handlerName);
            return beforeHandlerMap.get("NIL");
        }
        return handler;
    }

    /**
     *  获取后置处理器
     */
    public AuthStrategy getAfterHandler(String handlerName) {
        AuthStrategy handler = afterHandlerMap.get(handlerName);
        if (handler == null) {
            log.info("No after handler found for name: {}, executing default behavior (NIL)", handlerName);
            return afterHandlerMap.get("NIL");
        }
        return handler;
    }
}