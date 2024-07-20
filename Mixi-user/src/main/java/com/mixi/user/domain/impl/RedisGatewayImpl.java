package com.mixi.user.domain.impl;

import com.mixi.user.domain.RedisGateway;
import io.github.common.RedisKey;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.mixi.user.constants.MixiUserConstant.INVALID_TIME;

@Component
public class RedisGatewayImpl implements RedisGateway {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String get(RedisKey redisKey,Object... args){
        String key = redisKey.getKey(args);
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void set(RedisKey redisKey,String value,Object... args){
        String key = redisKey.getKey(args);
        stringRedisTemplate.opsForValue().set(key,value);
        if(redisKey.getTime()!=INVALID_TIME){
            stringRedisTemplate.expire(key,redisKey.getTime(),redisKey.getTimeUnit());
        }
    }

    @Override
    public boolean setIfAbsent(RedisKey redisKey, String value, Object... args) {
        String key = redisKey.getKey(args);
        Boolean res = stringRedisTemplate.opsForValue().setIfAbsent(key, value);
        if(Boolean.TRUE.equals(res)&&redisKey.getTime()!=INVALID_TIME){
            stringRedisTemplate.expire(key,redisKey.getTime(),redisKey.getTimeUnit());
        }
        return Boolean.TRUE.equals(res);
    }

    @Override
    public StringRedisTemplate template() {
        return stringRedisTemplate;
    }
}
