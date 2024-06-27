package com.mixi.webroom.common.cache;

/**
 * @Author：XiaoChun
 * @Date：2024/6/27 11:02
 */
public class RedisBuilder {
    private StringBuffer key;

    private String value;

    public RedisBuilder() {
    }

    private RedisBuilder key(String key) {
        this.key.append('.').append(key);
        return this;
    }

    private RedisBuilder value(){
        this.value = this.key.toString();
        return this;
    }
}
