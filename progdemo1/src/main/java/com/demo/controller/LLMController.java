package com.demo.controller;

import com.demo.service.llm.DeepSeekService;
import com.demo.service.llm.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/llm", produces = "application/json;charset=UTF-8")
public class LLMController {

    @Autowired
    private OllamaService ollamaService;

    @Autowired
    private DeepSeekService deepSeekService;

    @RequestMapping("/chat")
    public String simpleChat(@RequestParam(required = false, defaultValue = "你是谁?") String ask){
        return ollamaService.chat(ask);
    }

    @RequestMapping("/chat2")
    public String simpleChat2(@RequestParam(required = false, defaultValue = "你是谁?") String ask){
        return deepSeekService.chat(ask);
    }



}
