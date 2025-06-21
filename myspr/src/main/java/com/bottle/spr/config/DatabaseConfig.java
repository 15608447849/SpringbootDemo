package com.bottle.spr.config;

import com.bottle.jdbc.JDBC;
import com.bottle.jdbc.imp.JDBCConnection;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;
import static com.bottle.util.ObjectUtil.objectToProperties;

@Slf4j
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
            log.info("加载数据库连接池配置: {}", pool);
        }
    }

}
