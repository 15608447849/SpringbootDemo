package com.demo.controller;

import com.demo.config.LLMConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class TestController {

    @Autowired
    private LLMConfig  llmConfig;

    @RequestMapping("/")
    public String index(){
        return String.format("demo local time : %s",  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
    }
    @RequestMapping("/bean")
    public Object bean(){
        return llmConfig;
    }


}
