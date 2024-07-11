package com.mixi.common.utils;

import lombok.Getter;

/**
 * 描述: 自定义状态码
 * @author suifeng
 * 日期: 2024/7/5
 */
@Getter
public enum RCode {

    // 成功响应
    SUCCESS(200, "成功"),

    /**
     * 失败返回码
     */
    ERROR(400, "服务器繁忙，请稍后重试"),

    /**
     * 参数异常
     */
    PARAMS_ERROR(20001, "参数异常或者格式错误"),

    /**
     * 用户权限
     */
    INSUFFICIENT_AUTHORITY(30001, "您的权限不足，无权访问此资源，请联系系统管理员"),
    INVALID_TOKEN(30002, "无效的Token"),
    TOKEN_VALIDATION_FAILURE(30003, "Token验证错误"),
    LACK_OF_TOKEN(30004, "缺少用于认证的Token"),
    FAILED_TO_CREATE_TOKEN(30005, "创建验证的Token失败，请联系系统管理员"),
    PERMISSION_EXPIRED(30006, "此权限已经到期，请重新申请授权"),
    PASSWORD_ERROR(30007, "密码错误，请再试一次"),
    USER_DOES_NOT_EXIST(30008, "此用户不存在，请重试或联系系统管理员"),
    SINGLE_SIGN_ON(30009, "该账号已在其他设备登录，请重新登陆或者检查账号是否异常");

    /**
     * 自定义状态码
     **/
    private final int code;

    /**
     * 自定义描述
     **/
    private final String message;

    RCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

