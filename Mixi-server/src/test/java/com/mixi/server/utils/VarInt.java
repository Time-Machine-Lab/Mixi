package com.mixi.server.utils;

import java.io.ByteArrayOutputStream;

/**
 * @Description
 * @Author welsir
 * @Date 2024/6/26 17:24
 */
public class VarInt {

    public static void main(String[] args) {
        int value = 300; // 示例输入数字
        int i = com.mixi.server.common.compress.VarInt.computeVarInt32Size(value);
        System.out.println(i);
        System.out.println();
    }
}
