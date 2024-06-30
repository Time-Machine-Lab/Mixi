package com.mixi.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SuppressWarnings({"all"})
public class MixiUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(MixiUserApplication.class, args);
    }
}
