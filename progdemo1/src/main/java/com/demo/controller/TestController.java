package com.demo.controller;

import com.demo.config.LLMConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TestController {

    @Autowired
    private LLMConfig  llmConfig;
    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @RequestMapping("/")
    public String index(){
        return String.format("demo local time : %s",  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
    }
    @RequestMapping("/bean")
    public Object bean(){
        return llmConfig;
    }

    @RequestMapping("/convt")
    public List<String> listConverters() {
        return handlerAdapter.getMessageConverters().stream()
                .map(converter -> converter.getClass().getName())
                .collect(Collectors.toList());
    }

    @RequestMapping("/err")
    public Object rerror() {
        try {
            throw new IllegalArgumentException("我是一个错误");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
