package com.mini.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MiniApplication extends MiniServletInitializer {
    @Override
    protected Class<?> getApplicationClass() {
        return MiniApplication.class;
    }

    public static void main(String[] args) {
        // git@github.com:pangxchao/CFQ.git
        SpringApplication.run(MiniApplication.class);
    }
}
