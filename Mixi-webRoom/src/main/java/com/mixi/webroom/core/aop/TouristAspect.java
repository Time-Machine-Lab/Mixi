package com.mixi.webroom.core.aop;

import cn.hutool.core.util.StrUtil;
import com.mixi.common.component.token.TokenService;
import com.mixi.common.pojo.TokenUserInfo;
import com.mixi.common.utils.UserThread;
import com.mixi.rpc.client.UserClient;
import com.mixi.webroom.core.exception.ServerException;
import com.mixi.webroom.service.FingerCodeService;
import io.github.common.web.Result;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 描述: 游客模式切面类，用于处理游客判断逻辑
 * @author suifeng
 * 日期: 2024/7/20
 */
@RequiredArgsConstructor
@Component
@Aspect
public class TouristAspect {

    private final FingerCodeService fingerCodeService;
    private final TokenService tokenService;
    private final UserClient userClient;

    @Around("@annotation(com.mixi.webroom.core.annotation.TouristCheck) && args(code, fingerprint, ..)")
    public Object checkTourist(ProceedingJoinPoint joinPoint, String code, String fingerprint) throws Throwable {

        // 游客判断逻辑
        if (StrUtil.isNotEmpty(UserThread.getUserId())) {
            return joinPoint.proceed();
        }

        // 如果有验证码，走验证注册逻辑
        if (StrUtil.isNotEmpty(code)) {
            if (!fingerCodeService.validateCode(fingerprint, code)) {
                throw new ServerException("1001", "验证码错误，请重试");
            }

            // 验证通过，调用用户模块的生成随机游客接口得到token
            String token = userClient.GenerateVisitorUser(fingerprint);
            TokenUserInfo tokenUserInfo = tokenService.validateAndExtractUserInfo(token);
            if (null == tokenUserInfo) {
                throw new ServerException("1002", "游客用户生成失败");
            }

            // 加入用户线程
            UserThread.setUser(tokenUserInfo);
        } else {
            // 没有验证码，根据指纹生成验证码
            String newCode = fingerCodeService.generateCode(fingerprint);

            // 返回验证码给前端
            return Result.success(newCode);
        }

        return joinPoint.proceed();
    }
}