package com.mini.core.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mini.core.mvc.processor.JsonModelProcessor;
import com.mini.core.mvc.processor.PageModelProcessor;
import com.mini.core.mvc.processor.StreamModelProcessor;
import com.mini.core.mvc.processor.WebSessionProcessor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES;
import static com.fasterxml.jackson.core.json.JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static com.fasterxml.jackson.databind.ser.std.ToStringSerializer.instance;

public abstract class MiniSpringBootServletInitializer extends SpringBootServletInitializer implements WebMvcConfigurer {
    @Autowired
    protected ApplicationContext applicationContext;

    @Override
    protected final SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(getSpringApplicationBuilderSources());
    }

    protected Class<?>[] getSpringApplicationBuilderSources() {
        return new Class<?>[]{this.getClass()};
    }

    @SuppressWarnings("SameParameterValue")
    protected static void run(Class<?>[] sources, String[] args) {
        SpringApplication.run(sources, args);
    }

    @SuppressWarnings("SameParameterValue")
    protected static void run(Class<?> source, String[] args) {
        SpringApplication.run(source, args);
    }

    @Bean
    @Qualifier("jsonModelProcessor")
    public JsonModelProcessor jsonModelProcessor() {
        return new JsonModelProcessor();
    }

    @Bean
    @Qualifier("pageModelProcessor")
    public PageModelProcessor pageModelProcessor() {
        return new PageModelProcessor();
    }

    @Bean
    @Qualifier("streamModelProcessor")
    public StreamModelProcessor streamModelProcessor() {
        return new StreamModelProcessor();
    }

    @Bean
    @Qualifier("webSessionProcessor")
    public WebSessionProcessor webSessionProcessor() {
        return new WebSessionProcessor();
    }

    @Bean
    @Qualifier("miniControllerInterceptor")
    public MiniControllerInterceptor miniControllerInterceptor() {
        return new MiniControllerInterceptor();
    }

    @Bean
    @Qualifier("miniMessageSupportHandler")
    public MiniMessageSupportHandler miniMessageSupportHandler() {
        return new MiniMessageSupportHandler();
    }

    @Bean
    @Qualifier("miniMessageController")
    public MiniMessageController miniMessageController() {
        return new MiniMessageController();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() throws RuntimeException, Error {
        final JsonMapper.Builder jsonMapperBuilder = JsonMapper.builder();
        // 是否允许JSON字符串包含未转义的控制字符(值小于32的ASCII字符，包括制表符和换行符)的特性。
        // 如果feature设置为false，则在遇到这样的字符时抛出异常。
        jsonMapperBuilder.configure(ALLOW_UNESCAPED_CONTROL_CHARS, true);
        // 有未知属性 要不要抛异常
        jsonMapperBuilder.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 确定解析器是否允许使用单引号(撇号，字符'\ ")引用字符串(名称和字符串值)的特性。
        // 如果是，这是除了其他可接受的标记。但不是JSON规范)。
        jsonMapperBuilder.configure(ALLOW_SINGLE_QUOTES, true);
        // 生成 JsonMapper 对象
        final JsonMapper jsonMapper = jsonMapperBuilder.build();
        // 日期类型输出为时间戳格式
        jsonMapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);
        // 输出Json串时，将Long转换成String类型
        jsonMapper.registerModule(new SimpleModule() {{
            this.addSerializer(Long.class, instance);
            addSerializer(Long.TYPE, instance);
        }});
        return jsonMapper;
    }

    @Override
    public void addArgumentResolvers(@NotNull List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(applicationContext.getBean(StreamModelProcessor.class));
        resolvers.add(applicationContext.getBean(WebSessionProcessor.class));
        resolvers.add(applicationContext.getBean(PageModelProcessor.class));
        resolvers.add(applicationContext.getBean(JsonModelProcessor.class));
    }

    /**
     * Spring 启动完成时调用
     *
     * @author xchao
     */
    public static class MiniApplicationRunner implements ApplicationRunner {
        public void run(ApplicationArguments arguments) {

        }
    }
}
