package com.mixi.user.designpattern.chain.ext;

import com.mixi.user.designpattern.chain.ApproveChain;
import com.mixi.user.service.impl.RedisDaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

import static com.mixi.user.constants.CommonConstant.COMMON_ERROR;

/**
 * @NAME: RedisQueryExistCheck
 * @USER: yuech
 * @Description:
 * @DATE: 2024/7/10
 */
@Scope("prototype")
@Component("RedisQueryExistCheck")
@RequiredArgsConstructor
@Slf4j
//redis中某一值是否存在
public class RedisQueryExistCheck extends ApproveChain {

    private final String NAME = "RedisQueryExistCheck";

    private final RedisDaoService redisDaoService;

    @Override
    public boolean approve() {
        String redisDataMap = (String) redisDaoService.query(getParams()[0]);
        if (Objects.isNull(redisDataMap) || !Objects.equals(getParams()[1], redisDataMap)) {
            log.info("无匹配对象");
            throw new RuntimeException(COMMON_ERROR);
        }
        Map map = checkRes();
        map.put(NAME,redisDataMap);
        if (Objects.isNull( getNextChain())){
            return true;
        }
        ApproveChain nextChain = getNextChain();
        return nextChain.approve();
    }
}