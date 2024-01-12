package ru.clevertec.ecl.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.util.Objects;

/**
 * Конфигурация источника свойств для приложения.
 * Этот класс определяет конфигурацию для загрузки свойств из файла "application.yml" и их доступности в приложении.
 */
@Configuration
@ComponentScan(basePackages = "ru.clevertec.ecl")
public class PropertySourceConfig {

    /**
     * Создает и настраивает PropertySourcesPlaceholderConfigurer для загрузки свойств из файла "application.yml".
     *
     * @return PropertySourcesPlaceholderConfigurer для доступа к свойствам в приложении.
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertyConfigurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        propertyConfigurer.setProperties(Objects.requireNonNull(yaml.getObject()));
        return propertyConfigurer;
    }
}
