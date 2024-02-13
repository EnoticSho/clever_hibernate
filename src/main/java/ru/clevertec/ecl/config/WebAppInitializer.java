package ru.clevertec.ecl.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Инициализатор веб-приложения для настройки DispatcherServlet и контекстов Spring.
 * Этот класс расширяет AbstractAnnotationConfigDispatcherServletInitializer и определяет конфигурацию
 * корневого контекста и контекста сервлета для приложения.
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Возвращает конфигурационные классы для корневого контекста приложения.
     *
     * @return Массив классов конфигурации для корневого контекста.
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{DatabaseConfig.class};
    }

    /**
     * Возвращает конфигурационные классы для контекста DispatcherServlet.
     *
     * @return Массив классов конфигурации для контекста DispatcherServlet.
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{DatabaseConfig.class};
    }

    /**
     * Возвращает URL-маппинг для DispatcherServlet.
     *
     * @return Массив строк, определяющих URL-маппинг для DispatcherServlet.
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
