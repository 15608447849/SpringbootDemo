package com.demo.controller;

import com.bottle.jdbc.JDBC;
import com.bottle.spr.config.DatabaseConfig;
import com.demo.config.LLMConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class TestController {

    @Autowired
    private LLMConfig  llmConfig;

    @Autowired
    private DatabaseConfig databaseConfig;

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @GetMapping("/")
    public String index(){
        return String.format("demo local time : %s",  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
    }

    @GetMapping("/llmconfig")
    public Object llm(){
        return llmConfig.toString();
    }

    @GetMapping("/dbconfig")
    public Object database(){
        return databaseConfig.toString();
    }

    @GetMapping("/convt")
    public List<String> listConverters() {
        return handlerAdapter.getMessageConverters().stream()
                .map(converter -> converter.getClass().getName())
                .collect(Collectors.toList());
    }

    @GetMapping("/err")
    public Object rerror() {
        try {
            throw new IllegalArgumentException("我是一个错误");
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/querydb")
    public Object querydb() {
        return Objects.requireNonNull(JDBC.getFacadeFirst()).query("SELECT * FROM t_user ", null, null);
    }
}
