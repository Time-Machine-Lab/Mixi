package com.mixi.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan(basePackages = {"com.mixi.common", "com.mixi.user"})
@SpringBootApplication
@SuppressWarnings({"all"})
public class MixiUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(MixiUserApplication.class, args);
    }
}
