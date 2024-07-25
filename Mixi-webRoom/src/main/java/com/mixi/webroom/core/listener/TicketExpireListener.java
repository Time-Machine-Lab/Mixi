package com.mixi.webroom.core.listener;

import com.mixi.webroom.domain.KeyExpiryCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@EnableScheduling
public class TicketExpireListener {
    private final Map<String, String[]> keyMap;

    private final Map<String, String[]> increMap;

    @Resource
    private KeyExpiryCheck keyExpiryCheck;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private Boolean status;

    public TicketExpireListener() {
        this.keyMap = new ConcurrentHashMap<>();
        this.increMap = new ConcurrentHashMap<>();
        status = Boolean.FALSE;
    }

    public void put(String key, String[] value) {
        if(status){
            increMap.put(key, value);
        } else {
            keyMap.put(key, value);
        }
    }

    public Boolean exists(String key) {
        return keyMap.containsKey(key);
    }

    public void remove(String key) {
        redisTemplate.delete(key);
        redisTemplate.opsForHash().increment(keyMap.get(key)[0], keyMap.get(key)[1], -1);
        keyMap.remove(key);
    }

    // 每两秒执行一次
    @Scheduled(cron = "0/3 * * * * ? ")
    public void listenerSchedule() {
        if(this.keyMap.isEmpty()){
            return;
        }
        this.status = Boolean.TRUE;
        keyExpiryCheck.startCheck(this.keyMap);
        this.status = Boolean.FALSE;
        this.keyMap.putAll(increMap);
        increMap.clear();
    }
}