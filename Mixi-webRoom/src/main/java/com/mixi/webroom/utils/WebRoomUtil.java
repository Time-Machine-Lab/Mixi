package com.mixi.webroom.utils;

import com.mixi.webroom.pojo.DO.WebRoom;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Date 2024/7/7
 * @Author xiaochun
 */
@Component
public class WebRoomUtil {
    @Resource
    private StringEncryptor encryptor;

    @Value("${room.ip}")
    private String ip;

    @Value("${room.port}")
    private String port;

    public String link(WebRoom webRoom){
        return ip + ":" + port + "/linkJoin/?key=" + encryptor.encrypt(webRoom.getRoomId());
    }

    public String decryptLink(String key){
        return encryptor.decrypt(key);
    }
}
