package com.demo.controller;

import com.demo.service.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/llm", produces = "application/json;charset=UTF-8")
public class LLMController {
    @Autowired
    private OllamaService ollamaService;

    @RequestMapping("/chat")
    public String simpleChat(@RequestParam(required = false, defaultValue = "你是谁?") String ask){
        return ollamaService.chat(ask);
    }



}
