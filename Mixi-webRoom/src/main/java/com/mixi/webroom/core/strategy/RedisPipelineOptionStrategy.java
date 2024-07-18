package com.mixi.webroom.core.strategy;

import com.mixi.webroom.core.strategy.Impl.AbstractCallBack;
import com.mixi.webroom.core.strategy.Impl.AbstractRedisPipelineOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author：XiaoChun
 * @Date：2024/7/18 下午4:04
 */
@Component
public class RedisPipelineOptionStrategy extends Strategy<AbstractRedisPipelineOption>{
    @Autowired
    public RedisPipelineOptionStrategy(ApplicationContext applicationContext) {
        super(applicationContext.getBeansOfType(AbstractRedisPipelineOption.class));
    }
}