package com.mixi.user.service.impl;

import com.alibaba.nacos.api.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mixi.common.exception.ServeException;
import com.mixi.rpc.client.CaptchaServiceClient;
import com.mixi.user.constants.RedisConstant;
import com.mixi.user.constants.enums.EmailEnums;
import com.mixi.user.designpattern.chain.ApproveChainBuilder;
import com.mixi.user.designpattern.factory.LinkFactory;
import com.mixi.user.designpattern.strategy.LinkVerifyStrategyFactory;
import com.mixi.user.domain.entity.User;
import com.mixi.user.domain.vo.*;
import com.mixi.user.handler.SystemException;
import com.mixi.user.service.UserService;
import com.mixi.user.mapper.UserMapper;
import com.mixi.user.utils.*;
import io.github.common.web.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.mixi.user.constants.CommonConstant.*;
import static com.mixi.user.constants.RedisConstant.*;

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
    private final CaptchaServiceClient captchaServiceClient;

    @Override
    public Result link(String email, String type) {
        String  uuid = UuidUtils.creatUuid(), link = linkFactory.getLink(email, uuid,type);
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        if (ops.setIfAbsent(captchaCodeKey.getEmailKey(email), String.valueOf(uuid))) {
            redisTemplate.expire(captchaCodeKey.getEmailKey(email), REDIS_KEY_TIMEOUT, TimeUnit.MINUTES);
            threadService.sendEmail(email, link);
            ops.increment(captchaCodeKey.getEmailTimeKey(email));
            redisTemplate.expire(captchaCodeKey.getEmailTimeKey(email), REDIS_KEY_TIMEOUT, TimeUnit.MINUTES);
            return Result.success();
        }
        if (ops.increment(captchaCodeKey.getEmailTimeKey(email)) <= REDIS_KEY_TIMEOUT) {
            threadService.sendEmail(email, link);
            Long ttl = redisTemplate.getExpire(captchaCodeKey.getEmailKey(email), TimeUnit.SECONDS);
            ops.set(captchaCodeKey.getEmailKey(email), String.valueOf(uuid));
            redisTemplate.expire(captchaCodeKey.getEmailKey(email), ttl, TimeUnit.SECONDS);
            return Result.success();
        }
        log.debug("Do not resend");
        throw new SystemException(DO_NOT_REPEAT_THE_OPERATION);
    }

    @Override
    public Result linkVerify(String email, String uid, String type) {
        return Result.success(LinkVerifyStrategyFactory.getInvokeStrategy(type).process(email, uid));
    }

    @Override
    public Result updateInfo(String uid, InfoVo infoVo) {
        infoVo.setId(uid);
        User user = new User();
        //密码为空必须设置密码
        if (Objects.isNull(userMapper.selectById(uid).getPassword())){
            if (Objects.isNull(infoVo.getPassword())){
                log.error("password must");
                throw new SystemException(PASSWORD_MUST);
            }
            user.setPassword(infoVo.getPassword());
        }
        user.InfoTo(infoVo);
        return Result.success(userMapper.updateById(user));
    }

    @Override
    public Result commonRegister(UserRegisterVo userRegisterVo) {
        String email = userRegisterVo.getEmail();
        approveChainBuilder.buildInstance()
                .set(CODECHECKAPPROVECHAIN,"register",userRegisterVo.getCode(),email)
                .Build()
                .approve();
        User user = User.baseBuild(email);
        return Result.success(userDaoService.insert(user));
    }

    @Override
    public Result<?> login(UsernameLoginVo userLoginVo) {
        try {
            String email = userLoginVo.getUsername();
            if (email.length() == 10){
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("username",email);
                User user = userMapper.selectOne(userQueryWrapper);
                email = user.getEmail();
            }
            String cacheCode = (String) redisTemplate.opsForValue().get(RedisConstant.loginCodeKey.getKey(email));
            if(!StringUtils.equals(cacheCode,userLoginVo.getCode())){
                log.error("code err");
                throw new SystemException(CODE_ERR);
            }
//            String encipherPwd = DigestUtils.md5Hex(pwd);
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", userLoginVo.getUsername())
                    .eq("password", userLoginVo.getPassword());
            User userEntity = userMapper.selectOne(queryWrapper);

            if(Objects.isNull(userEntity)){
                log.error("{} username or password error",userLoginVo.getUsername());
                throw new ServeException(USERNAME_PASSWORD_ERROR);
            }
            redisTemplate.delete(RedisConstant.loginCodeKey.getKey(email));
            String token = TokenUtil.getToken(userEntity.getId(), userEntity.getUsername());
            return Result.success(token);
        } catch (Exception e) {
            log.error("login verify system error:{}",e.getMessage());
            throw new ServeException(SYSTEM_ERROR);
        }
    }

    @Override
    public Result userInfo(String uid) {
        return Result.success(userMapper.selectById(uid));
    }

    @Override
    public Map<String, String> preCode() {
        //调用rpc获取前置验证码
        com.mixi.rpc.domain.Result<Map<String, String>> result = captchaServiceClient.image();
        Map<String, String> map = result.getData();
        String base64 = map.get("base64");
        String code = map.get("code");
        String uuid = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(RedisConstant.captchaCodeKey.getImageKey(uuid), code, 2, TimeUnit.MINUTES);
        return Map.of("base64", base64, "uuid", uuid);
    }

    @Override
    public void sendCode(String email, String code, String uuid, int type) {
        preVerify(uuid,code);
        com.mixi.rpc.domain.Result<Map<String, String>> result = captchaServiceClient.email(email);
        redisTemplate.opsForValue().set(REDIS_KEY_CAPTCHA + EmailEnums.getSecondValue(type) + email, result.getData().get("code"), 5, TimeUnit.MINUTES);
    }

    @Override
    public Result passwordUpdate(PasswordUpdateVo passwordUpdateVo) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email",passwordUpdateVo.getEmail());
        User user = userMapper.selectOne(userQueryWrapper);
        if (!passwordUpdateVo.getOldPassword().equals(user.getPassword())){
            log.error("old password err");
            throw new SystemException("old password err");
        }
        user.setPassword(passwordUpdateVo.getNewPassword());
        return Result.success(userMapper.updateById(user));
    }

    @Override
    public Result emailUpdate(EmailUpdateVo emailUpdateVo) {
        switch (emailUpdateVo.getType()){
            case "1":dealWithOldPassword(emailUpdateVo);break;
            case "2":dealWithNewPassword(emailUpdateVo);break;
            default:  throw new SystemException(BAD_CMD);
        }
        return Result.success();
    }

    public void dealWithOldPassword(EmailUpdateVo emailUpdateVo){
        String email = emailUpdateVo.getEmail();
        //校验验证码 -> 从redis获取 前缀为 第一次
        String code = (String) redisTemplate.opsForValue().get(RedisConstant.REDIS_KEY_CAPTCHA + "email:" + emailUpdateVo.getEmail());
        if (!emailUpdateVo.getCode().equals(code)){
            log.error("code err");
            throw new SystemException(CODE_ERR);
        }
        //标记邮箱通过第一轮
        redisTemplate.delete(RedisConstant.REDIS_KEY_CAPTCHA + "register:" + emailUpdateVo.getEmail());
        redisTemplate.opsForValue().set("mixi:captcha:dealWithOldPassword:" + emailUpdateVo.getEmail() ,"1");

    }

    public void dealWithNewPassword(EmailUpdateVo emailUpdateVo){
        String newEmail = emailUpdateVo.getNewEmail();
        //看第一轮通过没有
        String val = (String) redisTemplate.opsForValue().get("mixi:captcha:dealWithOldPassword:" + emailUpdateVo.getEmail());
        if (!"1".equals(val)){
            log.error("first err");
            throw new SystemException("first err");
        }
        //校验验证码  -> 从redis获取 前缀为 第二次
        String code = (String) redisTemplate.opsForValue().get(RedisConstant.REDIS_KEY_CAPTCHA + "email:"+ newEmail);
        if (!code.equals(emailUpdateVo.getCode())){
            log.error("code err");
            throw new SystemException(CODE_ERR);
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email",emailUpdateVo.getEmail());
        User user = userMapper.selectOne(userQueryWrapper);
        //修改邮箱
        user.setEmail(newEmail);
        userMapper.updateById(user);
    }

    public boolean preVerify(String uuid, String code){
        String c = (String) redisTemplate.opsForValue().get(RedisConstant.captchaCodeKey.getImageKey(uuid));
        if(c != null && c.equalsIgnoreCase(code)){
            redisTemplate.delete(RedisConstant.captchaCodeKey.getImageKey(uuid));
            return true;
        }
        return false;
    }
}




