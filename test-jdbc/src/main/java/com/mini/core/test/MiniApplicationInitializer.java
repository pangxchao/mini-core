package com.mini.core.test;

import com.mini.core.jdbc.listener.MiniGeneratedIdListener;
import com.mini.core.mvc.MiniSpringBootServletInitializer;
import com.mini.core.test.interceptor.MiniHandlerInterceptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jdbc.repository.config.MyBatisJdbcConfiguration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import static org.springframework.jmx.support.RegistrationPolicy.IGNORE_EXISTING;

@EnableJdbcAuditing
@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
@Import(MyBatisJdbcConfiguration.class)
@MapperScan(basePackages = "com.mini.core.test")
@EnableMBeanExport(registration = IGNORE_EXISTING)
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
        return (DataSource) bean.getObject();
    }

    @Bean
    public NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public MiniGeneratedIdListener miniGeneratedIdListener() {
        return new MiniGeneratedIdListener();
    }

    @Bean
    public MiniHandlerInterceptor miniHandlerInterceptor() {
        return new MiniHandlerInterceptor();
    }

    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        registry.addInterceptor(miniHandlerInterceptor())
                .addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}


