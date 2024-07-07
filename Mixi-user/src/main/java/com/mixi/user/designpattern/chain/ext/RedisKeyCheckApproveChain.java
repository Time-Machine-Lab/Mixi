package com.mixi.user.designpattern.chain.ext;

import com.mixi.user.designpattern.chain.ApproveChain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.mixi.user.constants.CommonConstant.COMMON_ERROR;

/**
 * @NAME: RedisKeyCheckApproveChain
 * @USER: yuech
 * @Description:看某些key是否存在于redis
 * @DATE: 2024/6/28
 */
@Scope("prototype")
@Component
@RequiredArgsConstructor
@Slf4j
public class RedisKeyCheckApproveChain extends ApproveChain {

    private final RedisTemplate redisTemplate;

    @Override
    public boolean approve() {
        if(Objects.isNull(redisTemplate.opsForValue().get(getParams()))){
            return getNextChain().approve();
        }
        log.debug("请不要重复发送");
        throw new RuntimeException(COMMON_ERROR);
    }
}