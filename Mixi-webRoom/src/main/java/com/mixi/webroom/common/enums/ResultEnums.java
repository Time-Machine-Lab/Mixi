package com.mixi.webroom.common.enums;

import io.github.common.Status;

import java.io.Serializable;

/**
 * @Author：XiaoChun
 * @Date：2024/6/27 下午5:56
 */
public enum ResultEnums implements Serializable {
    SUCCESS("200", "操作成功"),
    SERVER_ERROR("500", "服务器错误"),
    USER_IN_ROOM("421", "用户已在房间中");

    private final String code;

    private final String message;

    ResultEnums(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }
}