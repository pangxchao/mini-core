package com.mini.core.web.argument;

import com.mini.core.holder.ClassHolder;
import com.mini.core.holder.FieldHolder;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.interceptor.ActionInvocation;

import javax.annotation.Nullable;
import java.util.EventListener;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.mini.core.web.argument.ArgumentResolverSupport.getBeanFunc;
import static java.lang.Class.forName;
import static java.util.Optional.ofNullable;

public abstract class ArgumentResolverBean implements ArgumentResolver, EventListener {
	private static Map<Class<?>, ArgumentResolver> MAP = new ConcurrentHashMap<>();
	private static final String $RESOLVER$ = "_$$$RESOLVER$$$";
	
	protected ArgumentResolverBean(String dateTimeFormat, String dateFormat, String timeFormat) {
		ArgumentResolverSupport.init(dateTimeFormat, dateFormat, timeFormat);
	}
	
	@Override
	public boolean supportParameter(MiniParameter parameter) {
		return ofNullable(create(parameter.getType()))
			.map(v -> v.supportParameter(parameter))
			.orElseGet(() -> {
				Class<?> type = parameter.getType();
				var holder = ClassHolder.create(type);
				return holder.hasParam();
			});
	}
	
	@Override
	public final Object getValue(MiniParameter parameter, ActionInvocation invoke) {
		return ofNullable(create(parameter.getType()))
			.filter(v -> v.supportParameter(parameter))
			.map(v -> v.getValue(parameter, invoke)).orElseGet(() -> {
				var holder = ClassHolder.create(parameter.getType());
				Object result = holder.createInstance();
				holder.fields().stream().filter(FieldHolder::hasSetter).forEach(h -> {
					Object value = Optional.ofNullable(getBeanFunc(h.getFieldType()))
						.map(func -> func.apply(getValue(h.getFieldName(), invoke)))
						.orElse(null);
					h.setValue(result, value);
				});
				return result;
			});
	}
	
	/**
	 * 根据参数名称获取参数值
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
				ofNullable(mType).filter(ArgumentResolver.class::isAssignableFrom)
					.orElseThrow(NoClassDefFoundError::new);
				var mClass = mType.asSubclass(ArgumentResolver.class);
				return mClass.getDeclaredConstructor().newInstance();
			} catch (ReflectiveOperationException | NoClassDefFoundError e) {
				return null;
			}
		});
	}
}
