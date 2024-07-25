package com.mixi.webroom.core.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @Author：XiaoChun
 * @Date：2024/7/18 下午4:06
 */
public abstract class Strategy<STRATEGY> {
    private final Map<String, STRATEGY> strategyMap;

    protected Strategy(Map<String, STRATEGY> strategyMap) {
        this.strategyMap = strategyMap;
    }

    public STRATEGY getStrategy(String strategyType) {
        return strategyMap.get(strategyType);
    }
}