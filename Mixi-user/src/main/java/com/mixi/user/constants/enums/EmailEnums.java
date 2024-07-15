package com.mixi.user.constants.enums;

import lombok.Getter;

/**
 * @Date 2023/12/14
 * @Author xiaochun
 */
@Getter
public enum EmailEnums {
    /**
     * 注册验证码
     */
    LOGIN(1, "登录", "login:"),

    REGISTER(2, "注册", "register:"),

    PASSWORD(3, "修改密码", "password:"),
    EMAIL(4, "修改邮箱", "email:");
    private final int code;  //验证码编号
    private final String msg;
    private final String codeHeader; //redis前置信息
    EmailEnums(int code, String msg, String codeHeader){
        this.code = code;
        this.msg = msg;
        this.codeHeader = codeHeader;
    }

    public static String getSecondValue(int code) {
        for (EmailEnums strategy : EmailEnums.values()) {
            if (strategy.code == code) {
                return strategy.codeHeader;
            }
        }
        throw new IllegalArgumentException("No matching strategy found for firstValue: " + code);
    }
}
