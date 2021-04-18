package com.mini.core.mvc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mini.core.mvc.converters.*;
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
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletContext;
import java.util.List;

import static com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteEnumUsingToString;
import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES;
import static com.fasterxml.jackson.core.json.JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static com.fasterxml.jackson.databind.ser.std.ToStringSerializer.instance;

public abstract class MiniSpringBootServletInitializer extends SpringBootServletInitializer implements WebMvcConfigurer {
    protected ApplicationContext context;

    @Autowired
    public final void setContext(ApplicationContext context) {
        this.context = context;
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
    @ConditionalOnMissingBean(value = JsonModelProcessor.class, name = "jsonModelProcessor")
    public JsonModelProcessor jsonModelProcessor() {
        return new JsonModelProcessor();
    }

    @Bean
    @Qualifier("pageModelProcessor")
    @ConditionalOnMissingBean(value = PageModelProcessor.class, name = "pageModelProcessor")
    public PageModelProcessor pageModelProcessor() {
        return new PageModelProcessor();
    }

    @Bean
    @Qualifier("streamModelProcessor")
    @ConditionalOnMissingBean(value = StreamModelProcessor.class, name = "streamModelProcessor")
    public StreamModelProcessor streamModelProcessor() {
        return new StreamModelProcessor();
    }

    @Bean
    @Qualifier("webSessionProcessor")
    @ConditionalOnMissingBean(value = WebSessionProcessor.class, name = "webSessionProcessor")
    public WebSessionProcessor webSessionProcessor() {
        return new WebSessionProcessor();
    }

    @Bean
    @Qualifier("miniControllerInterceptor")
    @ConditionalOnMissingBean(value = MiniControllerInterceptor.class, name = "miniControllerInterceptor")
    public MiniControllerInterceptor miniControllerInterceptor() {
        return new MiniControllerInterceptor();
    }

    @Bean
    @Qualifier("MiniMessageSupportController")
    @ConditionalOnMissingBean(value = MiniMessageSupportController.class, name = "MiniMessageSupportController")
    public MiniMessageSupportController miniMessageSupportController() {
        return new MiniMessageSupportController();
    }

    @Bean
    @Qualifier("miniMessageValidationHandler")
    @ConditionalOnMissingBean(value = MiniMessageValidationHandler.class, name = "miniMessageValidationHandler")
    public MiniMessageValidationHandler miniMessageValidationHandler() {
        return new MiniMessageValidationHandler();
    }

    @Bean
    @Qualifier("miniDefaultErrorAttributes")
    @ConditionalOnMissingBean(value = ErrorAttributes.class, name = "miniDefaultErrorAttributes")
    public MiniDefaultErrorAttributes miniDefaultErrorAttributes() {
        return new MiniDefaultErrorAttributes();
    }

    @Bean
    @Qualifier("dataConvertersFormat")
    @ConditionalOnMissingBean(value = DataConvertersFormat.class, name = "dataConvertersFormat")
    public DataConvertersFormat dataConvertersFormat() {
        return new DataConvertersFormat();
    }

    @Bean
    @Qualifier("stringToInstantConverter")
    @ConditionalOnMissingBean(value = StringToInstantConverter.class, name = "stringToInstantConverter")
    public StringToInstantConverter stringToInstantConverter() {
        return new StringToInstantConverter();
    }

    @Bean
    @Qualifier("stringToJavaUtilDateConverter")
    @ConditionalOnMissingBean(value = StringToJavaUtilDateConverter.class, name = "stringToJavaUtilDateConverter")
    public StringToJavaUtilDateConverter stringToJavaUtilDateConverter(DataConvertersFormat dataConvertersFormat) {
        return new StringToJavaUtilDateConverter(dataConvertersFormat);
    }

    @Bean
    @Qualifier("stringToLocalDateTimeConverter")
    @ConditionalOnMissingBean(value = StringToLocalDateTimeConverter.class, name = "stringToLocalDateTimeConverter")
    public StringToLocalDateTimeConverter stringToLocalDateTimeConverter(DataConvertersFormat dataConvertersFormat) {
        return new StringToLocalDateTimeConverter(dataConvertersFormat);
    }

    @Bean
    @Qualifier("stringToLocalDateConverter")
    @ConditionalOnMissingBean(value = StringToLocalDateConverter.class, name = "stringToLocalDateConverter")
    public StringToLocalDateConverter stringToLocalDateConverter(DataConvertersFormat dataConvertersFormat) {
        return new StringToLocalDateConverter(dataConvertersFormat);
    }

    @Bean
    @Qualifier("stringToLocalTimeConverter")
    @ConditionalOnMissingBean(value = StringToLocalTimeConverter.class, name = "stringToLocalTimeConverter")
    public StringToLocalTimeConverter stringToLocalTimeConverter(DataConvertersFormat dataConvertersFormat) {
        return new StringToLocalTimeConverter(dataConvertersFormat);
    }

    @Bean
    @Qualifier("stringToJavaSqlTimestampConverter")
    @ConditionalOnMissingBean(value = StringToJavaSqlTimestampConverter.class, name = "stringToJavaSqlTimestampConverter")
    public StringToJavaSqlTimestampConverter stringToJavaSqlTimestampConverter(DataConvertersFormat dataConvertersFormat) {
        return new StringToJavaSqlTimestampConverter(dataConvertersFormat);
    }

    @Bean
    @Qualifier("stringToJavaSqlDateConverter")
    @ConditionalOnMissingBean(value = StringToJavaSqlDateConverter.class, name = "stringToJavaSqlDateConverter")
    public StringToJavaSqlDateConverter stringToJavaSqlDateConverter(DataConvertersFormat dataConvertersFormat) {
        return new StringToJavaSqlDateConverter(dataConvertersFormat);
    }

    @Bean
    @Qualifier("stringToJavaSqlTimeConverter")
    @ConditionalOnMissingBean(value = StringToJavaSqlTimeConverter.class, name = "stringToJavaSqlTimeConverter")
    public StringToJavaSqlTimeConverter stringToJavaSqlTimeConverter(DataConvertersFormat dataConvertersFormat) {
        return new StringToJavaSqlTimeConverter(dataConvertersFormat);
    }

    @Bean
    @Primary
    @Qualifier("objectMapper")
    @ConditionalOnMissingBean(value = ObjectMapper.class, name = "objectMapper")
    public ObjectMapper objectMapper() throws RuntimeException, Error {
        final JsonMapper.Builder jsonMapperBuilder = JsonMapper.builder();
        // 否允许JSON字符串包含未转义的控制字符,值小于32的ASCII字符。
        jsonMapperBuilder.configure(ALLOW_UNESCAPED_CONTROL_CHARS, true);
        // 有未知属性 要不要抛异常
        jsonMapperBuilder.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 日期类型输出为时间戳格式
        jsonMapperBuilder.configure(WRITE_DATES_AS_TIMESTAMPS, true);
        // 属性为Null的不进行序列化，只对pojo起作用，对map和list不起作用
        jsonMapperBuilder.serializationInclusion(Include.NON_NULL);
        // json是否允许属性名为单引号 ，默认是false
        jsonMapperBuilder.configure(ALLOW_SINGLE_QUOTES, true);
        // 对Json进行缩进等格式化操作
        jsonMapperBuilder.configure(INDENT_OUTPUT, true);
        // 生成 JsonMapper对象,将Long转换成String类型
        JsonMapper jsonMapper = jsonMapperBuilder.build();
        jsonMapper.registerModule(new SimpleModule() {{
            addSerializer(Long.class, instance);
            addSerializer(Long.TYPE, instance);
        }});
        return jsonMapper;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 枚举类转JSON时使用 ToString 方法，并输出格式化配置
        final FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(WriteEnumUsingToString);
        fastJsonConfig.setSerializerFeatures(PrettyFormat);

        // 枚举类转JSON时使用 ToString 方法，并输出格式化配置
        JSON.DEFAULT_GENERATE_FEATURE |= WriteEnumUsingToString.mask;
        JSON.DEFAULT_GENERATE_FEATURE |= PrettyFormat.mask;

        // 对象转JSON时 Long 转换成 String 对象
        final var config = fastJsonConfig.getSerializeConfig();
        config.put(Long.class, ToStringSerializer.instance);
        config.put(Long.TYPE, ToStringSerializer.instance);

        // 转换器
        var converter = new FastJsonHttpMessageConverter();
        converter.setFastJsonConfig(fastJsonConfig);
        converters.add(converter);
    }

    @Override
    public void addArgumentResolvers(@NotNull List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(context.getBean(StreamModelProcessor.class));
        resolvers.add(context.getBean(WebSessionProcessor.class));
        resolvers.add(context.getBean(PageModelProcessor.class));
        resolvers.add(context.getBean(JsonModelProcessor.class));
    }

    /**
     * Spring 启动完成时调用
     *
     * @author xchao
     */
    public static class MiniServletContextAware implements ServletContextAware {
        public void setServletContext(@NotNull ServletContext context) {

        }
    }

    /**
     * Spring 启动完成时调用
     *
     * @author xchao
     */
    public static class MiniApplicationRunner implements ApplicationRunner {
        public void run(@NotNull ApplicationArguments arguments) {

        }
    }
}
