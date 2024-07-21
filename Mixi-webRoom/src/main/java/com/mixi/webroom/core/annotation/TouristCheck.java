package com.mixi.webroom.core.annotation;

import java.lang.annotation.*;

/**
 * 描述: 游客模式检查注解，用于标记需要进行游客判断的方法
 * @author suifeng
 * 日期: 2024/7/20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface TouristCheck {
}