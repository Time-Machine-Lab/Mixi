package com.infrastructure.core.auth.annotation;

import com.mixi.common.annotation.auth.AuthType;
import java.lang.annotation.*;

/**
 *  权限策略注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AuthStrategyType {
    AuthType value();
}