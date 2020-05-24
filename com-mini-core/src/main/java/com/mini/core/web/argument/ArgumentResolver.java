package com.mini.core.web.argument;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;

public interface ArgumentResolver extends Comparable<ArgumentResolver> {
	
	boolean supportParameter(MiniParameter parameter);
	
	Object getValue(MiniParameter parameter, ActionInvocation invocation);
	
	@Override
	default int compareTo(@Nonnull ArgumentResolver o) {
		return this.hashCode() - o.hashCode();
	}
}
