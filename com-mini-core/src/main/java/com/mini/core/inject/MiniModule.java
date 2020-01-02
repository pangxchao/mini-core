package com.mini.core.inject;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.mini.core.inject.annotation.ComponentScan;
import com.mini.core.inject.annotation.PropertySource;
import com.mini.core.inject.annotation.PropertySources;
import com.mini.core.jdbc.transaction.TransInterceptor;
import com.mini.core.jdbc.transaction.TransactionEnable;
import com.mini.core.jdbc.transaction.Transactional;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.util.Properties;

import static com.google.inject.Key.get;
import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;
import static com.google.inject.name.Names.named;
import static com.mini.core.util.MiniProperties.createProperties;

public abstract class MiniModule implements Module {

	@Override
	public synchronized final void configure(Binder binder) {
		PropertySources sources = this.getPropertySources();
		Properties properties = createProperties(sources);
		PropertySource source = this.getPropertySource();
		properties.putAll(createProperties(source));
		Names.bindProperties(binder, properties);
		binder.requestInjection(this);
		this.onStartup(binder);

		// 开启事务
		if (this.getAnnotation(TransactionEnable.class) != null) {
			// 创建事务拦截器对象并注入属性
			TransInterceptor interceptor = new TransInterceptor();
			binder.requestInjection(interceptor);

			// 配置事务拦截器到指定方法上
			var element = annotatedWith(Transactional.class);
			binder.bindInterceptor(any(), element, interceptor);
		}
	}

	protected abstract void onStartup(Binder binder);

	/**
	 * 绑定字符串
	 * @param binder 绑定器
	 * @param name   绑定名称
	 * @param value  绑定值
	 */
	public final void bind(Binder binder, String name, String value) {
		binder.bind(get(String.class, named(name))).toInstance(value);
	}

	/**
	 * 绑定Long值
	 * @param binder 绑定器
	 * @param name   绑定名称
	 * @param value  绑定值
	 */
	public final void bind(Binder binder, String name, Long value) {
		binder.bind(get(Long.class, named(name))).toInstance(value);
	}

	/**
	 * 绑定 Integer 值
	 * @param binder 绑定器
	 * @param name   绑定名称
	 * @param value  绑定值
	 */
	public final void bind(Binder binder, String name, Integer value) {
		binder.bind(get(Integer.class, named(name))).toInstance(value);
	}

	/**
	 * 绑定Short值
	 * @param binder 绑定器
	 * @param name   绑定名称
	 * @param value  绑定值
	 */
	public final void bind(Binder binder, String name, Short value) {
		binder.bind(get(Short.class, named(name))).toInstance(value);
	}

	/**
	 * 绑定Byte值
	 * @param binder 绑定器
	 * @param name   绑定名称
	 * @param value  绑定值
	 */
	public final void bind(Binder binder, String name, Byte value) {
		binder.bind(get(Byte.class, named(name))).toInstance(value);
	}

	/**
	 * 绑定Double值
	 * @param binder 绑定器
	 * @param name   绑定名称
	 * @param value  绑定值
	 */
	public final void bind(Binder binder, String name, Double value) {
		binder.bind(get(Double.class, named(name))).toInstance(value);
	}

	/**
	 * 绑定Float值
	 * @param binder 绑定器
	 * @param name   绑定名称
	 * @param value  绑定值
	 */
	public final void bind(Binder binder, String name, Float value) {
		binder.bind(get(Float.class, named(name))).toInstance(value);
	}

	/**
	 * 绑定Boolean值
	 * @param binder 绑定器
	 * @param name   绑定名称
	 * @param value  绑定值
	 */
	public final void bind(Binder binder, String name, Boolean value) {
		binder.bind(get(Boolean.class, named(name))).toInstance(value);
	}

	/**
	 * 绑定Character值
	 * @param binder 绑定器
	 * @param name   绑定名称
	 * @param value  绑定值
	 */
	public final void bind(Binder binder, String name, Character value) {
		binder.bind(get(Character.class, named(name))).toInstance(value);
	}

	// 获取当前类指定注解信息
	public final <T extends Annotation> T getAnnotation(Class<T> clazz) {
		return this.getClass().getAnnotation(clazz);
	}

	// 获取当前类ComponentScan注解信息
	public final ComponentScan getComponentScan() {
		return getAnnotation(ComponentScan.class);
	}

	/**
	 * 获取当前类ComponentScan注解信息的值
	 * @return ComponentScan注解信息的值
	 */
	@Nonnull
	public final String[] getComponentScanValue() {
		ComponentScan scan = getComponentScan();
		if (scan == null) return new String[0];
		return scan.value();
	}

	// 获取当前类PropertySources注解信息
	public final PropertySources getPropertySources() {
		return getAnnotation(PropertySources.class);
	}

	// 获取当前类PropertySource注解信息
	public final PropertySource getPropertySource() {
		return getAnnotation(PropertySource.class);
	}
}
