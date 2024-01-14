package ru.clevertec.ecl.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Конфигурация Hibernate для работы с базой данных.
 * Этот класс определяет конфигурацию Hibernate для управления сессиями и транзакциями базы данных.
 */
@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = "ru.clevertec.ecl")
public class HibernateConfig {

    /**
     * Создает и настраивает фабрику сессий Hibernate с использованием указанного источника данных (DataSource).
     * Настраивает сканирование пакетов и свойства Hibernate.
     *
     * @param dataSource Источник данных для конфигурации сессий.
     * @return Фабрика сессий Hibernate.
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("ru.clevertec.ecl");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    /**
     * Создает и настраивает менеджер транзакций Hibernate для управления транзакциями в приложении.
     * Ассоциирует менеджер транзакций с фабрикой сессий Hibernate.
     *
     * @param dataSource Источник данных, используемый для транзакций.
     * @return Менеджер транзакций Hibernate.
     */
    @Bean
    public HibernateTransactionManager transactionManager(DataSource dataSource) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory(dataSource).getObject());
        return transactionManager;
    }

    /**
     * Возвращает настройки Hibernate, включая параметр "hibernate.show_sql", установленный в "true" для отображения SQL-запросов в логах.
     *
     * @return Настройки Hibernate.
     */
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "true");
        return properties;
    }
}
