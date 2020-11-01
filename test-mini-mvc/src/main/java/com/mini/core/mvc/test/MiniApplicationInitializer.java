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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;

@EnableJdbcAuditing
@SpringBootApplication
@EnableJdbcRepositories
public abstract class MiniApplicationInitializer extends MiniSpringBootServletInitializer {

    @EnableWebMvc
    @EnableTransactionManagement
    @Import(AbstractJdbcConfiguration.class)
    public static class WebServletInitializer extends MiniApplicationInitializer {
        private static final String JNDI = "java:/comp/env/jdbc/mini";

        @Override
        public void onStartup(ServletContext servletContext) throws ServletException {
            R.setLocationPath(servletContext.getInitParameter("LOCATION_PATH"));
            super.onStartup(servletContext);
        }

        @Bean
        @Qualifier("dataSource")
        public DataSource dataSource() throws NamingException {
            InitialContext context = new InitialContext();
            return (DataSource) context.lookup(JNDI);
        }
    }

    @EnableWebMvc
    @EnableTransactionManagement
    @Import(AbstractJdbcConfiguration.class)
    public static class MainApplication extends MiniApplicationInitializer {
        public static void main(String[] args) {
            run(MainApplication.class, args);
        }
    }

    @Bean
    @Qualifier("jdbcTemplate")
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @Qualifier("namedParameterJdbcOperations")
    public NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public ApplicationListener<BeforeSaveEvent<LongId>> LongIdBeforeSaveApplicationListener() {
        return new LongIdBeforeSaveApplicationListener();
    }

    @Bean
    @Qualifier("transactionManager")
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
