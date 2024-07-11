package com.mixi.common.constant.constpool;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/10 21:22
 */
public class RedisCacheKey {

    private static final String roomPreKey = "Mixi.webRoom";
    private static final String roomOwner = "owner";
    public static String isDestroyRoom(String roomId){return roomPreKey+roomId+roomOwner;}


}
