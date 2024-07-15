package com.mixi.webroom.config;

import com.mixi.webroom.core.worker.SnowFlakeIdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Date 2024/7/9
 * @Author xiaochun
 */
@Configuration
public class SnowFlakeIdWorkerConfig {

    @Bean
    public SnowFlakeIdWorker snowFlakeIdWorker(){
        return new SnowFlakeIdWorker();
    }
}
