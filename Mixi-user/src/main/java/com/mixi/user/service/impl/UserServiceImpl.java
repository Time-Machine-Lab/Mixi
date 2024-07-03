package com.mixi.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mixi.user.designpattern.chain.ext.EmailCheckApproveChain;
import com.mixi.user.designpattern.chain.ext.LastStepApproveChain;
import com.mixi.user.designpattern.chain.ext.RedisKeyCheckApproveChain;
import com.mixi.user.designpattern.factory.LinkFactory;
import com.mixi.user.domain.entity.User;
import com.mixi.user.domain.vo.UserLoginVo;
import com.mixi.user.service.UserService;
import com.mixi.user.mapper.UserMapper;
import com.mixi.user.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.mixi.user.constants.RedisConstant.REDIS_PRE;

/**
 * @author yuech
 * @description 针对表【mixi_user】的数据库操作Service实现
 * @createDate 2024-06-25 16:18:03
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private final RedisTemplate redisTemplate;
    private final UserMapper userMapper;
    private final ThreadService threadService;

    private final RedisKeyCheckApproveChain redisKeyCheckApproveChain;
    private final EmailCheckApproveChain emailCheckApproveChain;
    private final LastStepApproveChain lastStepApproveChain;


    @Override
    public Object linkLogin(UserLoginVo userLoginVo) {
        String email = userLoginVo.getEmail(), uuid = UuidUtils.getUuid(), link = new LinkFactory().getLink(email, uuid), uKey = REDIS_PRE + userLoginVo.getEmail() + ":" + "uid", tKey = REDIS_PRE + userLoginVo.getEmail() + ":" + "times";
        emailCheckApproveChain.setNext(email, lastStepApproveChain);
        emailCheckApproveChain.approve();
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        if (ops.setIfAbsent(uKey, String.valueOf(uuid))) {
            redisTemplate.expire(uKey, 5, TimeUnit.MINUTES);
            threadService.sendEmail(userLoginVo.getEmail(), link);
            ops.increment(tKey);
            redisTemplate.expire(tKey, 5, TimeUnit.MINUTES);
            return "ok";
        }
        if (ops.increment(REDIS_PRE + userLoginVo.getEmail() + ":" + "times") <= 5) {
            threadService.sendEmail(userLoginVo.getEmail(), link);
            Long ttl = redisTemplate.getExpire(uKey, TimeUnit.SECONDS);
            ops.set(REDIS_PRE + userLoginVo.getEmail() + ":" + "uid", String.valueOf(uuid));
            redisTemplate.expire(uKey, ttl, TimeUnit.SECONDS);
            return "ok";
        }
        return "无效链接";
    }

    @Override
    public Object linkVerify(String email, String uid) {
        String redisDataMap = (String) redisTemplate.opsForValue().get(REDIS_PRE + email + ":" + "uid");
        if (Objects.isNull(redisDataMap) || !Objects.equals(uid, redisDataMap)) {
            return "链接出错！";
        }
        redisTemplate.delete(REDIS_PRE + email + ":" + "uid"); // 删除指定键的数据
        redisTemplate.delete(REDIS_PRE + email + ":" + "times");
        return MapUtils.build()
                .set("token", TokenUtil.getToken(email))
                .set("transTo", "www.baidu.com")
                .buildMap();
    }
}




