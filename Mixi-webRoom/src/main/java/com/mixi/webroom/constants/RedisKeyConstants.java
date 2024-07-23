package com.mixi.webroom.constants;

/**
 * @Author：XiaoChun
 * @Date：2024/6/27 11:25
 */
public class RedisKeyConstants {
    public static String WEB_ROOM = "Mixi:WebRoom:";

    public static String OWNER = "Owner";

    public static String INFO = "Info";

    public static String NUMBER = "Number";

    public static String MAX = "Max";

    public static String PULL_FLAG = "PullFlag";

    public static String USER = "Mixi:User:";

    public static String TICKET = "Ticket";

    public static String OWN = "Own";

    public static String CONNECTED = "Connection";

    public static String user(String uid){
        return USER + uid;
    }

    public static String webRoom(String roomId){
        return WEB_ROOM + roomId;
    }
}
