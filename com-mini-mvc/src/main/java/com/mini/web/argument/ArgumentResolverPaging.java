package com.mini.web.argument;

import com.mini.jdbc.util.Paging;
import com.mini.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import static com.mini.util.TypeUtil.castToIntVal;

@Named
@Singleton
public final class ArgumentResolverPaging implements ArgumentResolver {
    @Override
    public Object value(@Nonnull String name, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) {
        String limit = invocation.getRequest().getParameter("limit");
        String page = invocation.getRequest().getParameter("page");
        return new Paging(castToIntVal(page), castToIntVal(limit));
    }

}
