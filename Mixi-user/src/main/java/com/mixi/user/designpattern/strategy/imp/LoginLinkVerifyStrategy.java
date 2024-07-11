package com.mixi.user.designpattern.strategy.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mixi.user.designpattern.chain.ApproveChain;
import com.mixi.user.designpattern.chain.ApproveChainBuilder;
import com.mixi.user.designpattern.strategy.LinkVerifyStrategy;
import com.mixi.user.designpattern.strategy.LinkVerifyStrategyFactory;
import com.mixi.user.domain.entity.User;
import com.mixi.user.mapper.UserMapper;
import com.mixi.user.service.impl.RedisDaoService;
import com.mixi.user.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.mixi.user.constants.CommonConstant.COMMON_ERROR;
import static com.mixi.user.constants.RedisConstant.REDIS_PRE;

/**
 * @NAME: LoginLinkVerifyStrategy
 * @USER: yuech
 * @Description:
 * @DATE: 2024/7/6
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class LoginLinkVerifyStrategy implements LinkVerifyStrategy {

    private final RedisDaoService redisDaoService;
    private final UserMapper userMapper;
    private final String NAME = "LoginLinkVerifyStrategy";
    private final ApproveChainBuilder approveChainBuilder;

    @Override
    public String process(String email, String uid) {
        approveChainBuilder.buildInstance()
                .set("MysqlQueryExistCheck", "email", email)
                .set("RedisQueryExistCheck", REDIS_PRE + email + ":" + "uid", uid)
                .Build()
                .approve();
        redisDaoService.deleteKeys(REDIS_PRE + email + ":" + "uid",REDIS_PRE + email + ":" + "times");
        User user = (User) ApproveChain.res.get().get("MysqlQueryExistCheck");
        return TokenUtil.getToken(user.getId(),email);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LinkVerifyStrategyFactory.register(NAME,this);
    }
}