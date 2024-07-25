package com.mixi.common.annotation.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述: Api权限注解类
 * @author suifeng
 * 日期: 2024/7/6
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiAuth {

    /**
     *  权限注解分为4种:
     *  - NEED: 需要进行TOKEN验证的接口
     *  - NOT:  不需要进行TOKEN验证的接口
     *  - INNER: 服务内部调用的接口, 网关外部无法调用
     *  - OPTIONAL: 选填TOKEN，有TOKEN就会提取出来
     *  -----------------------------------------
     *  使用方式:
     *  - 1. 添加在类上，将会应用到类的所有方法上
     *  - 2. 添加在方法上，优先级最高，会覆盖类的注解
     *  - 3. 默认使用NEED，@ApiAuth <==> @ApiAuth(AuthType.NEED)
     */

    AuthType value() default AuthType.NEED;


    /**
     *  身份维度数组，主要针对NEED类型的接口:
     *  - 1. 只要加了身份维度的，就一定会转成NEED类型
     *  - 2. 加了roles的代表用户必须要持有此维度才能调用此接口，比如
     *      接口权限是{1001, 1002}, 持有1001和持有1002的用户都能
     *      调用, 而1003就会被拒绝
     *  - 3. 如果什么维度都不填，那么接口就是全开放的，不需要进行维度校验
     *  -----------------------------------------
     *  使用方式:
     *  - 1. @ApiAuth(roles = 1001)
     *  - 2. @ApiAuth(roles = {1002, 1003})
     *  - 3. @ApiAuth(value = AuthType.NEED, roles = {1001, 1002})
     */

    int[] roles() default {};

    /**
     *  添加前置后置过滤器，对应网关模块的过滤器
     *   - 默认为NIL，代表不进行任何处理
     */
    String before() default "NIL";
    String after() default "NIL";
}
