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

import java.util.HashMap;
import java.util.Map;
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
    implements UserService{

    private final RedisTemplate redisTemplate;
    private final UserMapper userMapper;
    private final ThreadService threadService;

    private final RedisKeyCheckApproveChain redisKeyCheckApproveChain;
    private final EmailCheckApproveChain emailCheckApproveChain;
    private final LastStepApproveChain lastStepApproveChain;


    @Override
    public Object linkLogin(UserLoginVo userLoginVo) {
        String email = userLoginVo.getEmail();
        emailCheckApproveChain.setNext(email,lastStepApproveChain);
        emailCheckApproveChain.approve();
        String uuid = UuidUtils.getUuid();
        String link =  new LinkFactory().getLink(email,uuid);
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        if(ops.setIfAbsent(REDIS_PRE + userLoginVo.getEmail() + ":" + "uid", String.valueOf(uuid))){
            redisTemplate.expire(REDIS_PRE + userLoginVo.getEmail() + ":" + "uid", 5, TimeUnit.MINUTES);
            threadService.sendEmail(userLoginVo.getEmail(), link);
            return ops.increment(REDIS_PRE + userLoginVo.getEmail() + ":" + "times");
        }
        if(ops.increment(REDIS_PRE + userLoginVo.getEmail() + ":" + "times") <= 5){
            threadService.sendEmail(userLoginVo.getEmail(), link);
            ops.set(REDIS_PRE + userLoginVo.getEmail() + ":" + "uid", String.valueOf(uuid));
            return "";
        }
        return "无效链接";
    }

    @Override
    public Object linkVerify(String email,String uid) {
        Map<String, String> redisDataMap = (Map<String, String>) redisTemplate.opsForValue().get(email);
        if (Objects.isNull(redisDataMap) || !Objects.equals(uid, redisDataMap.get("uuid"))){
            return "链接出错！";
        }
        redisTemplate.delete(email); // 删除指定键的数据
        return MapUtils.build()
                .set("token",TokenUtil.getToken(email))
                .set("transTo","www.baidu.com")
                .buildMap();
    }
}




