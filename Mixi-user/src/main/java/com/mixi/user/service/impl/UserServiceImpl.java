package com.mixi.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mixi.user.designpattern.chain.ext.CodeCheckApproveChain;
import com.mixi.user.designpattern.chain.ext.EmailCheckApproveChain;
import com.mixi.user.designpattern.chain.ext.LastStepApproveChain;
import com.mixi.user.designpattern.factory.LinkFactory;
import com.mixi.user.designpattern.strategy.LinkVerifyProcess;
import com.mixi.user.designpattern.strategy.LinkVerifyStrategy;
import com.mixi.user.designpattern.strategy.imp.LoginLinkVerifyStrategy;
import com.mixi.user.designpattern.strategy.imp.RegisterLinkVerifyStrategy;
import com.mixi.user.domain.entity.User;
import com.mixi.user.domain.vo.InfoVo;
import com.mixi.user.domain.vo.UserLoginVo;
import com.mixi.user.domain.vo.UserRegisterVo;
import com.mixi.user.service.UserService;
import com.mixi.user.mapper.UserMapper;
import com.mixi.user.utils.*;
import io.github.common.web.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.mixi.user.constants.CommonConstant.COMMON_ERROR;
import static com.mixi.user.constants.RedisConstant.REDIS_KEY_TIMEOUT;
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

    private final UserMapper userMapper;
    private final RedisTemplate redisTemplate; // 使用<String, Object>指定RedisTemplate的泛型类型
    private final ThreadService threadService;
    private final LinkFactory linkFactory;

    private final LastStepApproveChain lastStepApproveChain;
    private final CodeCheckApproveChain codeCheckApproveChain;
    private final EmailCheckApproveChain emailCheckApproveChain;

    private final LinkVerifyProcess linkVerifyProcess;

    @Override
    public Result link(String email, String type) {
        String  uuid = UuidUtils.creatUuid(), link = linkFactory.getLink(email, uuid,type);
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        if (ops.setIfAbsent(REDIS_PRE + email + ":uid", String.valueOf(uuid))) {
            redisTemplate.expire(REDIS_PRE + email + ":uid", REDIS_KEY_TIMEOUT, TimeUnit.MINUTES);
            threadService.sendEmail(email, link);
            ops.increment(REDIS_PRE + email + ":times");
            redisTemplate.expire(REDIS_PRE + email + ":times", REDIS_KEY_TIMEOUT, TimeUnit.MINUTES);
            return Result.success();
        }
        if (ops.increment(REDIS_PRE + email + ":" + "times") <= REDIS_KEY_TIMEOUT) {
            threadService.sendEmail(email, link);
            Long ttl = redisTemplate.getExpire(REDIS_PRE + email + ":uid", TimeUnit.SECONDS);
            ops.set(REDIS_PRE + email + ":" + "uid", String.valueOf(uuid));
            redisTemplate.expire(REDIS_PRE + email + ":uid", ttl, TimeUnit.SECONDS);
            return Result.success();
        }
        log.debug("重复发送链接");
        throw new RuntimeException(COMMON_ERROR);
    }

    @Override
    public Result linkVerify(String email, String uid, String type) {
        String token = linkVerifyProcess.process(type, email, uid);
        Map<String, String> stringStringMap = MapUtils.build()
                .set("token",token)
                .set("transTo", "www.baidu.com")
                .buildMap();
        return Result.success(stringStringMap);
    }

    @Override
    public Result updateInfo(String uid, InfoVo infoVo) {
        infoVo.setId(uid);
        //2、满足条件修改其一
        if (!Objects.isNull(infoVo.getPassword())||!Objects.isNull(infoVo.getEmail())){
            return Result.success("接口未开放");
        }
        User user = User.InfoTo(infoVo);
        return Result.success(userMapper.updateById(user));
    }


    @Override
    public Result commonRegister(UserRegisterVo userRegisterVo) {
        String email = userRegisterVo.getEmail(),code = userRegisterVo.getCode();
        emailCheckApproveChain.setNext(email, codeCheckApproveChain);
        codeCheckApproveChain.setNext(code,lastStepApproveChain);
        emailCheckApproveChain.approve();
        User user = User.baseBuild(email);
        userMapper.insert(user);
        return Result.success(TokenUtil.getToken(user.getId(),email));
    }

    @Override
    public Result<?> login(UserLoginVo userLoginVo) {
        return null;
    }
}




