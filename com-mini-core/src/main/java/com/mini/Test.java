package com.mini;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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

        @Override
        public void say() {
            a.say();
        }
    }

    @Configuration
    @ComponentScan("com.mini")
    public static class Config {

    }

    @Configuration
    @ComponentScan("com.mini")
    public static class Config1 {

    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        B b = context.getBean(BImpl.class);
        B b1 = context.getBean(BImpl.class);
        System.out.println(b);
        System.out.println(b1);
    }
}
