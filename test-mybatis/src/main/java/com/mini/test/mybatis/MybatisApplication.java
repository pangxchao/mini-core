package com.mini.test.mybatis;

import com.mini.core.mvc.MiniSpringBootServletInitializer;
import com.mini.core.mybatis.MiniMybatisConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.springframework.jmx.support.RegistrationPolicy.IGNORE_EXISTING;


@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.mini.test.mybatis")
@Import(MiniMybatisConfiguration.class)
@EnableMBeanExport(registration = IGNORE_EXISTING)
public class MybatisApplication extends MiniSpringBootServletInitializer {

    public static void main(String[] args) {
        run(MybatisApplication.class, args);
    }


}

