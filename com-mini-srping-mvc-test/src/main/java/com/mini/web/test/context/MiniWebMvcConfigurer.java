package com.mini.web.test.context;

import com.mini.web.config.WebMvcConfigureDefault;
import org.springframework.stereotype.Component;

@Component
//@Scanning("com.mini.web.test")
public class MiniWebMvcConfigurer extends WebMvcConfigureDefault {

    //@Inject
    //@Named("mini.datasource.jndi-name")
    //private String jndiName;
    //
    //@Override
    //protected HttpServletElement httpServletConfigure(HttpServletConfigure configure) {
    //    configure.addServlet(new FileUploadServlet()).addUrlPatterns("/front/user/group.htm");
    //    return super.httpServletConfigure(configure)
    //            .addUrlPatterns("/group1/*");
    //}
    //
    //@Override
    //protected void configureInitializer(Binder binder) throws Error {
    //    Injector injector = Guice.createInjector(new MiniModule());
    //
    //}
    //
    //
    //@Provides
    //@Component
    //public DataSource getDataSource() throws Exception {
    //    InitialContext context = new InitialContext();
    //    return (DataSource) context.lookup(jndiName);
    //}
}
