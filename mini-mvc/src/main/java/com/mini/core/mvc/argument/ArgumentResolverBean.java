package com.mini.core.mvc.argument;

import com.mini.core.mvc.annotation.Param;
import com.mini.core.mvc.interceptor.ActionInvocation;
import com.mini.core.mvc.support.config.Configures;
import com.mini.core.mvc.validation.ValidateUtil;
import com.mini.core.util.holder.ClassHolder;
import com.mini.core.util.holder.FieldHolder;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;

import javax.annotation.Nonnull;
import java.util.EventListener;
import java.util.Locale;
import java.util.function.Function;

import static java.util.Objects.nonNull;

public abstract class ArgumentResolverBean implements ArgumentResolver, EventListener {
    @NotNull
    private final Configures configures;

    protected ArgumentResolverBean(@NotNull Configures configures) {
        ArgumentResolverSupport.init(configures);
        this.configures = configures;
    }

    @Override
    public boolean supportParameter(MethodParameter parameter) {
        var h = ClassHolder.create(parameter.getParameterType());
        return nonNull(h.getAnnotation(Param.class));
    }

    @Override
    public final Object getValue(MethodParameter parameter, ActionInvocation invoke) {
        ClassHolder<?> holder = ClassHolder.create(parameter.getParameterType());
        final Object result = holder.getInstance();
        holder.getFields().values().stream().filter(FieldHolder::hasSetter).forEach(h -> {
            // 获取参数解析器
            final Function<String[], Object> function = ArgumentResolverSupport.getBeanFunc(h.getType());
            if (function == null) return;
            // 获取参数的值
            Object value = function.apply(getValue(h.getName(), invoke));
            h.setValue(result, value);
        });
        // 验证参数
        Locale locale = configures.getLocaleFactory().getLocal(invoke.getRequest());
        ValidateUtil.validate(locale, result);
        return result;
    }

    /**
     * 根据参数名称获取参数值
     *
     * @param name       参数名称
     * @param invocation Action 调用对象
     * @return 参数值
     */
    protected abstract String[] getValue(String name, ActionInvocation invocation);

    @Override
    public final int compareTo(@Nonnull ArgumentResolver o) {
        return this.hashCode() - o.hashCode();
    }
}
