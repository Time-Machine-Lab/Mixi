package com.mixi.user.designpattern.strategy.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mixi.user.designpattern.chain.ext.EmailCheckApproveChain;
import com.mixi.user.designpattern.chain.ext.LastStepApproveChain;
import com.mixi.user.designpattern.strategy.LinkVerifyStrategy;
import com.mixi.user.domain.entity.User;
import com.mixi.user.mapper.UserMapper;
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

    private final RedisTemplate redisTemplate;
    private final UserMapper userMapper;

    @Override
    public String process(String email, String uid) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("email", email));
        if (Objects.isNull(user)){
            log.debug("邮箱未注册！");
            throw new RuntimeException(COMMON_ERROR);
        }
        String redisDataMap = (String) redisTemplate.opsForValue().get(REDIS_PRE + email + ":" + "uid");
        if (Objects.isNull(redisDataMap) || !Objects.equals(uid, redisDataMap)) {
            log.debug("链接出错！");
            throw new RuntimeException(COMMON_ERROR);
        }
        redisTemplate.delete(REDIS_PRE + email + ":" + "uid"); // 删除指定键的数据
        redisTemplate.delete(REDIS_PRE + email + ":" + "times");
        return TokenUtil.getToken(user.getId(),email);
    }
}