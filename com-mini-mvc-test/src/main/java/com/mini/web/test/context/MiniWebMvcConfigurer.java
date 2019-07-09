package com.mini.web.test.context;

import com.google.inject.Provides;
import com.mini.inject.annotation.Scanning;
import com.mini.web.config.HttpServletConfigure;
import com.mini.web.config.HttpServletConfigure.HttpServletElement;
import com.mini.web.config.WebMvcConfigure;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.naming.InitialContext;
import javax.sql.DataSource;

@Scanning("com.mini.web.test")
public class MiniWebMvcConfigurer extends WebMvcConfigure {
    @Override
    protected void onStartup() throws Error {
        enabledTransactionalInterceptor();
    }

    @Inject
    @Named("mini.datasource.jndi-name")
    private String jndiName;

    @Override
    protected HttpServletElement httpServletConfigure(HttpServletConfigure configure) {
        configure.addServlet(FileUploadServlet.class).addUrlPatterns("/front/user/group.htm");
        return super.httpServletConfigure(configure);
    }


    @Provides
    @Singleton
    public DataSource getDataSource() throws Exception {
        InitialContext context = new InitialContext();
        return (DataSource) context.lookup(jndiName);
    }
}
