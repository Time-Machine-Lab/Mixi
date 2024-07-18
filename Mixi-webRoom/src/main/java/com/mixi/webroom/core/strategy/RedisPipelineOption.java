package com.mixi.webroom.core.strategy;

import org.springframework.data.redis.connection.RedisConnection;

import java.util.concurrent.TimeUnit;

/**
 * @Author：XiaoChun
 * @Date：2024/7/18 下午4:18
 */
public interface RedisPipelineOption {
    <T>void execute(String key, T value, RedisConnection connection, Long expire, TimeUnit timeUnit);
}
