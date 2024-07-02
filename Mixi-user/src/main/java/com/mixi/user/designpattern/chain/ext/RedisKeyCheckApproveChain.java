package com.mixi.user.designpattern.chain.ext;

import com.mixi.user.designpattern.chain.ApproveChain;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @NAME: RedisKeyCheckApproveChain
 * @USER: yuech
 * @Description:看某些key是否存在于redis
 * @DATE: 2024/6/28
 */
@Scope("prototype")
@Component
@RequiredArgsConstructor
public class RedisKeyCheckApproveChain extends ApproveChain {

    private final RedisTemplate redisTemplate;

    @Override
    public boolean approve() {
        if(Objects.isNull(redisTemplate.opsForValue().get(getParams()))){
            return getNextChain().approve();
        }
        throw new RuntimeException("请不要重复发送");
    }
}