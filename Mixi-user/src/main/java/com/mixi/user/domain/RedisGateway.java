package com.mixi.user.domain;

import io.github.common.RedisKey;
import org.springframework.data.redis.core.StringRedisTemplate;

public interface RedisGateway {

    String getAndSet(RedisKey redisKey, String value, Object... args);
    String get(RedisKey redisKey,Object... args);
    void set(RedisKey redisKey, String value, Object... args);

    boolean setIfAbsent(RedisKey redisKey, String value, Object... args);
    StringRedisTemplate template();
}
