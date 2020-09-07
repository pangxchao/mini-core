package com.mini.core.mvc.test;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.mini.core.jdbc.JdbcTemplate;
import com.mini.core.jdbc.MysqlJdbcTemplate;
import com.mini.core.mvc.support.WebApplicationInitializer;
import com.mini.core.mvc.support.config.Configures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
public class MiniTestWebApplicationInitializer implements WebApplicationInitializer {
    private static final String JNDI = "java:/comp/env/jdbc/cloud";

    @Override
    public void onStartup(javax.servlet.ServletContext context, Configures configures) {
        configures.setLocationPath(context.getInitParameter("LOCATION_PATH"));
        // 全局FastJson 设置
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        // configures
    }

    @Bean
    @Qualifier("dataSource")
    public DataSource dataSource() throws NamingException {
        InitialContext context = new InitialContext();
        return (DataSource) context.lookup(JNDI);
    }

    @Bean
    @Qualifier("jdbcTemplate")
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new MysqlJdbcTemplate(dataSource);
    }

    @Bean
    @Qualifier("transactionManager")
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
