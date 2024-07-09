package com.mixi.webroom.config;

import javax.validation.constraints.NotBlank;

/**
 * @Author：XiaoChun
 * @Date：2024/6/27 11:25
 */
public class RedisKeyConfig {
    public static String WEB_ROOM = "Mixi:WebRoom:";

    public static String USER = "Mixi:User:";

    public static String userNettyState(@NotBlank String uid){
        return WEB_ROOM + uid + ":NettyState";
    }

    public static String userVideoState(@NotBlank String uid){
        return WEB_ROOM + uid + ":VideoState";
    }

    public static String roomLimit(@NotBlank String roomId){
        return WEB_ROOM + roomId + ":Limit";
    }

    public static String roomNumber(@NotBlank String roomId){
        return WEB_ROOM + roomId + ":Number";
    }

    public static String roomInfo(@NotBlank String roomId){
        return WEB_ROOM + roomId + ":Info";
    }

    public static String roomOwner(@NotBlank String roomId){
        return WEB_ROOM + roomId + ":Owner";
    }

    public static String roomLink(@NotBlank String roomId){
        return WEB_ROOM + roomId + ":RoomLink";
    }

    public static String userOwn(@NotBlank String uid){
        return USER + uid + ":Own";
    }

    public static String userConnected(@NotBlank String uid){
        return USER + uid + ":Connected";
    }
}
