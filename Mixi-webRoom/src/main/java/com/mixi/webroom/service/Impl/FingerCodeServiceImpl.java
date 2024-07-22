package com.mixi.webroom.service.Impl;

import com.mixi.common.exception.ServeException;
import com.mixi.common.utils.RCode;
import com.mixi.webroom.service.FingerCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 描述: 指纹验证码实现类
 * @author suifeng
 * 日期: 2024/7/20
 */
@RequiredArgsConstructor
@Component
public class FingerCodeServiceImpl implements FingerCodeService {

    private String temp;

    @Override
    public String generateCode(String fingerprint) {
        if (fingerprint == null || fingerprint.isEmpty()) {
            throw new ServeException(RCode.MISSING_FINGERPRINTS);
        }
        temp = fingerprint;
        return "123456";
    }

    @Override
    public boolean validateCode(String fingerprint, String code) {
        if (fingerprint == null || fingerprint.isEmpty()) {
            throw new ServeException(RCode.MISSING_FINGERPRINTS);
        }
        if (code == null || code.isEmpty()) {
            throw new ServeException(RCode.MISSING_VERIFICATION_CODE);
        }
        return fingerprint.equals(temp) && code.equals("123456");
    }
}
