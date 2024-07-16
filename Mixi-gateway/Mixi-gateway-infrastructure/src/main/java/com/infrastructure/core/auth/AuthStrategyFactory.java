package com.infrastructure.core.auth;

import com.mixi.common.annotation.auth.AuthType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import java.util.EnumMap;
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(AuthStrategyType.class);
        for (Object bean : beansWithAnnotation.values()) {
            AuthStrategyType annotation = bean.getClass().getAnnotation(AuthStrategyType.class);
            authStrategyMap.put(annotation.value(), (AuthStrategy) bean);
        }
        log.info("AuthStrategyFactory initialized with strategies: {}", authStrategyMap.keySet());
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
}