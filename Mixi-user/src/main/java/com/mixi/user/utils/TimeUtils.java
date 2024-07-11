package com.mixi.user.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @NAME: TimeUtils
 * @USER: yuech
 * @Description:
 * @DATE: 2024/6/27
 */
public class TimeUtils {
    public static String getTime(int times,String type){
        String expirationStr = "";
        switch (type){
            case "MINUTE": Instant now = Instant.now();
                Instant expiration = now.plus(Duration.ofMinutes(times)); // 过期时间为当前时间加上5分钟
                expirationStr = expiration.toString();
        }
        return expirationStr;
    }

    public static LocalDateTime getTime(String time,String type){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(type);
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        return dateTime;
    }
}