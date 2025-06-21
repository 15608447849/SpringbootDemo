package com.demo.controller;

import com.bottle.jdbc.JDBC;
import com.demo.config.DatabaseConfig;
import com.demo.config.LLMConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("/")
    public String index(){
        return String.format("demo local time : %s",  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
    }

    @RequestMapping("/llmconfig")
    public Object llm(){
        return llmConfig.toString();
    }

    @RequestMapping("/dbconfig")
    public Object database(){
        return databaseConfig.toString();
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

    @RequestMapping("/querydb")
    public Object querydb() {
        return Objects.requireNonNull(JDBC.getFacadeFirst()).query("SELECT * FROM t_user ", null, null);
    }
}
