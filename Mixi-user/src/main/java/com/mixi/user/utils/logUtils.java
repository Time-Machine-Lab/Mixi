package com.mixi.user.utils;

public class logUtils {
    /**
     * 获取净化后的消息，过滤掉换行，避免日志注入
     */
    public static String cleanMsg(String message) {
        if (message == null) {
            return "";
        }
        System.out.println(message);
        message = message.replace("\\n", "").replace("\\r", "").replace("\\t", "");
        System.out.println(message);
        return message;
    }
}
