package com.dada;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients
public class oauth {
    public static void main(String[] args) {
        SpringApplication.run(oauth.class);
    }
}
