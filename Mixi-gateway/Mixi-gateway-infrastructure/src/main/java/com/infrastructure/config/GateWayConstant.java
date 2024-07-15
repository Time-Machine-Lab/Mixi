package com.infrastructure.config;

/**
 * 描述: 网关模块的常量
 * @author suifeng
 * 日期: 2024/7/12
 */
public class GateWayConstant {

    // 请求头中存放token
    public final static String AUTHORIZATION = "Authorization";

    // 正规token的"Bearer "前缀
    public final static String BEARER = "Bearer ";

    // 前端请求过来的非参数原始路径
    public final static String REQUEST_PREFIX = "X-Request-Prefix";

    // 添加与请求上下文对象的key
    public final static String REQUEST_CONTEXT = "requestContext";
}
