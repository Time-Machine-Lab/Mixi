package com.mixi.webroom.config;

/**
 * @Author：XiaoChun
 * @Date：2024/6/27 11:25
 */
public class RedisKeyConfig {
    public static String WEB_ROOM = "Mixi:WebRoom:";

    public static String USER_STATE = "Mixi:User:State:";

    public static String userNettyState(String uid){
        return WEB_ROOM + uid + ":NettyState";
    }

    public static String userVideoState(String uid){
        return WEB_ROOM + uid + ":VideoState";
    }
}
