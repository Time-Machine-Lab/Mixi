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
     *  权限注解分为3种:
     *  - NEED: 需要进行TOKEN验证的接口
     *  - NOT:  不需要进行TOKEN验证的接口
     *  - INNER: 服务内部调用的接口, 网关外部无法调用
     *  -----------------------------------------
     *  使用方式:
     *  - 1. 添加在类上，将会应用到类的所有方法上
     *  - 2. 添加在方法上，优先级最高，会覆盖类的注解
     *  - 3. 默认使用NEED，@ApiAuth <==> @ApiAuth(AuthType.NEED)
     */

    AuthType value() default AuthType.NEED;
}
