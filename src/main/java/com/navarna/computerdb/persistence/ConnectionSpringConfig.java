package com.navarna.computerdb.persistence;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource({ "classpath:informationDB.properties" })
@ComponentScan(basePackages = "com.navarna.computerdb")
public class ConnectionSpringConfig {

    @Autowired
    private Environment env;

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate creationJDBCTemplate() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(env.getProperty("Database.url"));
        dataSource.setUsername(env.getProperty("Database.user"));
        dataSource.setPassword(env.getProperty("Database.password"));
        Properties properties = new Properties();
        properties.put("cachePrepStmt", env.getProperty("Database.cachePrepStmt"));
        properties.put("prepStmtCacheSize", env.getProperty("Database.prepStmtCacheSize"));
        properties.put("prepStmtCacheSqlLimit",  env.getProperty("Database.prepStmtCacheSqlLimit"));
        dataSource.setConnectionProperties(properties);
        return new JdbcTemplate(dataSource);
    }
}
