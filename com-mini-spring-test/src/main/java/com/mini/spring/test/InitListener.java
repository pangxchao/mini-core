package com.mini.spring.test;

import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        System.out.println("=========InitListener==========");
    }
}
