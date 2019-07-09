package com.mini;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

public class Test {

    public interface A {
        void say();
    }

    @Named("a")
    @Singleton
    public static class AImpl implements A {
        @Override
        public void say() {
            System.out.println("A");
        }
    }


    public interface B {
        void say();
    }

    @Singleton
    @Named("b")
    public static class BImpl implements B {


        private A a;

        private String name;

        /**
         * The value of aImpl
         * @param aa The value of aImpl
         * @return {@Code this}
         */
        @Inject
        @Named("a")
        public BImpl setA(A aa) {
            this.a = aa;
            return this;
        }

        /**
         * The value of name
         * @param name The value of name
         * @return {@Code this}
         */
        @Inject
        @Named("name")
        public BImpl setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public void say() {
            a.say();
        }
    }


    @Named
    @Singleton
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @PropertySource("classpath:mini-application.properties")
    public final static class Config {

        @Inject
        private ApplicationContext context;

        @Value("${mini.mvc.view.prefix}")
        private String prefix;

        // 访问路径
        @Value("${mini.http.servlet.urls}")
        private String[] urlPatterns;

        @Value("${mini.http.multipart.max-file-size}")
        private long maxFileSize;

        @Bean
        @Named("name")
        public String getName() {
            System.out.println("-----------");
            return "Name";
        }


        public ApplicationContext getContext() {
            return context;
        }
    }

    @Configuration
    @ComponentScan("com.mini")
    public static class Config1 {

    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class, Config1.class);

        B b = context.getBean(BImpl.class);
        B b1 = context.getBean(BImpl.class);
        Config c = context.getBean(Config.class);
        System.out.println(b);
        System.out.println(b1);
    }
}
