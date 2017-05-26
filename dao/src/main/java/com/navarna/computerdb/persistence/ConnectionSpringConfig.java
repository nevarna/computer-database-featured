package com.navarna.computerdb.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.User;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource({ "classpath:informationDB.properties" })
@ComponentScan(basePackages = "com.navarna.computerdb.persistence")
public class ConnectionSpringConfig {

    @Autowired
    private Environment env;

    @Bean(name = "dataSource")
    public HikariDataSource hikariDataSource() {
        HikariConfig configuration = new HikariConfig();
        configuration.setDriverClassName("com.mysql.cj.jdbc.Driver");
        configuration.setJdbcUrl(env.getProperty("Database.url"));
        configuration.setUsername(env.getProperty("Database.user"));
        configuration.setPassword(env.getProperty("Database.password"));
        configuration.setMaximumPoolSize(Integer.parseInt(env.getProperty("Database.maxPoolSize")));
        configuration.setAutoCommit(Boolean.parseBoolean(env.getProperty("Database.autoCommit")));
        configuration.addDataSourceProperty("cachePrepStmt", env.getProperty("Database.cachePrepStmt"));
        configuration.addDataSourceProperty("prepStmtCacheSize", env.getProperty("Database.prepStmtCacheSize"));
        configuration.addDataSourceProperty("prepStmtCacheSqlLimit", env.getProperty("Database.prepStmtCacheSqlLimit"));
        return new HikariDataSource(configuration);
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate creationJDBCTemplate() {
        return new JdbcTemplate(hikariDataSource());
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
        factory.setDataSource(hikariDataSource());
        factory.setAnnotatedClasses(Company.class, Computer.class,User.class);
        return factory;
    }
}
