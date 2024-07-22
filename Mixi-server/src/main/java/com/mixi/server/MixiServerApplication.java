package com.mixi.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.annotation.Resource;

@SpringBootApplication(scanBasePackages = "com.mixi.*")
@EnableDiscoveryClient
@EnableFeignClients
public class MixiServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MixiServerApplication.class, args);
    }

    @Resource
    NettyServerBoot nettyServerBoot;
    @Override
    public void run(String... args) throws Exception {
        nettyServerBoot.start();
    }
}
