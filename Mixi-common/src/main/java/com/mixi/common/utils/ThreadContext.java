package com.mixi.common.utils;

import java.util.HashMap;
import java.util.Map;

public class ThreadContext {

    private static final ThreadLocal<ThreadContext> LOCAL = ThreadLocal.withInitial(
        ThreadContext::new
    );

    private final Map<String,Object> data;

    public ThreadContext() {
        this.data = new HashMap<>();
    }

    public static ThreadContext context() {
        return LOCAL.get();
    }

    public static Object getData(String key) {
        return LOCAL.get().data.get(key);
    }

    public static void setData(String key, Object value) {
        context().data.put(key, value);
    }

    public <T> T getData(String key,Class<T> clazz){
        return clazz.cast(getData(key));
    }

    public static void clear() {
        LOCAL.get().data.clear();
    }
}
