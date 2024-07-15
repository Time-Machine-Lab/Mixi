package com.mixi.user.handler;


import com.mixi.common.constant.enums.AppHttpCodeEnum;

public class SystemException extends RuntimeException{

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

    public SystemException(String message) {
        super(message);
        this.code = "505";  // 这里填写你的默认错误代码，或者根据需要设置
        this.msg = message;
    }
}