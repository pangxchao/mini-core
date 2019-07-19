package com.mini.web.listener;

import com.mini.logger.Logger;
import com.mini.logger.LoggerFactory;
import com.mini.web.config.Configure;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Objects;

/**
 * 默认监听器
 * <p>处理静态资源版本</p>
 * @author xchao
 */
@Named
@Singleton
public final class MiniServletContextListener implements ServletContextListener {
    @Inject
    private Configure configure;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        Objects.requireNonNull(configure, "Configure can not be null");
        Logger logger = LoggerFactory.getLogger(this.getClass());
        // 转出注册的Action代理对象
        for (String key : configure.getInvocationProxy().keySet()) {
            logger.debug("Register Action: " + key);
        }
    }
}
