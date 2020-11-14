package com.mini.core.test;

import com.mini.core.mvc.MiniSpringBootServletInitializer;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@SpringBootApplication
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dev")
public class MiniApplicationInitializerDev extends MiniSpringBootServletInitializer {
    private static final String JNDI = "jdbc/mini";

    @Bean
    public DataSourceConfig dataSourceConfig() {
        return new DataSourceConfig();
    }

    @Bean
    public TomcatServletWebServerFactory tomcatFactory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatWebServer(tomcat);
            }

            @Override
            protected void postProcessContext(Context context) {
                final ContextResource resource = new ContextResource();
                final DataSourceConfig config = dataSourceConfig();
                resource.setProperty("factory", BasicDataSourceFactory.class.getName());
                resource.setProperty("driverClassName", config.getDriverClassName());
                resource.setProperty("password", config.getPassword());
                resource.setProperty("username", config.getUsername());
                resource.setType(DataSource.class.getName());
                resource.setProperty("url", config.getUrl());
                resource.setName(JNDI);
                context.getNamingResources().addResource(resource);
                context.addParameter("LOCATION_PATH", "c:/temp");
                super.postProcessContext(context);
            }
        };
    }

    @Getter
    @Setter
    @Component
    @ConfigurationProperties(prefix = "spring.datasource")
    public static class DataSourceConfig {
        private String driverClassName;
        private String username;
        private String password;
        private String url;
    }

}
