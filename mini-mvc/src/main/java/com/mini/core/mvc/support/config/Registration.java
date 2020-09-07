package com.mini.core.mvc.support.config;

import javax.servlet.ServletContext;
import java.util.EventListener;

public interface Registration extends EventListener {
	void register(ServletContext context);
}
