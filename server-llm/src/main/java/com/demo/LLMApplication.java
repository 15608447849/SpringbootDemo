package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication()
@EnableDiscoveryClient()
public class LLMApplication {
    public static void main(String[] args) {
//        System.setProperty("nacos.logging.default.config.enabled","false");
        SpringApplication.run(LLMApplication.class, args);
    }
}
