package ru.clevertec.ecl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.clevertec.ecl.mapper.HouseMapper;
import ru.clevertec.ecl.mapper.HouseMapperImpl;
import ru.clevertec.ecl.mapper.PersonMapper;
import ru.clevertec.ecl.mapper.PersonMapperImpl;

/**
 * Конфигурация Spring для приложения.
 * Этот класс определяет основную конфигурацию Spring, включая настройку MVC, управление транзакциями и сканирование компонентов.
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "ru.clevertec.ecl")
public class SpringConfig {

    /**
     * Создает и настраивает бин для маппера домов (HouseMapper).
     *
     * @return HouseMapper для преобразования данных домов.
     */
    @Bean
    public HouseMapper houseMapper() {
        return new HouseMapperImpl();
    }

    /**
     * Создает и настраивает бин для маппера людей (PersonMapper).
     *
     * @return PersonMapper для преобразования данных людей.
     */
    @Bean
    public PersonMapper personMapper() {
        return new PersonMapperImpl();
    }


//    @Bean
//    public GenericDaoImpl<House> houseDao() {
//        return new GenericDaoImpl<>(House.class);
//    }
//
//    @Bean
//    public GenericDaoImpl<Person> personDao() {
//        return new GenericDaoImpl<>(Person.class);
//    }
}
