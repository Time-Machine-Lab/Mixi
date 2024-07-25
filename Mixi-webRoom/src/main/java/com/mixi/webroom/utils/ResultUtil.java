package com.mixi.webroom.utils;


import com.mixi.webroom.pojo.enums.ResultEnums;
import io.github.common.web.Result;

/**
 * @Author：XiaoChun
 * @Date：2024/6/28 下午4:03
 */
public class ResultUtil<T>{
    public static <T> Result<T> success(T data) {
        return Result.success(data);
    }

    public static <T> Result<T> error(String code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(message);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(String code, String message) {
        return Result.error(code, message);
    }

    public static <T> Result<T> error(ResultEnums resultEnums) {
        return error(resultEnums.getCode(), resultEnums.getMessage());
    }

    public static <T> Result<T> error(ResultEnums resultEnums, T data) {
        return error(resultEnums.getCode(), resultEnums.getMessage(), data);
    }
}
