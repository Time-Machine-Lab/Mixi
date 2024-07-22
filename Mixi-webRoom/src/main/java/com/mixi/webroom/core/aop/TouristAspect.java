package com.mixi.webroom.core.aop;

import cn.hutool.core.util.StrUtil;
import com.mixi.common.component.token.TokenService;
import com.mixi.common.exception.ServeException;
import com.mixi.common.pojo.TokenUserInfo;
import com.mixi.common.utils.R;
import com.mixi.common.utils.RCode;
import com.mixi.common.utils.ThreadContext;
import com.mixi.common.utils.UserThread;
import com.mixi.rpc.client.UserClient;
import com.mixi.webroom.service.FingerCodeService;
import io.github.common.web.Result;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.mixi.common.constant.constpool.TransferConstant.IP_ADDRESS;
import static com.mixi.common.constant.constpool.TransferConstant.MACHINE_CODE;

/**
 * 描述: 游客模式切面类，用于处理游客判断逻辑
 *
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

        String ipAddress = ThreadContext.getData(IP_ADDRESS).toString();
        String machineCode = ThreadContext.getData(MACHINE_CODE).toString();

        // 游客判断逻辑
        if (StrUtil.isNotEmpty(UserThread.getUserId())) {
            return joinPoint.proceed();
        }

        // 没有验证码，根据指纹生成验证码
        if (StrUtil.isEmpty(code)) {

            String newCode = fingerCodeService.generateCode(fingerprint);

            // 返回验证码给前端
            return R.success(Map.of("code", newCode));
        }

        // 有验证码，就进行验证
        else {
            if (!fingerCodeService.validateCode(fingerprint, code)) {
                throw new ServeException(RCode.VERIFICATION_FAILURE);
            }

            // 验证通过，调用用户模块的生成随机游客接口得到token
            String token = userClient.generateVisitorUser(fingerprint).getMessage();

            // 解析出用户信息
            TokenUserInfo tokenUserInfo = tokenService.validateAndExtractUserInfo(token);

            if (null == tokenUserInfo) {
                throw new ServeException(RCode.FAILED_TO_CREATE_USER);
            }

            // 加入用户线程
            tokenUserInfo.addField("token", token);
            UserThread.setUser(tokenUserInfo);
        }

        // 走正常用户流程
        return joinPoint.proceed();
    }
}