package com.mini.web.argument;

import com.mini.web.config.Configure;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.lang.reflect.Constructor;
import java.util.Arrays;

import static java.util.Optional.ofNullable;

@Named
@Singleton
public final class ArgumentResolverBean implements ArgumentResolver {
    private Configure configure;

    @Inject
    public void setConfigure(Configure configure) {
        this.configure = configure;
    }

    @Override
    public final Object value(@Nonnull String name, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        try {
            Constructor<?> constructor = type.getConstructor();
            if (constructor == null) return null;

            Object instance = constructor.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            Arrays.stream(beanInfo.getPropertyDescriptors()).forEach(descriptor -> {
                ArgumentResolver resolve = configure.getResolver(descriptor.getPropertyType());
                ofNullable(resolve).flatMap(r -> ofNullable(descriptor.getWriteMethod())).ifPresent(m -> {
                    try {
                        m.invoke(instance, resolve.value(descriptor.getName(), descriptor //
                                .getPropertyType(), request, response));
                    } catch (Exception | Error ignored) {}
                });
            });

            return instance;
        } catch (Exception | Error ignored) {}
        return null;
    }


}
