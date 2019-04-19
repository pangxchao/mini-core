package com.mini.test;

import com.mini.web.MiniServletInitializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@SpringBootApplication
public class MiniApplication extends MiniServletInitializer {

    @Override
    protected Class<?> getApplicationClass() {
        return MiniApplication.class;
    }

    // 默认数据源
    @Value("${spring.datasource.jndi-name}")
    private String jndiName;

    @Primary
    @Bean("dataSource")
    public DataSource mysqlJNDISource() {
        return MiniServletInitializer.getJndiDataSource(jndiName);
    }

    public static void main(String[] args) {
        SpringApplication.run(MiniApplication.class);
    }

}
