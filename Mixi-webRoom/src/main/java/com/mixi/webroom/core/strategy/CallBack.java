package com.mixi.webroom.core.strategy;

/**
 * @Author：XiaoChun
 * @Date：2024/7/18 下午4:00
 */
public interface CallBack {
    Boolean successCallBack(String roomId, String uid);

    Boolean failCallBack(String roomId, String uid);
}