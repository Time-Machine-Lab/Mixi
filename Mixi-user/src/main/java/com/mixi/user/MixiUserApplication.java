package com.mixi.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan(basePackages = {"com.mixi.common", "com.mixi.user"})
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@SuppressWarnings({"all"})
public class MixiUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(MixiUserApplication.class, args);
    }
}
