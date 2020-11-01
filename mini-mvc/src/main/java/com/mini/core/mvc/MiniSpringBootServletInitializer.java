package com.mini.core.mvc;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static com.fasterxml.jackson.core.json.JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static com.fasterxml.jackson.databind.ser.std.ToStringSerializer.instance;
import static java.util.Arrays.asList;

public abstract class MiniSpringBootServletInitializer extends SpringBootServletInitializer implements WebMvcConfigurer {
    private WebSessionProcessor webSessionProcessor;
    private StreamModelProcessor streamModelProcessor;
    private JsonModelProcessor jsonModelProcessor;
    private PageModelProcessor pageModelProcessor;

    @Autowired
    public final void setWebSessionProcessor(WebSessionProcessor webSessionProcessor) {
        this.webSessionProcessor = webSessionProcessor;
    }

    @Autowired
    public final void setStreamModelProcessor(StreamModelProcessor streamModelProcessor) {
        this.streamModelProcessor = streamModelProcessor;
    }

    @Autowired
    public final void setJsonModelProcessor(JsonModelProcessor jsonModelProcessor) {
        this.jsonModelProcessor = jsonModelProcessor;
    }

    @Autowired
    public final void setPageModelProcessor(PageModelProcessor pageModelProcessor) {
        this.pageModelProcessor = pageModelProcessor;
    }

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
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder mapperBuilder) {
        final ObjectMapper objectMapper = mapperBuilder.createXmlMapper(false).build();
        // 有未知属性时，不抛出JsonMappingException异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许出现特殊字符和转义符
        objectMapper.configure(ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        // 允许出现单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 不包含Null值
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        // 输出Json串时，将Long转换成String类型
        objectMapper.registerModule(new JsonComponentModule() {{
            this.addSerializer(Long.class, instance);
            addSerializer(Long.TYPE, instance);
        }});
        // 日期类型输出为时间戳格式
        objectMapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }

    @Override
    public void addArgumentResolvers(@NotNull List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.addAll(asList(streamModelProcessor, pageModelProcessor, jsonModelProcessor));
        resolvers.add(webSessionProcessor);
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
