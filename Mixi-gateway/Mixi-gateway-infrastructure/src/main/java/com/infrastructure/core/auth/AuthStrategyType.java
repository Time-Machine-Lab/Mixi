package com.infrastructure.core.auth;

import com.mixi.common.annotation.auth.AuthType;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthStrategyType {
    AuthType value();
}