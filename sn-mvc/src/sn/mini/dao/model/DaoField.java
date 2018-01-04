/**
 * Created the sn.mini.dao.model.DaoField.java
 * @created 2017年11月2日 下午4:23:52
 * @version 1.0.0
 */
package sn.mini.dao.model;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Optional;

import sn.mini.dao.annotaion.Binding;
import sn.mini.util.lang.StringUtil;

/**
 * sn.mini.dao.model.DaoField.java 
 * @author XChao
 */
public final class DaoField {
	private final Optional<PropertyDescriptor> descriptor;
	private final Optional<Binding> binding;

	public DaoField(PropertyDescriptor descriptor, Binding binding) {
		this.descriptor = Optional.ofNullable(descriptor);
		this.binding = Optional.ofNullable(binding);
	}

	public String getName() {
		return descriptor.map(v -> v.getName()).orElseThrow(() -> {
			return new RuntimeException("PropertyDescriptor cannot be null. ");
		});
	}

	public boolean isBinding() {
		return this.binding.isPresent();
	}

	public boolean isPrimary() {
		return binding.map(v -> v.des()).map(v -> ((v & 2) == 2)).orElse(false);
	}

	public boolean isForeign() {
		return binding.map(v -> v.des()).map(v -> ((v & 4) == 4)).orElse(false);
	}

	public String getDbName() {
		return binding.map(v -> v.value()).orElse(StringUtil.toDBName(getName()));
	}

	public Method getReadMethod() {
		return descriptor.map(v -> v.getReadMethod()).orElse(null);
	}

	public Method getWriteMethod() {
		return descriptor.map(v -> v.getWriteMethod()).orElse(null);
	}

	public Class<?> getType() {
		return descriptor.map(v -> v.getPropertyType()).orElseThrow(() -> {
			return new RuntimeException("PropertyDescriptor cannot be null. ");
		});
	}

	public Object getValue(Object instance) throws Exception {
		Method method = this.getReadMethod();
		return method == null ? null : method.invoke(instance);
	}

	public void setValue(Object instance, Object value) throws Exception {
		Method method = this.getWriteMethod();
		if(method != null) {
			method.invoke(instance, value);
		}
	}
}
