package com.mini.core.web.factory;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.util.EventListener;
import java.util.ResourceBundle;

/**
 * ResourceBundle 工厂
 * @author xchao
 */
public interface ResourceBundleFactory extends EventListener {
	@Nonnull
	ResourceBundle get(@Nonnull HttpServletRequest request);
}
