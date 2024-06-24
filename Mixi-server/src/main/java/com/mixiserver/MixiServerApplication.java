package com.mixiserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
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
