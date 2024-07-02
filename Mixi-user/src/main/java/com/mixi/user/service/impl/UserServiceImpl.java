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
        emailCheckApproveChain.setNext(email,redisKeyCheckApproveChain);
        redisKeyCheckApproveChain.setNext(email,lastStepApproveChain);
        emailCheckApproveChain.approve();
        String uuid = UuidUtils.getUuid();
        String link =  new LinkFactory().getLink(email,uuid);
        // 设置链接的有效期为5分钟 存入redis
        Map o = (Map<String, String> ) redisTemplate.opsForValue().get(REDIS_PRE + userLoginVo.getEmail());
        //第一次
        if (Objects.isNull(o)){
            Map<String, String> redisDataMap = MapUtils.build()
                    .set("uuid",String.valueOf(uuid))
                    .set("times","1")
                    .buildMap();
            redisTemplate.opsForValue().set(REDIS_PRE + userLoginVo.getEmail(),redisDataMap);
            redisTemplate.expire(userLoginVo.getEmail(), 5, TimeUnit.MINUTES); // 设置过期时间为5分钟
//        发送链接
            threadService.sendEmail(userLoginVo.getEmail(), link);
        }else{
            Integer times =  Integer.valueOf((String) o.get("times"));
            if(times >=5){
                throw new RuntimeException("请不要重复发送");
            }
            Map<String, String> redisDataMap = MapUtils.build()
                    .set("uuid",String.valueOf(uuid))
                    .set("times",times + 1 + "")
                    .buildMap();
            redisTemplate.opsForValue().set(REDIS_PRE + userLoginVo.getEmail(),redisDataMap);
//        发送链接
            threadService.sendEmail(userLoginVo.getEmail(), link);
        }
        return null;
    }

    @Override
    public Object linkVerify(String email,String uid) {
        //对时间和id做校验
        Map<String, String> redisDataMap = (Map<String, String>) redisTemplate.opsForValue().get(email);
        if (Objects.isNull(redisDataMap) || !Objects.equals(uid, redisDataMap.get("uuid"))){
            return "链接出错！";
        }
        redisTemplate.delete(email); // 删除指定键的数据
        String token = TokenUtil.getToken(email);
        redisTemplate.opsForValue().set(REDIS_PRE + token,email);
        redisTemplate.expire(token, 5, TimeUnit.MINUTES); // 设置过期时间为5分钟
        return MapUtils.build()
                .set("token",token)
                .set("transTo","www.baidu.com")
                .buildMap();
    }
}




