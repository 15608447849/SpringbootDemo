package com.demo.config;

import com.bottle.jdbc.JDBC;
import com.bottle.jdbc.imp.JDBCConnection;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.val;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static com.bottle.util.ObjectUtil.objectToProperties;


@Data
@Configuration
@ConfigurationProperties(prefix = "jdbc")
public class DatabaseConfig {

    private List<DataSourceConfig> sources = new ArrayList<>();

    @Data
    public static class DataSourceConfig {

        private String url;

        private String username;

        private String password;


    }


    @PostConstruct
    public void init() {
        // 执行你的初始化代码
        if (sources.isEmpty()) return;
        for (DataSourceConfig source : sources) {
            JDBCConnection pool = JDBC.loadDatabase(objectToProperties(source));

        }
    }

}
