package com.mini.web.test.context;

import com.google.inject.Provides;
import com.mini.inject.annotation.ComponentScan;
import com.mini.inject.annotation.EnableTransaction;
import com.mini.inject.annotation.PropertySource;
import com.mini.jdbc.JdbcTemplate;
import com.mini.jdbc.JdbcTemplateMysql;
import com.mini.jdbc.transaction.TransactionalManager;
import com.mini.jdbc.transaction.TransactionalManagerJdbc;
import com.mini.web.config.Configure;
import com.mini.web.config.WebMvcConfigure;
import com.mini.web.servlet.DispatcherHttpServlet;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

import static java.util.Collections.singletonList;

@Singleton
@EnableTransaction
@ComponentScan("com.mini.web.test")
@PropertySource("application.properties")
public class MiniWebMvcConfigurer extends WebMvcConfigure {

    @Inject
    @Named("mini.datasource.jndi-name")
    private String jndiName;

    @Override
    protected void onStartupRegister(ServletContext servletContext, Configure configure) {
        // 默认的Servlet相关设置可以在这里进行
        configure.addServlet(DispatcherHttpServlet.class);
        // 自定义Servlet相关设置可以在这里进行
        configure.addServlet(FileUploadServlet.class)
                .addUrlPatterns("/front/user/group.htm");
    }


    @Provides
    @Singleton
    //@Named("dataSource")
    public DataSource getDataSource(@Named("mini.datasource.jndi-name") String jndiName) throws NamingException {
        InitialContext context = new InitialContext();
        return (DataSource) context.lookup(jndiName);
    }

    @Provides
    @Singleton
    //@Named("jdbcTemplate")
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplateMysql(dataSource);
    }

    @Provides
    @Singleton
    //@Named("transactionalManager")
    public TransactionalManager getTransactionalManager(JdbcTemplate jdbcTemplate) {
        return new TransactionalManagerJdbc(singletonList(jdbcTemplate));
    }

    @Provides
    @Singleton
    //@Named("userTransactionProvider")
    public Provider<UserTransaction> getUserTransactionProvider() {
        return com.atomikos.icatch.jta.UserTransactionImp::new;
    }
}
