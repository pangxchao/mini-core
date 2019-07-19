package com.mini;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.mini.util.MiniProperties;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Test {

    public interface ServletContext {
        String getValue();

        void setValue(String value);
    }

    public static class ConfigInfo {

        @Inject
        @Named("pro.txt1")
        private String string1;

        @Inject
        @Named("pro.txt2")
        private String string2;

        @Override
        public String toString() {
            return String.format("Config(%s, %s)", string1, string2);
        }
    }

    public static class ConfigModule implements Module {
        private final List<AbsConfig> configures = new ArrayList<>();
        private final ServletContext servletContext;

        public ConfigModule(ServletContext servletContext, List<AbsConfig> configures) {
            this.servletContext = servletContext;
            this.configures.addAll(configures);
        }

        @Override
        public void configure(Binder binder) {
            binder.bind(ServletContext.class).toInstance(servletContext);
            MiniProperties properties = new MiniProperties();
            properties.put("pro.txt1", "pro.txt1.val");
            properties.put("pro.txt2", "pro.txt2.val");
            properties.put("pro.txt3", "pro.txt3.val");
            properties.put("pro.txt4", "pro.txt4.val");
            Names.bindProperties(binder, properties);
            configures.forEach(binder::install);
        }
    }


    public static class ModelA implements Module {
        private ConfigInfo configInfo;

        public ModelA(ConfigInfo configInfo) {
            this.configInfo = configInfo;
        }

        @Override
        public void configure(Binder binder) {
            binder.bind(String.class).annotatedWith(Names.named("string1")).toInstance("string1");
            binder.bind(String.class).annotatedWith(Names.named("string2")).toInstance("string2");

            binder.requestInjection(configInfo);

        }
    }

    public static abstract class AbsConfig implements Module {
        @Inject
        private ServletContext servletContext;
        @Inject
        private ConfigInfo configure;

        public void configure(Binder binder) {

        }

        public synchronized final void onStartup() {
            Objects.requireNonNull(servletContext);
            Objects.requireNonNull(configure);
        }
    }

    public static class ConfigA extends AbsConfig {
        @Inject
        private ServletContext servletContext;
        @Inject
        private ConfigInfo configure;

        public void configure(Binder binder) {
            System.out.println(servletContext);
            System.out.println(configure);
        }
    }

    public static class ConfigB extends AbsConfig {
        @Inject
        private ServletContext servletContext;
        @Inject
        private ConfigInfo configure;

        public void configure(Binder binder) {
            System.out.println(servletContext);
            System.out.println(configure);
        }
    }

    public static void main(String[] args) {
        ServletContext servletContext = new ServletContext() {
            private String value = "123456";

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        };
        List<AbsConfig> configures = Arrays.asList(new ConfigA(), new ConfigB());
        ConfigModule module = new ConfigModule(servletContext, configures);
        Injector injector = Guice.createInjector(module);

        // 获取配置信息
        ConfigInfo configInfo = injector.getInstance(ConfigInfo.class);
        System.out.println(configInfo);

        // 调用默认配置信息注册
        configures.stream().map(c -> injector.getInstance(c.getClass()))
                .forEach(AbsConfig::onStartup);
    }
}
