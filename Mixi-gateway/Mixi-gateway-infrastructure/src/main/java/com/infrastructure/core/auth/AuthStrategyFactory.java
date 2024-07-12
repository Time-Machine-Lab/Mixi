package com.infrastructure.core.auth;

import com.mixi.common.annotation.auth.AuthType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

import static com.mixi.common.annotation.auth.AuthType.*;

/**
 * 描述: 权限策略工厂
 * @author suifeng
 * 日期: 2024/7/12 
 */
@Component
@Slf4j
public class AuthStrategyFactory {

    private final Map<AuthType, AuthStrategy> authStrategyMap = new EnumMap<>(AuthType.class);

    @Autowired
    public AuthStrategyFactory(InnerAuthStrategy innerAuthStrategy, NotAuthStrategy notAuthStrategy, NeedAuthStrategy needAuthStrategy) {
        authStrategyMap.put(INNER, innerAuthStrategy);
        authStrategyMap.put(NOT, notAuthStrategy);
        authStrategyMap.put(NEED, needAuthStrategy);
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