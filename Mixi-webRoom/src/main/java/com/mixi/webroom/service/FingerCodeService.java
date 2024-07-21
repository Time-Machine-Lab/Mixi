package com.mixi.webroom.service;

/**
 * 描述: 指纹验证码接口
 * @author suifeng
 * 日期: 2024/7/20
 */
public interface FingerCodeService {

    /**
     * 根据指纹生成验证码并存储到Redis
     * @param fingerprint 指纹
     * @return 生成的验证码
     */
    String generateCode(String fingerprint);

    /**
     * 根据指纹从Redis中获取验证码并进行验证
     * @param fingerprint 指纹
     * @param code 验证码
     * @return 验证结果
     */
    boolean validateCode(String fingerprint, String code);
}