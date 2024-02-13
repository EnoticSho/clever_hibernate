package ru.clevertec.ecl.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Конфигурация базы данных.
 * Этот класс определяет конфигурацию для подключения к базе данных с использованием HikariCP и настраивает JdbcTemplate.
 */
@Configuration
@ComponentScan(basePackages = "ru.clevertec.ecl")
public class DatabaseConfig {

    @Value("${database.url}")
    private String dbUrl;

    @Value("${database.username}")
    private String dbUsername;

    @Value("${database.password}")
    private String dbPassword;

    @Value("${database.driver-class-name}")
    private String dbDriverClassName;

    /**
     * Создает и настраивает DataSource с использованием HikariCP для подключения к базе данных.
     *
     * @return DataSource для подключения к базе данных.
     */
    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dbUrl);
        hikariConfig.setUsername(dbUsername);
        hikariConfig.setPassword(dbPassword);
        hikariConfig.setDriverClassName(dbDriverClassName);
        return new HikariDataSource(hikariConfig);
    }

    /**
     * Создает и настраивает JdbcTemplate для выполнения SQL-запросов к базе данных с использованием DataSource.
     *
     * @param dataSource DataSource для подключения к базе данных.
     * @return JdbcTemplate для выполнения SQL-запросов.
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
