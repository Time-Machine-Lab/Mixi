package com.mixi.webroom.core.enums;

import lombok.Getter;

/**
 * @Author：XiaoChun
 * @Date：2024/6/27 下午5:56
 */
@Getter
public enum ResultEnums{
    SUCCESS("200", "操作成功"),
    SERVER_ERROR("500", "服务器错误"),
    USER_HAS_ROOM("420", "用户已有房间"),
    USER_CONNECTED("421", "用户已加入房间"),
    USER_STATE_ERROR("422", "用户状态错误"),
    ONLY_HOMEOWNER("423", "只有房主可以分享房间"),
    ROOM_FULLED("424", "房间已满"),
    QUIT_ROOM_ERROR("425", "退出房间错误"),
    PULL_HAS_NOT_COOLED_DOWN("426", "拉人还未冷却"),
    CALLBACK_EXECUTE_ERROR("427", "回调执行错误"),
    ROOM_NOT_EXIST("428", "房间不存在"),
    THE_USER_DID_NOT_CREATE_A_ROOM("429", "用户未创建房间"),
    TRANSCODE_ERROR("430", "转码错误");

    private final String code;

    private final String message;

    ResultEnums(String code, String message){
        this.code = code;
        this.message = message;
    }

}