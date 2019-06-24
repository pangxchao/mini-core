package com.mini.inject;

import com.google.inject.Module;
import com.google.inject.*;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.AnnotatedConstantBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.matcher.Matcher;
import com.google.inject.name.Names;
import com.google.inject.spi.Message;
import com.google.inject.spi.ProvisionListener;
import com.google.inject.spi.TypeConverter;
import com.google.inject.spi.TypeListener;
import com.mini.inject.annotation.Implemented;
import com.mini.inject.annotation.Scanning;
import com.mini.logger.Logger;
import com.mini.logger.LoggerFactory;
import com.mini.util.ClassUtil;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Properties;

import static com.google.inject.name.Names.named;

public class MiniModule extends AbstractModule {
    private static final Logger logger = LoggerFactory.getLogger(MiniModule.class);

    /** 自定义绑定 */
    @Override
    protected final synchronized void configure() {
        try {
            Scanning scanning = getClass().getAnnotation(Scanning.class);
            String[] packages = scanning == null ? new String[0] : scanning.value();
            ClassUtil.scanner(packages, Implemented.class).forEach(clazz -> {
                // 获取 Implemented， 如果为空，则不处理
                Implemented implemented = clazz.getAnnotation(Implemented.class);
                if (implemented == null) return;

                // 如果当前类不是 Implemented.value 的子类，不处理
                if (!implemented.value().isAssignableFrom(clazz)) {
                    return;
                }

                //  绑定实现名称
                bind(implemented.value(), implemented.name(), clazz);
            });
            //  调用子类初始化
            this.configures(binder());
        } catch (Exception | Error e) {
            logger.error("Inject Error!", e);
        }
    }

    //  绑定实现名称
    private <T> void bind(Class<T> superClass, String name, Class<?> clazz) {
        Class<? extends T> c = clazz.asSubclass(superClass);
        bind(superClass).annotatedWith(named(name)).to(c);
    }

    /** 自定义绑定 */
    protected void configures(Binder binder) throws Exception, Error {}

    /**
     * 绑定 Properties 配置文件信息
     * @param properties Properties文件
     */
    protected final void bindProperties(Properties properties) {
        Names.bindProperties(binder(), properties);
    }

    /**
     * 绑定实例
     * @param binder 绑定器
     * @param t      实例
     * @return {@Code this}
     */
    protected final <T> T requestInjection(Binder binder, T t) {
        binder.requestInjection(t);
        return t;
    }

    @Override
    protected final Binder binder() {
        return super.binder();
    }

    @Override
    protected final void bindScope(Class<? extends Annotation> scopeAnnotation, Scope scope) {
        super.bindScope(scopeAnnotation, scope);
    }

    @Override
    protected final <T> LinkedBindingBuilder<T> bind(Key<T> key) {
        return super.bind(key);
    }

    @Override
    protected final <T> AnnotatedBindingBuilder<T> bind(TypeLiteral<T> typeLiteral) {
        return super.bind(typeLiteral);
    }

    @Override
    protected final <T> AnnotatedBindingBuilder<T> bind(Class<T> clazz) {
        return super.bind(clazz);
    }

    @Override
    protected final AnnotatedConstantBindingBuilder bindConstant() {
        return super.bindConstant();
    }

    @Override
    protected final void install(Module module) {
        super.install(module);
    }

    @Override
    protected final void addError(String message, Object... arguments) {
        super.addError(message, arguments);
    }

    @Override
    protected final void addError(Throwable t) {
        super.addError(t);
    }

    @Override
    protected final void addError(Message message) {
        super.addError(message);
    }

    @Override
    protected final void requestInjection(Object instance) {
        super.requestInjection(instance);
    }

    @Override
    protected final void requestStaticInjection(Class<?>... types) {
        super.requestStaticInjection(types);
    }

    @Override
    protected final void bindInterceptor(Matcher<? super Class<?>> classMatcher, Matcher<? super Method> methodMatcher, MethodInterceptor... interceptors) {
        super.bindInterceptor(classMatcher, methodMatcher, interceptors);
    }

    @Override
    protected final void requireBinding(Key<?> key) {
        super.requireBinding(key);
    }

    @Override
    protected final void requireBinding(Class<?> type) {
        super.requireBinding(type);
    }

    @Override
    protected final <T> Provider<T> getProvider(Key<T> key) {
        return super.getProvider(key);
    }

    @Override
    protected final <T> Provider<T> getProvider(Class<T> type) {
        return super.getProvider(type);
    }

    @Override
    protected final void convertToTypes(Matcher<? super TypeLiteral<?>> typeMatcher, TypeConverter converter) {
        super.convertToTypes(typeMatcher, converter);
    }

    @Override
    protected final Stage currentStage() {
        return super.currentStage();
    }

    @Override
    protected final <T> MembersInjector<T> getMembersInjector(Class<T> type) {
        return super.getMembersInjector(type);
    }

    @Override
    protected final <T> MembersInjector<T> getMembersInjector(TypeLiteral<T> type) {
        return super.getMembersInjector(type);
    }

    @Override
    protected final void bindListener(Matcher<? super TypeLiteral<?>> typeMatcher, TypeListener listener) {
        super.bindListener(typeMatcher, listener);
    }

    @Override
    protected final void bindListener(Matcher<? super Binding<?>> bindingMatcher, ProvisionListener... listener) {
        super.bindListener(bindingMatcher, listener);
    }
}
