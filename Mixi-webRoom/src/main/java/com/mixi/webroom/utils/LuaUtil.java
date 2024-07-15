package com.mixi.webroom.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author：XiaoChun
 * @Date：2024/7/10 上午11:39
 */
@Component
public class LuaUtil {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public void bulkUpdate(List<String> keys, List<String> values) {
        // 创建Lua脚本实例
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("bulk_update.lua")));
        script.setResultType(Long.class); // 根据你的脚本返回类型来设置

        // 将键和值合并成一个列表，因为RedisTemplate只能接受单一列表作为参数
        List<String> allParams = new ArrayList<>(keys);
        allParams.addAll(values);

        // 执行脚本
        redisTemplate.execute(script, keys, (Object) allParams.toArray(new String[0]));
    }

//    public void bulkUpdate(Map<String, Object> keysAndValue) {
//        bulkUpdate(keysAndValue.keySet());
//    }
}
