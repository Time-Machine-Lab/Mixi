package com.mixi.webroom.domain;

import com.alibaba.fastjson.JSONObject;
import com.mixi.webroom.constants.LuaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.mixi.webroom.constants.RedisKeyConstants.webRoom;

/**
 * @Author：XiaoChun
 * @Date：2024/6/28 上午11:23
 */
@Component
@SuppressWarnings("all")
public class RedisOption {

    @Resource
    private RedisTemplate redisTemplate;

    private static final TimeUnit defaultTimeUnit = TimeUnit.SECONDS;

    @Value("${spring.redis.defaultExpire}")
    private Integer defaultExpire;

    public <T> Boolean setNxObject(String key, T value, Integer timeout, TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfAbsent(key, JSONObject.toJSONString(value), timeout, timeUnit);
    }

    public <T> Boolean setNxObject(String key, T value) {
        return redisTemplate.opsForValue().setIfAbsent(key, JSONObject.toJSONString(value));
    }

    public <T> Boolean setNnObject(String key, T value, Integer timeout, TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfPresent(key, JSONObject.toJSONString(value), timeout, timeUnit);
    }

    public <T> Boolean setNnObject(String key, T value) {
        return redisTemplate.opsForValue().setIfPresent(key, JSONObject.toJSONString(value));
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     * @param key 缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    public <T> void setCacheObject(String key, T value) {
        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(value));
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
        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(value), timeout, timeUnit);
    }

    /**
     * 获得缓存的基本对象。
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(String key, Class<T> clazz) {
        return JSONObject.parseObject((String) redisTemplate.opsForValue().get(key), clazz);
    }

    public void setHashObject(String key, Map<String, Object> map, Integer timeout, TimeUnit timeUnit) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(key, map);
        redisTemplate.expire(key, timeout, timeUnit);
    }

    public void setHashObject(String key, String subKey, Object value) {
        redisTemplate.opsForHash().put(key, subKey, value);
    }

    public void setHashInteger(String key, String subKey, Integer value) {
        redisTemplate.opsForHash().put(key, subKey, value);
    }

    public <T> T getHashObject(String key, String subKey, Class<T> clazz) {
        return JSONObject.parseObject((String) redisTemplate.opsForHash().get(key, subKey), clazz);
    }

    public String getHashString(String key, String subKey) {
        return (String) redisTemplate.opsForHash().get(key, subKey);
    }

    public Boolean setHashNx(String key, String subKey, Object value, Integer timeout, TimeUnit timeUnit) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if(hashOperations.putIfAbsent(key, subKey, value)) {
            redisTemplate.expire(key, timeout, timeUnit);
            return true;
        }
        return false;
    }

    public Boolean setHashNx(String key, String subKey, String value) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        return hashOperations.putIfAbsent(key, subKey, value);
    }

    public Boolean hashExist(String key, String subKey){
        return redisTemplate.opsForHash().hasKey(key, subKey);
    }

    public void hashIncrement(String key, String subKey, Integer increment){
        redisTemplate.opsForHash().increment(key, subKey, increment);
    }

    public void hashIncrement(String key, String subKey){
        hashIncrement(key, subKey, 1);
    }

    public void deleteHash(String key, String subKey){
        redisTemplate.opsForHash().delete(key);
    }

    public void deleteHash(String key){
        redisTemplate.delete(key);
    }

    /**
     * 删除单个对象
     * @param key
     */
    public Boolean deleteObject(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     * @param collection
     */
    public Long deleteObject(Collection collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * 获得缓存的基本对象列表
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public Boolean exist(String key){
        return redisTemplate.hasKey(key);
    }

    public void increment(String key, long delta) {
        redisTemplate.opsForValue().increment(key, delta);
    }

    public void increment(String key) {
        increment(key, 1);
    }

    public void removeExpiration(String key){
        redisTemplate.persist(key);
    }

    public Boolean compareAndIncrement(String key){
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(LuaConstants.COMPARE_AND_INCREMENT)));
        // Ensure you pass the hashKey as a list of keys to the execute method
        return Objects.nonNull(redisTemplate.execute(redisScript, Collections.singletonList(key)));
    }
}
