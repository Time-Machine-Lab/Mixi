package com.mixi.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mixi.common.exception.ServeException;
import com.mixi.user.designpattern.chain.ApproveChain;
import com.mixi.user.designpattern.chain.ApproveChainBuilder;
import com.mixi.user.designpattern.factory.LinkFactory;
import com.mixi.user.designpattern.strategy.LinkVerifyStrategyFactory;
import com.mixi.user.domain.entity.User;
import com.mixi.user.domain.vo.InfoVo;
import com.mixi.user.domain.vo.UserLoginVo;
import com.mixi.user.domain.vo.UserRegisterVo;
import com.mixi.user.service.UserService;
import com.mixi.user.mapper.UserMapper;
import com.mixi.user.utils.*;
import io.github.common.web.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
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
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private final UserDaoService userDaoService;
    private final UserMapper userMapper;
    private final RedisTemplate redisTemplate;
    private final ThreadService threadService;
    private final LinkFactory linkFactory;

    private final ApproveChainBuilder approveChainBuilder;
    private final String HOME_URL = "www.baidu.com";

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
        return Result.success(MapUtils.build()
                .set("token", LinkVerifyStrategyFactory.getInvokeStrategy(type).process(email, uid))
                .set("transTo", HOME_URL)
                .buildMap());
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
        approveChainBuilder.buildInstance()
                .set("CodeCheckApproveChain","000000")
                .Build()
                .approve();
        User user = User.baseBuild(email);
        return Result.success(userDaoService.insert(user));
    }

    @Override
    public Result<?> login(UserLoginVo userLoginVo) {
        return null;
    }

    @Override
    public Result userInfo() {
        return Result.success();
    }
}




