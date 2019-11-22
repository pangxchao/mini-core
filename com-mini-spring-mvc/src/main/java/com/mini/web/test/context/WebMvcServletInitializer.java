package com.mini.web.test.context;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Web 配置信息读取
 * @author xchao
 */
@EnableWebMvc
@Configuration
@ComponentScan("com.mini.web.test")
public class WebMvcServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer //
        implements WebMvcConfigurer {

    @Override
    protected final Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{this.getClass()};
    }

    @Override
    protected final Class<?>[] getServletConfigClasses() {
        return new Class[]{this.getClass()};
    }

    @Nonnull
    @Override
    protected String[] getServletMappings() {
        return new String[]{"*.htm"};
    }

    /**
     * 视图前缀匹配配置
     * @param registry 注册器
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 这里表示，访问/hello3路径后，进入名为hello的视图去
        // registry.addViewController("/hello3").setViewName("hello");
    }

    /**
     * 配置静态资源处理器
     * @param configurer 配置信息
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //@Override
    //public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //    registry.addResourceHandler("/resource/**").addResourceLocations("/resource/");
    //
    //}

    /**
     * 配置使用 FreeMarker 模板工具
     * @return FreeMarker 配置
     */
    @Bean
    public FreeMarkerViewResolver mvcViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setContentType("text/html;charset=UTF-8");
        resolver.setExposeContextBeansAsAttributes(true);
        resolver.setRequestContextAttribute("request");
        resolver.setExposeSessionAttributes(true);
        resolver.setExposeRequestAttributes(true);
        resolver.setAllowRequestOverride(true);
        resolver.setAllowSessionOverride(true);
        resolver.setPrefix("/WEB-INF/");
        resolver.setSuffix(".ftl");
        resolver.setCache(true);
        resolver.setOrder(1);
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer getFreemarkerConfig() {
        FreeMarkerConfigurer result = new FreeMarkerConfigurer();
        result.setTemplateLoaderPath("/");
        return result;
    }

    /**
     * JSON 转换设置为FastJson
     * @param converters 转换器列表
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        FastJsonConfig config = new FastJsonConfig();
        config.setCharset(StandardCharsets.UTF_8);
        converter.setFastJsonConfig(config);
        converters.add(converter);
    }

    //@Bean
    //public DataSource getDataSource() {
    //    return null;
    //}
}
