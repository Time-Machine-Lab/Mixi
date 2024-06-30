package com.mixi.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mixi.user.constants.CommonConstant;
import com.mixi.user.designpattern.chain.ext.EmailCheckApproveChain;
import com.mixi.user.designpattern.chain.ext.LastStepApproveChain;
import com.mixi.user.designpattern.chain.ext.RedisKeyCheckApproveChain;
import com.mixi.user.domain.entity.User;
import com.mixi.user.domain.vo.UserLoginVo;
import com.mixi.user.service.UserService;
import com.mixi.user.mapper.UserMapper;
import com.mixi.user.utils.*;
import io.github.util.token.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.mixi.user.constants.CommonConstant.DEV_ENV;

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
    private final TokenUtil tokenUtils;
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
        String link = "http://"+ DEV_ENV + "/api/user/verify?" + "&email=" + userLoginVo.getEmail() + "&uid=" + uuid;
        // 设置链接的有效期为5分钟 存入redis
        Map<String, String> redisDataMap = MapUtils.build()
                .set("uuid",String.valueOf(uuid))
                .buildMap();
        redisTemplate.opsForValue().set(userLoginVo.getEmail(),redisDataMap);
        redisTemplate.expire(userLoginVo.getEmail(), 5, TimeUnit.MINUTES); // 设置过期时间为5分钟
//        发送链接
        threadService.sendEmail(userLoginVo.getEmail(), link);
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
        String token = tokenUtils.createToken("email",email,"123456").getAccessToken();
        redisTemplate.opsForValue().set(token,email);
        redisTemplate.expire(token, 5, TimeUnit.MINUTES); // 设置过期时间为5分钟
        return MapUtils.build()
                .set("token",token)
                .set("transTo","www.baidu.com")
                .buildMap();
    }

}




