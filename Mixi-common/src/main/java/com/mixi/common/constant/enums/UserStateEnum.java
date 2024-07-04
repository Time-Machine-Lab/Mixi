package com.mixi.common.constant.enums;

/**
 * @Author：XiaoChun
 * @Date：2024/7/4 上午10:53
 */
public enum UserStateEnum {
    NORMAL(1),
    READY(2),
    CONNECTED(3);
    private final Integer userState;

    UserStateEnum(Integer userState) {
        this.userState = userState;
    }

    public Integer getUserState() {
        return userState;
    }
}
