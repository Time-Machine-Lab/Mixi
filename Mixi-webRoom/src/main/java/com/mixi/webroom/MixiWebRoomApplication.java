package com.mixi.webroom;

import com.mixi.rpc.config.DefaultFeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//@ComponentScan(basePackages = {"com.mixi.common", "com.mixi.rpc","com.mixi.webroom", "org.springframework.mail"})
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.mixi.rpc.client"} , defaultConfiguration = DefaultFeignConfig.class)
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.mixi.common", "com.mixi.webroom"})
public class MixiWebRoomApplication {
    public static void main(String[] args) {
        SpringApplication.run(MixiWebRoomApplication.class, args);
    }

}
