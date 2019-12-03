package com.mini.web.argument.paging;

import com.mini.core.jdbc.model.Paging;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.web.argument.ArgumentResolver;
import com.mini.web.interceptor.ActionInvocation;

public abstract class ArgumentResolverPaging implements ArgumentResolver {

    @Override
    public boolean supportParameter(MiniParameter parameter) {
        return Paging.class == parameter.getType();
    }

    @Override
    public Object getValue(MiniParameter parameter, ActionInvocation invocation) {
        return new Paging().setPage(getPageValue(invocation))
                .setLimit(getLimitValue(invocation));
    }

    protected abstract int getPageValue(ActionInvocation invocation);

    protected abstract int getLimitValue(ActionInvocation invocation);
}
