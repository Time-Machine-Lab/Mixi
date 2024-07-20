package com.mixi.user.constants;

import io.github.common.RedisKey;

import java.util.concurrent.TimeUnit;

public class RedisKeyConstant {

    // 用户token key
    public static final RedisKey USER_TOKEN_KEY = new RedisKey("mixi:user:token:%s",5, TimeUnit.HOURS);
    // 用户信息key
    public static final RedisKey USER_INFO_KEY = new RedisKey("mixi:user:info:%s",5, TimeUnit.HOURS);
    // 图片验证码key
    public static final RedisKey PIC_CODE_KEY = new RedisKey("mixi:user:pic-code:%s",1, TimeUnit.MINUTES);
    // 邮箱链接登录令牌key
    public static final RedisKey EMAIL_LINK_TOKEN_KEY = new RedisKey("mixi:user:link-token:%s",1, TimeUnit.MINUTES);
}
