package com.mixi.webroom.core.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XiaoChun
 * @date 2024/7/17
 */
@Component
public class CallBackStrategy {
    private final Map<String, AbstractCallBack> callBackMap;

    @Autowired
    public CallBackStrategy(ApplicationContext applicationContext) {
        callBackMap = applicationContext.getBeansOfType(AbstractCallBack.class);
    }

    public AbstractCallBack getCallBack(String callBackName) {
        return callBackMap.get(callBackName);
    }
}
