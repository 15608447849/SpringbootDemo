package com.demo.controller;

import com.demo.service.llm.DeepSeekService;
import com.demo.service.llm.OllamaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/llm", produces = "application/json;charset=UTF-8")
public class LLMController {

    @Autowired
    private OllamaService ollamaService;

    @Autowired
    private DeepSeekService deepSeekService;

    @PostMapping("/ollama")
    public String chat_ollama(@RequestParam(required = false, defaultValue = "你是谁?") String ask){
        return ollamaService.chat(ask);
    }


    // https://devtask.cn:7001/llm/chat2?ask=你是谁?
    @PostMapping("/deepseek")
    public String chat_deepseek(@RequestParam(required = false, defaultValue = "你是谁?") String ask){
        return deepSeekService.chat(ask);
    }



}
