package com.mini.spring.test;

import com.mini.spring.MiniWebInitializer;
import com.mini.spring.dao.DaoTemplate;
import com.mini.spring.dao.MysqlDaoTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan("com.mini.spring.test.*")
public class MiniApplication extends MiniWebInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MiniApplication.class);
    }

    /////////////////////////////////////////////////////////////////
    ///////////////////////////// 配置信息 ////////////////////////////
    /////////////////////////////////////////////////////////////////

    @Override
    protected Class<?> getApplicationClass() {
        return MiniApplication.class;
    }

    //@Bean
    //public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
    //    return new ResourceUrlEncodingFilter();
    //}

    @Primary
    @Bean("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean("daoTemplate")
    public DaoTemplate daoTemplate(@Qualifier("dataSource") DataSource dataSource) {
        return new MysqlDaoTemplate(dataSource);
    }

    @Primary
    @Bean("transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    //@Bean(name = "userDataSource")
    //@ConfigurationProperties(prefix = "spring.datasource.user")
    //public MysqlXADataSource userDataSource() throws SQLException {
    //    MysqlXADataSource dataSource = new MysqlXADataSource();
    //    dataSource.setPinGlobalTxToPhysicalConnection(true);
    //    dataSource.setPinGlobalTxToPhysicalConnection(true);
    //    return dataSource;
    //}
    //
    //@Bean(name = "userDataSourceBean")
    //@ConfigurationProperties(prefix = "spring.datasource.basics")
    //public AtomikosDataSourceBean userDataSourceBean(@Qualifier("userDataSource") MysqlXADataSource dataSource) throws SQLException {
    //    AtomikosDataSourceBean dataSourceBean = new AtomikosDataSourceBean();
    //    dataSourceBean.setUniqueResourceName("userDataSourceBean");
    //    dataSourceBean.setXaDataSource(dataSource);
    //    return dataSourceBean;
    //}
    //
    //@Bean(name = "userTemplate")
    //public DaoTemplate userTemplate(@Qualifier("userDataSourceBean") DataSource dataSource) {
    //    return new MysqlDaoTemplate(dataSource);
    //}
    //
    //@Bean(name = "commonDataSource")
    //@ConfigurationProperties(prefix = "spring.datasource.common")
    //public MysqlXADataSource commonDataSource() throws SQLException {
    //    MysqlXADataSource xaDataSource = new MysqlXADataSource();
    //    xaDataSource.setPinGlobalTxToPhysicalConnection(true);
    //    xaDataSource.setPinGlobalTxToPhysicalConnection(true);
    //    return xaDataSource;
    //
    //}
    //
    //@Bean(name = "commonDataSourceBean")
    //@ConfigurationProperties(prefix = "spring.datasource.basics")
    //public AtomikosDataSourceBean commonDataSourceBean(@Qualifier("commonDataSource") MysqlXADataSource dataSource) throws SQLException {
    //    AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
    //    atomikosDataSourceBean.setUniqueResourceName("commonDataSourceBean");
    //    atomikosDataSourceBean.setXaDataSource(dataSource);
    //    return atomikosDataSourceBean;
    //}
    //
    //@Bean(name = "commonTemplate")
    //public DaoTemplate commonTemplate(@Qualifier("commonDataSourceBean") DataSource dataSource) {
    //    return new MysqlDaoTemplate(dataSource);
    //}
}
