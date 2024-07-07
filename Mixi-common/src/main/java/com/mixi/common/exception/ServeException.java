package com.mixi.common.exception;

import com.mixi.common.utils.RCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述: 全局业务异常类
 * @author suifeng
 * 日期: 2024/7/5
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ServeException extends RuntimeException {

    /**
     * 状态码
     */
    private int code = RCode.ERROR.getCode();

    /**
     * 异常消息
     */
    private String message = RCode.ERROR.getMessage();


    public ServeException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServeException(String message) {
        this.message = message;
    }

    public ServeException(int code) {
        this.code = code;
    }

    public ServeException(RCode rCode) {
        this.code = rCode.getCode();
        this.message = rCode.getMessage();
    }

    public ServeException(RCode rCode, String addMessage) {
        this.code = rCode.getCode();
        this.message = rCode.getMessage() + addMessage;
    }
}
