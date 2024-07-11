package com.mixi.user.constants;

/**
 * @NAME: RedisConstant
 * @USER: yuech
 * @Description:
 * @DATE: 2024/7/1
 */
public class RedisConstant {

    //redis前缀key
    public final static String REDIS_PRE = "mixi:user:link:";

    //redis key超时时间（min）
    public final static int REDIS_KEY_TIMEOUT = 5;
}