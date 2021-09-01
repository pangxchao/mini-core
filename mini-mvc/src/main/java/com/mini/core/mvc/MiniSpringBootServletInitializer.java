package com.mini.core.mvc;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.mini.core.mvc.converters.*;
import com.mini.core.mvc.enums.NumberToIEnumConvertFactory;
import com.mini.core.mvc.enums.ValueToEnumArgumentResolver;
import com.mini.core.mvc.enums.ValueToIEnumConvertFactory;
import com.mini.core.mvc.processor.JsonModelProcessor;
import com.mini.core.mvc.processor.PageModelProcessor;
import com.mini.core.mvc.processor.StreamModelProcessor;
import com.mini.core.mvc.processor.WebSessionProcessor;
import com.mini.core.mvc.util.Jackson;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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
    @ConditionalOnMissingBean(value = JsonModelProcessor.class)
    public JsonModelProcessor jsonModelProcessor() {
        return new JsonModelProcessor();
    }

    @Bean
    @ConditionalOnMissingBean(value = PageModelProcessor.class)
    public PageModelProcessor pageModelProcessor() {
        return new PageModelProcessor();
    }

    @Bean
    @ConditionalOnMissingBean(value = StreamModelProcessor.class)
    public StreamModelProcessor streamModelProcessor() {
        return new StreamModelProcessor();
    }

    @Bean
    @ConditionalOnMissingBean(value = WebSessionProcessor.class)
    public WebSessionProcessor webSessionProcessor() {
        return new WebSessionProcessor();
    }

    @Bean
    @ConditionalOnMissingBean(value = ErrorAttributes.class)
    public MiniDefaultErrorAttributes miniDefaultErrorAttributes(MessageSource messageSource) {
        return new MiniDefaultErrorAttributes(messageSource);
    }

    @Bean
    @ConditionalOnMissingBean(value = StringToInstantConverter.class)
    public StringToInstantConverter stringToInstantConverter() {
        return new StringToInstantConverter();
    }

    @Bean
    @ConditionalOnMissingBean(value = StringToJavaUtilDateConverter.class)
    public StringToJavaUtilDateConverter stringToJavaUtilDateConverter() {
        return new StringToJavaUtilDateConverter();
    }

    @Bean
    @ConditionalOnMissingBean(value = StringToLocalDateTimeConverter.class)
    public StringToLocalDateTimeConverter stringToLocalDateTimeConverter() {
        return new StringToLocalDateTimeConverter();
    }

    @Bean
    @ConditionalOnMissingBean(value = StringToLocalDateConverter.class)
    public StringToLocalDateConverter stringToLocalDateConverter() {
        return new StringToLocalDateConverter();
    }

    @Bean
    @ConditionalOnMissingBean(value = StringToLocalTimeConverter.class)
    public StringToLocalTimeConverter stringToLocalTimeConverter() {
        return new StringToLocalTimeConverter();
    }

    @Bean
    @ConditionalOnMissingBean(value = StringToJavaSqlTimestampConverter.class)
    public StringToJavaSqlTimestampConverter stringToJavaSqlTimestampConverter() {
        return new StringToJavaSqlTimestampConverter();
    }

    @Bean
    @ConditionalOnMissingBean(value = StringToJavaSqlDateConverter.class)
    public StringToJavaSqlDateConverter stringToJavaSqlDateConverter() {
        return new StringToJavaSqlDateConverter();
    }

    @Bean
    @ConditionalOnMissingBean(value = StringToJavaSqlTimeConverter.class)
    public StringToJavaSqlTimeConverter stringToJavaSqlTimeConverter() {
        return new StringToJavaSqlTimeConverter();
    }

    @Bean
    @ConditionalOnMissingBean(value = NumberToIEnumConvertFactory.class)
    public NumberToIEnumConvertFactory numberToIEnumConvertFactory() {
        return new NumberToIEnumConvertFactory();
    }

    @Bean
    @ConditionalOnMissingBean(value = ValueToIEnumConvertFactory.class)
    public ValueToIEnumConvertFactory valueToIEnumConvertFactory() {
        return new ValueToIEnumConvertFactory();
    }

    @Bean
    @ConditionalOnMissingBean(value = ValueToEnumArgumentResolver.class)
    public ValueToEnumArgumentResolver valueToEnumArgumentResolver() {
        return new ValueToEnumArgumentResolver();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(value = JsonMapper.class)
    public JsonMapper jsonMapper() throws RuntimeException, Error {
        return Jackson.getJsonMapper();
    }

    @Override
    public void addArgumentResolvers(@NotNull List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(context.getBean(ValueToEnumArgumentResolver.class));
        resolvers.add(context.getBean(StreamModelProcessor.class));
        resolvers.add(context.getBean(WebSessionProcessor.class));
        resolvers.add(context.getBean(PageModelProcessor.class));
        resolvers.add(context.getBean(JsonModelProcessor.class));
    }

    @Override
    public void addFormatters(@NotNull final FormatterRegistry registry) {
        registry.addConverterFactory(context.getBean(NumberToIEnumConvertFactory.class));
        registry.addConverterFactory(context.getBean(ValueToIEnumConvertFactory.class));
        registry.addConverter(context.getBean(StringToJavaSqlTimestampConverter.class));
        registry.addConverter(context.getBean(StringToLocalDateTimeConverter.class));
        registry.addConverter(context.getBean(StringToJavaUtilDateConverter.class));
        registry.addConverter(context.getBean(StringToJavaSqlTimeConverter.class));
        registry.addConverter(context.getBean(StringToJavaSqlDateConverter.class));
        registry.addConverter(context.getBean(StringToLocalDateConverter.class));
        registry.addConverter(context.getBean(StringToLocalTimeConverter.class));
        registry.addConverter(context.getBean(StringToInstantConverter.class));
        registry.addConverter(context.getBean(StringToInstantConverter.class));
    }
}
