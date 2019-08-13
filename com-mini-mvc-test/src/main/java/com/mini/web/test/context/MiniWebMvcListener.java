package com.mini.web.test.context;

import com.mini.web.test.R;
import com.mini.web.test.service.InitService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Objects;

/**
 * 自定义监听器
 * @author xchao
 */
@Singleton
public class MiniWebMvcListener implements ServletContextListener {
    @Inject
    private InitService initService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Objects.requireNonNull(initService).queryAll().forEach(init -> {
            R.put(init.getId(), init.getValue()); //
        });
    }
}
