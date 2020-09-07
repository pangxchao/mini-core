package com.mini.core.test;

import com.mini.core.jdbc.JdbcTemplate;
import com.mini.core.jdbc.MysqlJdbcTemplate;
import com.mini.core.mvc.validation.ValidateUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.List;

import static javax.validation.Validation.buildDefaultValidatorFactory;

@EnableWebMvc
@SpringBootApplication
public class MiniTestApplication /*extends SpringBootServletInitializer*/ implements WebMvcConfigurer {
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(MiniTestApplication.class);
//    }

    public static void main(String[] args) {
        SpringApplication.run(MiniTestApplication.class, args);
    }
//
//    @Override
//    public void configureMessageConverters(@NotNull List<HttpMessageConverter<?>> converters) {
//        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//        converter.setDefaultCharset(StandardCharsets.UTF_8);
//        converter.setFastJsonConfig(new FastJsonConfig() {{
//            setCharset(StandardCharsets.UTF_8);
//        }});
//        converters.add(converter);
//    }


    @Bean
    @Qualifier("jdbcTemplate")
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new MysqlJdbcTemplate(dataSource);
    }
}
