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
    USER_IN_ROOM("421", "用户已在房间中"),
    USER_STATE_ERROR("422", "用户状态错误"),
    ONLY_HOMEOWNER("423", "只有房主可以分享房间");

    private final String code;

    private final String message;

    ResultEnums(String code, String message){
        this.code = code;
        this.message = message;
    }

}