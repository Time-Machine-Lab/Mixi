package com.mixi.server.util;

import com.mixi.server.netty.protocol.AccessMessage;
import com.mixi.server.netty.protocol.Header;
import com.mixi.server.netty.protocol.HeaderEnum;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

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

    public static String extractHeaderData(AccessMessage message, HeaderEnum headerEnum) {
        int headerType = headerEnum.getType();
        List<Header> headers = message.getHeaders();
        if (CollectionUtils.isEmpty(headers)) {
            return null;
        }
        return headers.stream().filter(header -> header.getType() == headerType).findFirst().map(header -> new String(header.getData(), StandardCharsets.UTF_8)).orElse(null);
    }

}
