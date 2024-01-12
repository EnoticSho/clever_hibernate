package ru.clevertec.ecl.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Конфигурация Liquibase для управления миграциями базы данных.
 * Этот класс определяет конфигурацию Liquibase для автоматического применения миграций базы данных при запуске приложения.
 */
@Configuration
@ComponentScan(basePackages = "ru.clevertec.ecl")
public class liquibaseConfig {

    @Value("${database.sql-path}")
    private String sqlPathKey;

    /**
     * Создает и настраивает SpringLiquibase для автоматического применения миграций базы данных.
     *
     * @param dataSource DataSource для подключения к базе данных.
     * @return SpringLiquibase для управления миграциями.
     */
    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(sqlPathKey);
        return liquibase;
    }
}
