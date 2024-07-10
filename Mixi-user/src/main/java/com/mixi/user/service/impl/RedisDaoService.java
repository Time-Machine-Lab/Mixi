package com.mixi.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @NAME: RedisDaoService
 * @USER: yuech
 * @Description:
 * @DATE: 2024/7/10
 */
@Component
@RequiredArgsConstructor
public class RedisDaoService {

    private final RedisTemplate redisTemplate;

    public Object query(String key){
        return  redisTemplate.opsForValue().get(key);
    }

    public void deleteKey(String key){
        redisTemplate.delete(key);
    }
    public void deleteKeys(String... keys){
        for (String key : keys) {
            redisTemplate.delete(key);
        }
    }
}