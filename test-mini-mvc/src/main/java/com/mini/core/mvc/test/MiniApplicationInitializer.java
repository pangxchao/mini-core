package com.mini.core.mvc.test;

import com.mini.core.jdbc.common.LongId;
import com.mini.core.jdbc.listener.LongIdBeforeSaveApplicationListener;
import com.mini.core.mvc.MiniSpringBootServletInitializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;

@EnableWebMvc
@EnableJdbcAuditing
@SpringBootApplication
@EnableJdbcRepositories
@EnableTransactionManagement
@Import(AbstractJdbcConfiguration.class)
public class MiniApplicationInitializer extends MiniSpringBootServletInitializer {
    private static final String JNDI = "java:/comp/env/jdbc/mini";

    public static void main(String[] args) {
        R.setLocationPath("c:/temp");
        run(MiniApplicationInitializer.class, args);
    }

    @Override
    public void onStartup(ServletContext context) throws ServletException {
        R.setLocationPath(context.getInitParameter("LOCATION_PATH"));
        super.onStartup(context);
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
    @Qualifier("namedParameterJdbcOperations")
    public NamedParameterJdbcOperations namedParameterJdbcOperations(@Qualifier("dataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Qualifier("beforeSaveApplicationListener")
    public ApplicationListener<BeforeSaveEvent<LongId>> beforeSaveApplicationListener() {
        return new LongIdBeforeSaveApplicationListener();
    }

    @Bean
    @Qualifier("transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
