package com.app.config;

/**
 * 描述: JWT操作常量
 * @author suifeng
 * 日期: 2024/7/5 
 */
public final class TokenConstant {

    /**
     * token有效期时长 12 小时
     */
    public static final long TOKEN_VALIDITY_IN_MILLISECONDS = 24 * 3600 * 1000;

    /**
     * 记住我后 token有效时长 7 天
     */
    public static final long TOKEN_VALIDITY_IN_MILLISECONDS_FOR_REMEMBER_ME = 7 * 24 * 3600 * 1000;

    public static final String TOKEN_HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String USER_ID_KEY = "mx_user_id_mx";

    public static final String USER_NAME_KEY = "mx_user_name_mx";

    public static final String ISSUER = "mx.love.mx";

    public static final String AUTHORITIES_KEY = "mx_auth_role_mx";

    /**
     * 秘钥
     */
    public static final String SECRET = "SecretKey039245678901232039487623456783092349288901402967890140939827";

    // 退出登录将token加入黑名单
    public static final String TOKEN_BLACK_LIST = "blacklist:";
}
