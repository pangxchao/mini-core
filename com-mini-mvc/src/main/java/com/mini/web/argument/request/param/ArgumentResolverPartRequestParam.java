package com.mini.web.argument.request.param;

import com.mini.core.inject.annotation.Associated;
import com.mini.core.util.StringUtil;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.web.argument.ArgumentResolver;
import com.mini.web.argument.annotation.RequestParam;
import com.mini.web.argument.annotation.RequestUri;
import com.mini.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;

import static java.util.Optional.ofNullable;

@Named
@Singleton
public final class ArgumentResolverPartRequestParam implements ArgumentResolver {

    @Override
    public boolean supportParameter(MiniParameter parameter) {
        if (parameter.getAnnotation(RequestParam.class) == null) {
            return false;
        }
        return Part.class == parameter.getType();
    }

    /**
     * 获取参数名称
     * @param parameter 参数对象
     * @return 参数名称
     */
    @Nonnull
    private String getParameterName(MiniParameter parameter) {
        RequestParam param = parameter.getAnnotation(RequestParam.class);
        if (param == null || StringUtil.isBlank(param.value())) {
            return parameter.getName();
        }
        return param.value();
    }

    @Override
    public Object getValue(MiniParameter parameter, ActionInvocation invocation) {
        try {
            String name = this.getParameterName(parameter);
            return ofNullable(invocation.getRequest().getPart(name))
                    .filter(part -> part.getSize() > 0)
                    .orElse(null);
        } catch (IOException | ServletException ignored) {}
        return null;
    }
}
