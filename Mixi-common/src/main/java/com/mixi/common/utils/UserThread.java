package com.mixi.common.utils;

import com.mixi.common.pojo.TokenUserInfo;

/**
 * 描述: 操作用户线程类
 * @author suifeng
 * 日期: 2024/7/5
 */
public class UserThread {

    private static final ThreadLocal<TokenUserInfo> userThreadLocal = new ThreadLocal<>();

    /**
     * 获取当前操作用户
     */
    public static TokenUserInfo get() {
        return userThreadLocal.get();
    }

    /**
     * 获取当前操作用户的Id
     */
    public static String getUserId() {
        TokenUserInfo user = get();
        return user != null ? user.getUserId() : null;
    }

    /**
     * 获取当前操作用户的用户名
     */
    public static String getUsername() {
        TokenUserInfo user = get();
        return user != null ? user.getUsername() : null;
    }

    /**
     * 获取当前操作用户的角色
     */
    public static int[] getRoles() {
        TokenUserInfo user = get();
        return user != null ? user.getRoles() : null;
    }

    /**
     * 设置当前操作用户
     */
    public static void setUser(TokenUserInfo user) {
        userThreadLocal.set(user);
    }

    /**
     * 移除当前操作用户
     */
    public static void removeUser() {
        userThreadLocal.remove();
    }
}
