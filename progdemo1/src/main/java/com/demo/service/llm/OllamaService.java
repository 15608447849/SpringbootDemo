package com.demo.service.llm;

import com.demo.config.LLMConfig;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OllamaService {

    private ChatLanguageModel model;

    @Autowired
    public OllamaService(LLMConfig llmConfig) {
        log.info("llm 配置 {}", llmConfig.getOllama());
        // 初始化Ollama模型
        this.model = OllamaChatModel.builder()
                .baseUrl(llmConfig.getOllama().getUrl()) // Ollama 默认地址
                .modelName(llmConfig.getOllama().getModel()) // 模型名称
                .temperature(llmConfig.getTemperature()) // 控制生成文本的随机性
                .build();
    }

    public String chat(String ask) {
        // 简单的聊天交互
        log.info("问题: {}", ask);
        return model.generate(ask);
    }

}
