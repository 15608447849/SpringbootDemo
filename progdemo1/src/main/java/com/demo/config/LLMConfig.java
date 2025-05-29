package com.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import lombok.Data;




@Data
@Configuration
@ConfigurationProperties(prefix = "llm")
public class LLMConfig {

    private double temperature = 0.7;

    @Data
    public static class Ollama {
        private String url;
        private String model;
    }
    private Ollama ollama;


}
