package com.fedirchyk.blackjack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * This Class is Entry - Point for all application, it runs Spring Boot engine and contains setup configuration
 * 
 * @author artem.fedirchyk
 * 
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class EntryPoint extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(EntryPoint.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EntryPoint.class);
    }
}