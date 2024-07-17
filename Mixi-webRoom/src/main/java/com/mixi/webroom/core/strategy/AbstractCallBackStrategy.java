package com.mixi.webroom.core.strategy;

/**
 * @Author：XiaoChun
 * @Date：2024/7/17 下午4:25
 */
public abstract class AbstractCallBackStrategy {
    public abstract Boolean successCallBack();

    public abstract Boolean failCallBack();
}