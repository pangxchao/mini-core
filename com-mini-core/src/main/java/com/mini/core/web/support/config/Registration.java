package com.mini.core.web.support.config;

import javax.servlet.ServletContext;
import java.util.EventListener;

public interface Registration extends EventListener {
    void register(ServletContext context);
}
