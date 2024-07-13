package com.mixi.user.constants;

/**
 * @NAME: RedisConstant
 * @USER: yuech
 * @Description:
 * @DATE: 2024/7/1
 */
public class RedisConstant {

    //redis前缀key
    public final static String REDIS_PRE = "mixi:user:link:";

    //redis key超时时间（min）
    public final static int REDIS_KEY_TIMEOUT = 5;

    private static final String REDIS_KEY_PREFIX = "LOGIN_CODE:";

    public static class loginCodeKey {
        public static String getKey(String username) {
            // 这里可以根据具体的业务逻辑生成一个唯一的 Redis 键名
            return REDIS_KEY_PREFIX + username;
        }
    }
}