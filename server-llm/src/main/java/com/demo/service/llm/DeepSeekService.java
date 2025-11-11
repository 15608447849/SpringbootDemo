package com.demo.service.llm;

import com.demo.config.LLMConfig;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeepSeekService {

    private ChatLanguageModel model;

    @Autowired
    public DeepSeekService(LLMConfig llmConfig) {
        log.info("DeepSeekService llm 配置 {}", llmConfig.getDeepseek());
        // 初始化Ollama模型
        this.model = OpenAiChatModel.builder()
                .baseUrl(llmConfig.getDeepseek().getUrl()) // Ollama 默认地址
                .apiKey(llmConfig.getDeepseek().getAppkey())// 默认的key
                .modelName(llmConfig.getDeepseek().getModel()) // 模型名称
                .temperature(llmConfig.getTemperature()) // 控制生成文本的随机性
                .maxTokens(3000) // 最大返回 token 数
                .build();
    }

    public String chat(String ask) {
        // 简单的聊天交互
        log.info("问题: {}", ask);
        return model.generate(ask);
    }

}
