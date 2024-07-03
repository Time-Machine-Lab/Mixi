package com.mixi.webroom.utils;

import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author：XiaoChun
 * @Date：2024/6/28 上午11:23
 */
@Component
public class RedisUtil {
    @Resource
    private RedisTemplate redisTemplate;

    private static final TimeUnit defaultTimeUnit = TimeUnit.MINUTES;

    private static final Integer defaultExpire = 120;

    public <T> Boolean setNxObject(String key, T value, Integer timeout, TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit);
    }

    public <T> Boolean setNxObject(String key, T value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, defaultExpire, defaultTimeUnit);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     * @param key 缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    public <T> void setCacheObject(String key, T value) {

        setCacheObject(key, value, defaultExpire, defaultTimeUnit);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     * @return 缓存的对象
     */
    public <T> void setCacheObject(String key, T value, Integer timeout, TimeUnit timeUnit) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value, timeout, timeUnit);
    }

    /**
     * 获得缓存的基本对象。
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     * @param key
     */
    public void deleteObject(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     * @param collection
     */
    public void deleteObject(Collection collection) {
        redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     */
    public <T> void setCacheList(String key, List<T> dataList) {
        ListOperations listOperation = redisTemplate.opsForList();
        if (null != dataList) {
            listOperation.leftPushAll(key, dataList);
        }
    }

    /**
     * 获得缓存的list对象
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(String key) {
        ListOperations<String, T> listOperation = redisTemplate.opsForList();
        return listOperation.range(key, 0, -1);
    }

    /**
     * 缓存Set
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> void setCacheSet(String key, Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
    }

    /**
     * 获得缓存的set
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(String key) {
        Set<T> dataSet;
        BoundSetOperations<String, T> operation = redisTemplate.boundSetOps(key);
        dataSet = operation.members();
        return dataSet;
    }

    /**
     * 缓存Map
     * @param key
     * @param dataMap
     * @return
     */
    public <T> void setCacheMap(String key, Map<String, T> dataMap) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            for (Map.Entry<String, T> entry : dataMap.entrySet()) {
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 获得缓存的Map
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(String key) {
        Map<String, T> map = redisTemplate.opsForHash().entries(key);
        return map;
    }

    /**
     * 获得缓存的基本对象列表
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }
}
