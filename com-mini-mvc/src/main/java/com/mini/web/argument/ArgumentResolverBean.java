package com.mini.web.argument;

import static com.mini.logger.LoggerFactory.getLogger;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import com.mini.logger.Logger;
import com.mini.web.config.Configure;
import com.mini.web.config.Configure.UnregisteredException;
import com.mini.web.interceptor.ActionInvocation;
import com.mini.web.util.IStatus;

public abstract class ArgumentResolverBean implements ArgumentResolver {
    private static final Logger logger = getLogger(Configure.class);
    private Configure configure;

    @Inject
    public final void setConfigure(Configure configure) {
        this.configure = configure;
    }

    /**
     * 根据属性名称和目标类型获取属性值
     * @param name 属性名称
     * @param type 转换成的类型
     * @return 属性值
     */
    protected final Object getParameter(@Nonnull String name, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) {
        try {
            ArgumentResolver resolver = configure.getResolver(type);
            return resolver.value(name, type, invocation);
        } catch (NumberFormatException e) {
            return (byte) 0;
        } catch (UnregisteredException e) {
            logger.warn(e.getMessage());
        } catch (Exception ignored) {
        }
        return null;
    }

    @Override
    public final Object value(@Nonnull String _name, @Nonnull Class<?> _type, @Nonnull ActionInvocation invocation) throws Exception {
        return value(new ArgumentResolverBean.Transform() {
            public <T> T get(String key, Class<T> clazz) throws RuntimeException {
                return Optional.ofNullable(getParameter(key, clazz, invocation))
                        .map(v -> (clazz.isAssignableFrom(v.getClass()) ? clazz.cast(v) : null))
                        .orElse(null);
            }
        }, _name, _type, invocation);
    }

    protected abstract Object value(@Nonnull Transform transform, @Nonnull String name, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) throws Exception;

    protected interface Transform extends IStatus {
        <T> T get(String name, Class<T> clazz);

        default String getString(String name) {
            return get(name, String.class);
        }

        default Long getLong(String name) {
            return get(name, Long.class);
        }

        default long getLongVal(String name) {
            Long v = this.getLong(name);
            return v == null ? 0 : v;
        }

        default Integer getInteger(String name) {
            return get(name, Integer.class);
        }

        default int getIntVal(String name) {
            Integer v = getInteger(name);
            return v == null ? 0 : v;
        }

        default Short getShort(String name) {
            return get(name, Short.class);
        }

        default short getShortVal(String name) {
            Short v = this.getShort(name);
            return v == null ? 0 : v;
        }

        default Byte getByte(String name) {
            return get(name, Byte.class);
        }

        default byte getByteVal(String name) {
            Byte v = this.getByte(name);
            return v == null ? 0 : v;
        }

        default Double getDouble(String name) {
            return get(name, Double.class);
        }

        default double getDoubleVal(String name) {
            Double v = this.getDouble(name);
            return v == null ? 0 : v;
        }

        default Float getFloat(String name) {
            return get(name, Float.class);
        }

        default float getFloatVal(String name) {
            Float v = this.getFloat(name);
            return v == null ? 0 : v;
        }

        default Boolean getBoolean(String name) {
            return get(name, Boolean.class);
        }

        default boolean getBooleanVal(String name) {
            Boolean v = this.getBoolean(name);
            return v == null ? false : v;
        }
    }
}
