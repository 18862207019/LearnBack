package com.dada;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableCaching // 开启缓存
@SpringCloudApplication
@EnableFeignClients
@MapperScan("com.dada.mapper")
@Slf4j
public class UserBack {
    public static void main(String[] args) {
        SpringApplication.run(UserBack.class);
    }
}
