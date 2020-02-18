package com.mini.core.web.argument;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.interceptor.ActionInvocation;

public interface ArgumentResolver {
	
	boolean supportParameter(MiniParameter parameter);
	
	Object getValue(MiniParameter parameter, ActionInvocation invocation);
}
