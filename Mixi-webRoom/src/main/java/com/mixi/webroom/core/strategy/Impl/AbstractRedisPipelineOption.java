package com.mixi.webroom.core.strategy.Impl;

import com.mixi.webroom.core.strategy.RedisPipelineOption;
import org.springframework.data.redis.connection.RedisConnection;

import java.util.concurrent.TimeUnit;

/**
 * @Author：XiaoChun
 * @Date：2024/7/18 下午4:19
 */
public class AbstractRedisPipelineOption implements RedisPipelineOption {
    @Override
    public <T> void execute(String key, T value, RedisConnection connection, Long expire, TimeUnit timeUnit) {

    }
}