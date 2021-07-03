package com.csf.dbtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author fuping
 */
@SpringBootApplication
public class DbToolApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(DbToolApplication.class, args);
    }
}
