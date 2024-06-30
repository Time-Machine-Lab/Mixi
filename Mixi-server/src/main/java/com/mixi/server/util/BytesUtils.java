package com.mixi.server.util;

import io.netty.buffer.ByteBuf;

/**
 * @Description
 * @Author welsir
 * @Date 2024/6/27 14:02
 */
public class BytesUtils {

    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    public static byte[] getFromBuf(ByteBuf in, int length) {
        if (length <= 0) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] bTemp = new byte[length];
        in.readBytes(bTemp);
        return bTemp;
    }
}
