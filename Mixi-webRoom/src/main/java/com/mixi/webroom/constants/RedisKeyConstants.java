package com.mixi.webroom.constants;

/**
 * @Author：XiaoChun
 * @Date：2024/6/27 11:25
 */
public class RedisKeyConstants {
    public static String WEB_ROOM = "Mixi:WebRoom:";

    public static String USER = "Mixi:User:";

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
