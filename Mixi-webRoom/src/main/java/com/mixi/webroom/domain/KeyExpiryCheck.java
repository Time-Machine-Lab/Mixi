package com.mixi.webroom.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
@RequiredArgsConstructor
@Slf4j
public class KeyExpiryCheck {

    private final RedisTemplate<String, Object> redisTemplate;

    public void startCheck(Map<String, String[]> ticket){
        List<Object> results = checkAndDeleteExpiredKeys(ticket);
        List<String[]> timeoutList = getTimeoutTicket(ticket, results);
        deleteExpiredTicket(timeoutList);
    }

    public List<Object> checkAndDeleteExpiredKeys(Map<String, String[]> ticket) {
        return redisTemplate.executePipelined(new SessionCallback<>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                ValueOperations<String, Object> valueOperations = (ValueOperations<String, Object>) operations.opsForValue();
                // 检查每个key是否过期
                for (String ticket : ticket.keySet()) {
                    valueOperations.getOperations().hasKey(ticket);
                }
                return null;
            }
        });
    }

    public List<String[]> getTimeoutTicket(Map<String, String[]> ticket, List<Object> results) {
        List<String[]> timeoutTickets = new ArrayList<>();

        Iterator<Map.Entry<String, String[]>> iterator = ticket.entrySet().iterator();
        for (Object b: results){
            Map.Entry<String, String[]> entry = iterator.next();
            if(Boolean.FALSE.equals(b)){
                timeoutTickets.add(entry.getValue());
                iterator.remove();
            }
        }
        log.info(String.format("检查完毕，过期ticket共%d条", timeoutTickets.size()));
        return timeoutTickets;
    }

    public void deleteExpiredTicket(List<String[]> timeoutTickets) {
        redisTemplate.executePipelined(new SessionCallback<>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                HashOperations<String, String, Object> option = (HashOperations) operations.opsForHash();
                for (String[] ticket : timeoutTickets) {
                    option.increment(ticket[0], ticket[1], -1);
                }
                return null;
            }
        });
    }
}