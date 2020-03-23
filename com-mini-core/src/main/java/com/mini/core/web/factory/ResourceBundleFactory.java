package com.mini.core.web.factory;

import com.mini.core.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;
import java.util.EventListener;
import java.util.ResourceBundle;

/**
 * ResourceBundle 工厂
 * @author xchao
 */
public interface ResourceBundleFactory extends EventListener {
	@Nonnull
	ResourceBundle get(ActionInvocation invocation);
}
