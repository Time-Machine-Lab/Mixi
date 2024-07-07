package com.mixi.common.utils;

import com.mixi.common.pojo.User;

/**
 * 描述: 操作用户线程类
 * @author suifeng
 * 日期: 2024/7/5
 */
public class UserThread {

    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    /**
     * 获取当前操作用户
     */
    public static User get() {
        return userThreadLocal.get();
    }

    /**
     * 获取当前操作用户的Id
     */
    public static String getUserId() {
        if (get() != null) {
            return get().getId();
        }
        return null;
    }

    /**
     * 获取当前操作用户的用户名
     */
    public static String getUsername() {
        if (get() != null) {
            return get().getUsername();
        }
        return null;
    }

    /**
     * 设置当前操作用户(根据userInfo)
     */
    public static void setUser(String userInfo) {
        // 用空格作为分割符
        String[] split = userInfo.split(" ");
        // TODO 这里的User对象只有单纯的id和username，如果要加其他字段，可能要在读一次表获取其他字段
        setUser(User.builder().id(split[0]).username(split[1]).build());
    }

    /**
     * 设置当前操作用户(根据user)
     */
    public static void setUser(User user) {
        userThreadLocal.set(user);
    }

    /**
     * 移除当前操作用户
     */
    public static void removeUser() {
        userThreadLocal.remove();
    }
}
