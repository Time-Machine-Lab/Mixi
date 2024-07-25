package com.mixi.webroom.core.strategy;

import com.mixi.webroom.core.strategy.Impl.AbstractCallBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author XiaoChun
 * @date 2024/7/17
 */
@Component
public class CallBackStrategy extends Strategy<AbstractCallBack> {
    @Autowired
    public CallBackStrategy(ApplicationContext applicationContext) {
        super(applicationContext.getBeansOfType(AbstractCallBack.class));
    }
}
