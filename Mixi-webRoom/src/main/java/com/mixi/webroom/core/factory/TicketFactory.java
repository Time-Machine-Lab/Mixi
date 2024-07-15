package com.mixi.webroom.core.factory;

import com.mixi.webroom.core.enums.ResultEnums;
import com.mixi.webroom.core.exception.ServerException;
import com.mixi.webroom.utils.WebRoomUtil;
import lombok.Getter;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
public class TicketFactory {
    @Resource
    StringEncryptor encryptor;

    public String createTicket(Map<String, String> params) {
        return encryptor.encrypt(mapToString(params));
    }

    public String createTicket(Builder builder) {
        return encryptor.encrypt(mapToString(builder.getParams()));
    }

    public Map<String, String> decryptTicket(String ticket) {
        return stringToMap(encryptor.decrypt(ticket));
    }

    private String mapToString(Map<String, String> map) {
        return map.entrySet().stream()
                .map(entry -> entry.getKey() + ":" + entry.getValue())
                .reduce((a, b) -> a + "|" + b)
                .orElse("");
    }

    private Map<String, String> stringToMap(String input) {
        Map<String, String> map = new HashMap<>();
        if (input != null && !input.isEmpty()) {
            String[] pairs = input.split("\\|"); // 使用正则表达式分割，防止普通'|'字符被误识别

            for (String pair : pairs) {
                String[] keyValue = pair.split(":"); // 限制split只进行一次，避免处理值中可能包含的':'

                if (keyValue.length != 2) {
                    throw new ServerException(ResultEnums.TRANSCODE_ERROR);
                }
                String key = keyValue[0];
                String value = keyValue[1];
                map.put(key, value);
            }
        }
        return map;
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
