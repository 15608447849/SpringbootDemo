package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication()
@EnableAspectJAutoProxy()
@EnableConfigurationProperties()
public class AppMain {
    public static void main(String[] args) {
        SpringApplication.run(AppMain.class, args);
    }
}
