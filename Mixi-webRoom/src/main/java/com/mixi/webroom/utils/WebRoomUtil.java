package com.mixi.webroom.utils;

import com.mixi.webroom.core.factory.TicketFactory;
import com.mixi.webroom.domain.entity.WebRoom;
import lombok.Getter;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2024/7/7
 * @Author xiaochun
 */
@Component
public class WebRoomUtil {
    @Resource
    private TicketFactory ticketFactory;

    @Value("${room.ip}")
    private String ip;

    @Value("${room.port}")
    private String port;

    public String link(Map<String, String> keys){
        return ip + ":" + port + "/linkJoin/?key=" + ticketFactory.createTicket(keys);
    }

    public String link(Builder builder){
        return link(builder.getParams());
    }

    public String link(WebRoom webRoom){
        Map<String, String> params = new HashMap<>();
        params.put("roomId", webRoom.getRoomId());
        return link(params);
    }

    public Map<String, String> decryptLink(String key){
        return ticketFactory.decryptTicket(key);
    }

    public static Builder builder(){
        return new Builder();
    }

    @Getter
    public static class Builder{
        private final Map<String, String> params = new HashMap<>();

        public Builder put(String key, String value){
            params.put(key, value);
            return this;
        }

        public Builder done(){
            return this;
        }
    }
}
