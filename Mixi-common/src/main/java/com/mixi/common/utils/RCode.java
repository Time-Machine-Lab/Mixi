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

    USER_HAS_ROOM(420, "用户已有房间"),
    USER_CONNECTED(421, "用户已加入房间"),
    USER_STATE_ERROR(422, "用户状态错误"),
    ONLY_HOMEOWNER(423, "只有房主可以分享房间"),
    ROOM_FULLED(424, "房间已满"),
    QUIT_ROOM_ERROR(425, "退出房间错误"),
    PULL_HAS_NOT_COOLED_DOWN(426, "拉人还未冷却"),
    CALLBACK_EXECUTE_ERROR(427, "回调执行错误"),
    ROOM_NOT_EXIST(428, "房间不存在"),
    THE_USER_DID_NOT_CREATE_A_ROOM(429, "用户未创建房间"),
    TRANSCODE_ERROR(430, "转码错误"),

    /**
     * 参数异常
     */
    PARAMS_ERROR(20001, "参数异常或者格式错误"),

    /**
     * 用户权限
     */
    VERIFICATION_FAILURE(30001, "验证失败"),
    FAILED_TO_CREATE_USER(30002, "创建用户失败"),
    MISSING_VERIFICATION_CODE(30003, "缺少验证码"),
    USER_DOES_NOT_EXIST(30004, "用户不存在"),

    /**
     * 游客
     */
    VISITOR_REDIRECTION(90001, "游客用户请先进行游客注册"),
    ILLEGAL_FINGERPRINT(90002, "指纹参数不合法，请再试一次");

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

