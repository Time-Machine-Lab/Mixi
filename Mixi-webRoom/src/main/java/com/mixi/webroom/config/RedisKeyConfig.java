package com.mixi.webroom.config;

import javax.validation.constraints.NotBlank;

/**
 * @Author：XiaoChun
 * @Date：2024/6/27 11:25
 */
public class RedisKeyConfig {
    public static String WEB_ROOM = "Mixi:WebRoom:";

    public static String USER = "Mixi:User:";

//    public static String userNettyState( String uid){
//        return WEB_ROOM + uid + ":NettyState";
//    }
//
//    public static String userVideoState( String uid){
//        return WEB_ROOM + uid + ":VideoState";
//    }

//    public static String roomLimit( String roomId){
//        return WEB_ROOM + roomId + ":Limit";
//    }

    public static String roomPullFlag( String roomId){
        return WEB_ROOM + roomId + ":PullFlag";
    }
    
    public static String roomNumber( String roomId){
        return WEB_ROOM + roomId + ":Number";
    }

    public static String roomInfo( String roomId){
        return WEB_ROOM + roomId + ":Info";
    }

    public static String roomOwner( String roomId){
        return WEB_ROOM + roomId + ":Owner";
    }

//    public static String roomLink( String roomId){
//        return WEB_ROOM + roomId + ":RoomLink";
//    }

    public static String userTicket( String uid){
        return USER + uid;
    }

    public static String userOwn( String uid){
        return USER + uid + ":Own";
    }

    public static String userConnected( String uid){
        return USER + uid + ":Connected";
    }
}
