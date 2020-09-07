package com.mini.core.web.argument;

import com.mini.core.util.holder.ClassHolder;
import com.mini.core.util.holder.FieldHolder;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.annotation.Param;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.support.config.Configures;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.EventListener;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.mini.core.validation.ValidationUtil.validate;
import static java.lang.Class.forName;
import static java.util.Objects.nonNull;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

public abstract class ArgumentResolverBean implements ArgumentResolver, EventListener {
    private static final Map<Class<?>, ArgumentResolver> MAP = new ConcurrentHashMap<>();
    private static final String $RESOLVER$ = "_$$$RESOLVER$$$";

    @Inject
    private Configures configures;

    protected ArgumentResolverBean(Configures configures) {
        ArgumentResolverSupport.init(configures);
    }

    @Override
    public boolean supportParameter(MiniParameter parameter) {
        return ofNullable(create(parameter.getType()))
                .map(v -> v.supportParameter(parameter))
                .orElseGet(() -> {
                    var h = ClassHolder.create(parameter.getType());
                    return nonNull(h.getAnnotation(Param.class));
                });
    }

    @Override
    public final Object getValue(MiniParameter parameter, ActionInvocation invoke) {
        return ofNullable(ArgumentResolverBean.create(parameter.getType()))
                .filter(v -> v.supportParameter(parameter))
                .map(v -> v.getValue(parameter, invoke)).orElseGet(() -> {
                    var holder = ClassHolder.create(parameter.getType());
                    Object result = holder.getInstance();
                    holder.getFields().values().stream().filter(FieldHolder::hasSetter).forEach(h -> {
                        // 获取参数值
                        Object value = Optional.ofNullable(ArgumentResolverSupport.getBeanFunc(h.getType()))
                                .map(func -> func.apply(getValue(h.getName(), invoke)))
                                .orElse(null);
                        // 验证参数信息
                        validate(configures.getInjector(), h.getField(), value);
                        // 设置参数信息
                        h.setValue(result, value);
                    });
                    return result;
                });
    }

    /**
     * 根据参数名称获取参数值
     *
     * @param name       参数名称
     * @param invocation Action 调用对象
     * @return 参数值
     */
    protected abstract String[] getValue(String name, ActionInvocation invocation);

    @Nullable
    private static ArgumentResolver create(Class<?> type) {
        if (ArgumentResolverBean.MAP.containsKey(type)) {
            return ArgumentResolverBean.MAP.get(type);
        }
        return ArgumentResolverBean.MAP.computeIfAbsent(type, key -> {
            try {
                Class<?> mType = forName(type.getCanonicalName() + $RESOLVER$);
                of(mType).filter(ArgumentResolver.class::isAssignableFrom)
                        .orElseThrow(NoClassDefFoundError::new);
                var mClass = mType.asSubclass(ArgumentResolver.class);
                return mClass.getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException | NoClassDefFoundError e) {
                return null;
            }
        });
    }

    @Override
    public final int compareTo(@Nonnull ArgumentResolver o) {
        return this.hashCode() - o.hashCode();
    }
}
