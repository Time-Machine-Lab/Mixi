package com.mixi.webroom.common.exception;

/**
 * @Author：XiaoChun
 * @Date：2024/7/4 下午5:32
 */

import com.mixi.webroom.common.enums.ResultEnums;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @Date 2023/5/6
 * @Author xiaochun
 */
@EqualsAndHashCode(callSuper = true)
public class ServerException extends RuntimeException{
    private String message;

    @Getter
    private String code;

    public ServerException(ResultEnums resultEnums){
        this.message = resultEnums.getMessage();

        this.code = resultEnums.getCode();
    }

    public ServerException(String code, String message){
        this.message = message;

        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
