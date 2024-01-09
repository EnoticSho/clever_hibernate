package ru.clevertec.ecl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.clevertec.ecl.config.DatabaseConfig;
import ru.clevertec.ecl.service.impl.HouseServiceImpl;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        HouseServiceImpl bean = context.getBean(HouseServiceImpl.class);
        System.out.println(bean.findAll(1, 1));
    }
}
