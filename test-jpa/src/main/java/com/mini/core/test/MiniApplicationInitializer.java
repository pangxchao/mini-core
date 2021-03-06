package com.mini.core.test;

import com.mini.core.mvc.MiniSpringBootServletInitializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;

@EnableJpaAuditing
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
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
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }


}
