package com.bottle.spr;

import com.bottle.moduls.OutResultStruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@Configuration
@EnableAspectJAutoProxy
@ConditionalOnClass(OutResultStruct.class)
@ComponentScan("com.bottle.spr")
public class AutoConfig {

    public AutoConfig() {
        log.info("自动加载-自定义spring boot环境");
    }
}
