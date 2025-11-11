package com.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;




@Data
@Configuration
@ConfigurationProperties(prefix = "llm")
public class LLMConfig {

    @Data
    public static class OllamaProperties {
        private String url;
        private String model;
    }

    @Data
    public static class DeepseekProperties {
        private String url;
        private String model;
        private String appkey;
    }


    private double temperature = 0.7;
    private OllamaProperties ollama;
    private DeepseekProperties deepseek;


}
