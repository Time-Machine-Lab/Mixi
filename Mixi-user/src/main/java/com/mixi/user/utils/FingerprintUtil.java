package com.mixi.user.utils;

/**
 * 描述: 指纹工具类
 * @author suifeng
 * 日期: 2024/7/23
 */
public class FingerprintUtil {

    /**
     * 验证指纹的合法性
     * 目前仅检查指纹是否包含字符串 "MIXI"
     * 将来可以扩展为非对称加密验证
     */
    public static boolean isValidFingerprint(String fingerprint) {
        return fingerprint != null && fingerprint.contains("MIXI");
    }
}