package com.mini.core.mvc.argument;

import com.mini.core.mvc.interceptor.ActionInvocation;
import org.springframework.core.MethodParameter;

import javax.annotation.Nonnull;

public interface ArgumentResolver extends Comparable<ArgumentResolver> {

    boolean supportParameter(MethodParameter parameter);

    Object getValue(MethodParameter parameter, ActionInvocation invocation);

    @Override
    default int compareTo(@Nonnull ArgumentResolver o) {
        return this.hashCode() - o.hashCode();
    }
}
