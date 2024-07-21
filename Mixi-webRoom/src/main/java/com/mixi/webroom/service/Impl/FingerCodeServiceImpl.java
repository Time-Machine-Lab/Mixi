package com.mixi.webroom.service.Impl;

import com.mixi.webroom.service.FingerCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 指纹验证码实现类
 * @author suifeng
 * 日期: 2024/7/20
 */
@RequiredArgsConstructor
@Component
public class FingerCodeServiceImpl implements FingerCodeService {

    private final StringRedisTemplate redisTemplate;

    private static final int CODE_LENGTH = 6; // 验证码长度
    private static final long EXPIRE_TIME = 300; // 验证码过期时间，单位秒

    @Override
    public String generateCode(String fingerprint) {
        // 生成随机验证码
        String code = generateRandomCode();
        // 将验证码存储到Redis，键为指纹，值为验证码，设置过期时间
        redisTemplate.opsForValue().set(fingerprint, code, EXPIRE_TIME, TimeUnit.SECONDS);
        return code;
    }

    @Override
    public boolean validateCode(String fingerprint, String code) {
        // 从Redis中获取验证码
        // TODO 要删除验证码
        String storedCode = redisTemplate.opsForValue().get(fingerprint);
        // 比较获取到的验证码和传入的验证码
        return code != null && code.equals(storedCode);
    }

    private String generateRandomCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(FingerCodeServiceImpl.CODE_LENGTH);
        for (int i = 0; i < FingerCodeServiceImpl.CODE_LENGTH; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
