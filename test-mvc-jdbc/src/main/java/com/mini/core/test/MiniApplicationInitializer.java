package com.mini.core.test;

import com.mini.core.data.listener.BeforeSaveApplicationListener;
import com.mini.core.mvc.MiniSpringBootServletInitializer;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringConnectionProvider;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.inject.Provider;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.sql.Connection;

import static org.springframework.jmx.support.RegistrationPolicy.IGNORE_EXISTING;

@EnableWebMvc
@EnableJdbcAuditing
@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
@Import({AbstractJdbcConfiguration.class})
@EnableMBeanExport(registration = IGNORE_EXISTING)
@EnableJdbcRepositories({"com.mini.core.jdbc", "com.mini.core.test"})
public class MiniApplicationInitializer extends MiniSpringBootServletInitializer {
    private static final String JNDI = "java:/comp/env/jdbc/mini";

    @Component
    public static class ServletContextListenerImpl implements ServletContextListener {
        public final void contextInitialized(ServletContextEvent servletContextEvent) {
            final ServletContext context = servletContextEvent.getServletContext();
            R.setLocationPath(context.getInitParameter("LOCATION_PATH"));
        }
    }

    public static void main(@Nullable String[] args) {
        DefaultMessageSourceResolvable a;
        run(MiniApplicationInitializer.class, args);
    }

    @Bean
    @Qualifier("dataSource")
    public DataSource dataSource() throws IllegalArgumentException, NamingException {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(false);
        bean.setJndiName(JNDI);
        bean.afterPropertiesSet();
        ValidationAutoConfiguration v;
        return (DataSource) bean.getObject();
    }

    @Bean
    @Qualifier("namedParameterJdbcOperations")
    public NamedParameterJdbcOperations namedParameterJdbcOperations(@Qualifier("dataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Qualifier("transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Qualifier("beforeSaveApplicationListener")
    public ApplicationListener<BeforeSaveEvent<Object>> beforeSaveApplicationListener() {
        return new BeforeSaveApplicationListener();
    }

    @Bean
    public com.querydsl.sql.Configuration querydslConfiguration() {
        SQLTemplates templates = MySQLTemplates.builder().build();
        var configuration = new com.querydsl.sql.Configuration(templates);
        configuration.setExceptionTranslator(new SpringExceptionTranslator());
        return configuration;
    }

    @Bean
    public SQLQueryFactory queryFactory(@Qualifier("dataSource") DataSource dataSource) {
        Provider<Connection> provider = new SpringConnectionProvider(dataSource);
        return new SQLQueryFactory(querydslConfiguration(), provider);
    }
}
