package com.mixi.webroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(basePackages = {"com.mixi.common", "com.mixi.rpc","com.mixi.webroom"})
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.mixi.rpc.client"})
@EnableAspectJAutoProxy
@EnableScheduling
public class MixiWebRoomApplication {
    public static void main(String[] args) {
        SpringApplication.run(MixiWebRoomApplication.class, args);
    }
}
