package com.mixi.server.netty.protocol;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/9 21:00
 */
public enum HeaderEnum {

    COMPRESS(1, "Body被压缩"),
    CHATROOM(2, "CHATROOM消息"),
    AUTH(3,"需要鉴权");
    private int code;

    private String desc;

    HeaderEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getType() {
        return code;
    }

}
