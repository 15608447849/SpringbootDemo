package com.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "wx")
public class WXConfig {

    @Data
    public static class MinProg {
        private String appid;
        private String appsecret;
    }


    private MinProg minprog ;

}
