package com.mini.web.test.context;

import com.google.inject.Provides;
import com.mini.web.config.HttpServletConfigure;
import com.mini.web.config.WebMvcConfigure;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.inject.Singleton;
import javax.naming.InitialContext;
import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class MiniWebMvcConfigurer implements WebMvcConfigure {

    @Value("${mini.datasource.jndi-name}")
    private String jndiName;

    @Override
    public void httpServletConfigure(HttpServletConfigure configure) {
        configure.addServlet(FileUploadServlet.class)
                .addUrlPatterns("/front/user/group.htm");

    }


    @Provides
    @Singleton
    public DataSource getDataSource() throws Exception {
        InitialContext context = new InitialContext();
        return (DataSource) context.lookup(jndiName);
    }
}
