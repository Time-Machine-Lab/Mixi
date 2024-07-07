package com.mixi.server.util;

import com.mixi.server.netty.protocol.AccessMessage;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/7 20:33
 */
public class AccessMessageUtils {

    public static AccessMessage createHeartResponse() {
        AccessMessage response = new AccessMessage();
        response.setHeartBeat(true);
        return response;
    }

}
