package com.mixi.webroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.mixi.common", "com.mixi.webroom"})
@SpringBootApplication
public class MixiWebRoomApplication {
    public static void main(String[] args) {
        SpringApplication.run(MixiWebRoomApplication.class, args);
    }

}
