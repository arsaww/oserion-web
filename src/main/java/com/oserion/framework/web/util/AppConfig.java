package com.oserion.framework.web.util;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.MultiPartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import javax.servlet.MultipartConfigElement;

/**
 * Created by Arsaww on 12/9/2015.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class AppConfig {
    public static final String PROPERTY_DB_CONNECTION = "database.connection";
    public static final String PROPERTY_SPRING_CONTEXT_API = "spring.context.api";
    public static final String PROPERTY_SPRING_CONTEXT_WEB = "spring.context.web";
    public static final String PROPERTY_CONFIG_PATH = "oserion.config.path";

    public static final String REQ_PARAM_AUTHENTICATION_LOGIN = "login";
    public static final String REQ_PARAM_AUTHENTICATION_PASSWORD = "password";

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultiPartConfigFactory factory = new MultiPartConfigFactory();
        factory.setMaxFileSize("5MB");
        factory.setMaxRequestSize("20MB");
        return factory.createMultipartConfig();
    }

}

