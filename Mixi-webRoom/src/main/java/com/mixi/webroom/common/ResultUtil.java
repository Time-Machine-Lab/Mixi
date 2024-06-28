package com.mixi.webroom.common;


import com.mixi.webroom.common.enums.ResultEnums;
import io.github.common.Status;
import io.github.common.web.Result;

/**
 * @Author：XiaoChun
 * @Date：2024/6/28 下午4:03
 */
public class ResultUtil<T>{
    public static <T> Result<T> success(T data) {
        return Result.success(data);
    }

    public static <T> Result<T> error(String code, String message) {
        return Result.error(code, message);
    }

    public static <T> Result<T> error(ResultEnums resultEnums) {
        return error(resultEnums.getCode(), resultEnums.getMessage());
    }
}
