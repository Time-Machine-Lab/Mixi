//package com.app.utils;
//
//import com.app.model.dto.UserInfoDTO;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.Claim;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.mixi.common.exception.ServeException;
//import com.mixi.common.utils.RCode;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static com.app.config.TokenConstant.*;
//
///**
// * 描述: Token验证工具类
// * @author suifeng
// * 日期: 2024/7/5
// */
//@RequiredArgsConstructor
//@Component
//@Slf4j
//public class TokenProvider {
//
//    private final StringRedisTemplate redisTemplate;
//
//    /**
//     * 创建一个token
//     * @param username 用户名
//     * @param userId 用户Id
//     * @param rememberMe 是否记住我
//     * @return 加密token
//     */
//    public static String createAndSignToken(String userId, String username, boolean rememberMe, Collection<? extends GrantedAuthority> authorities) {
//
//        // 拼接权限成完整字符串
//        String authoritiesStr = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
//
//        // 设定过期时间
//        Date validity = new Date((new Date()).getTime() + (rememberMe ? TOKEN_VALIDITY_IN_MILLISECONDS_FOR_REMEMBER_ME : TOKEN_VALIDITY_IN_MILLISECONDS));
//
//        try {
//            return com.auth0.jwt.JWT.create()
//                    .withClaim(AUTHORITIES_KEY, authoritiesStr)
//                    .withClaim(USER_NAME_KEY, username)
//                    .withClaim(USER_ID_KEY, userId)
//                    .withIssuer(ISSUER)
//                    .withExpiresAt(validity)
//                    .sign(getAlgorithm());
//        } catch (JWTVerificationException exception) {
//            log.error("创建token失败，Could be an illegal claim");
//            throw new ServeException(RCode.FAILED_TO_CREATE_TOKEN);
//        }
//    }
//
//    /**
//     * 验证token
//     */
//    public boolean validateToken(String token) {
//        try {
//            // 验证token，抛异常代表验证失败
//            getDecodedJWT(token);
//
//            // 这里主要对于用户主动选择登出的情况，判断这个token在不在黑名单里
//            return !Boolean.TRUE.equals(redisTemplate.hasKey(TOKEN_BLACK_LIST + token));
//        } catch (JWTVerificationException exception) {
//            return false;
//        }
//    }
//
//    /**
//     * 从请求体中提取token
//     */
//    public String extractToken(HttpServletRequest request) {
//        String token = request.getHeader(TOKEN_HEADER);
//        // 判断是否是带有"Bearer "前缀，并且去掉token的"Bearer "前缀
//        if (token != null && token.startsWith(TOKEN_PREFIX)) {
//            return token.substring(TOKEN_PREFIX.length());
//        }
//        return null;
//    }
//
//    /**
//     * 从 token 获取权限信息
//     */
//    public Authentication getAuthentication(String token) {
//        try {
//            Map<String, Claim> claimMap = getClaimMap(token);
//            Collection<? extends GrantedAuthority> authorities =
//                    Arrays.stream(claimMap.get(AUTHORITIES_KEY).asString().split(","))
//                            .map(SimpleGrantedAuthority::new)
//                            .collect(Collectors.toList());
//
//            UserInfoDTO principal = new UserInfoDTO(claimMap.get(USER_ID_KEY).asString(), claimMap.get(USER_NAME_KEY).asString());
//            return new UsernamePasswordAuthenticationToken(principal, token, authorities);
//        } catch (Exception e) {
//            log.error("从Token中获取权限信息失败: {}", e.getMessage());
//            return null;
//        }
//    }
//
//    /**
//     * 根据token获取用户信息
//     */
//    public String getInfoFromToken(String token) {
//        try {
//            Map<String, Claim> claimMap = getClaimMap(token);
//            return claimMap.get(USER_ID_KEY).asString();
//        } catch (Exception e) {
//            log.error("从Token中获取用户信息失败: {}", e.getMessage());
//            return null;
//        }
//    }
//
//    private DecodedJWT getDecodedJWT(String token) {
//        return com.auth0.jwt.JWT.require(getAlgorithm()).withIssuer(ISSUER).build().verify(token);
//    }
//
//    private Map<String, Claim> getClaimMap(String token) {
//        return getDecodedJWT(token).getClaims();
//    }
//
//    private static Algorithm getAlgorithm() {
//        return Algorithm.HMAC256(SECRET);
//    }
//}
