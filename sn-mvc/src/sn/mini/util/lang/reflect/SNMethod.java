/**
 * Created the sn.mini.util.lang.reflect.MMethod.java
 * @created 2017年9月5日 下午5:18:14
 * @version 1.0.0
 */
package sn.mini.util.lang.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import sn.mini.util.lang.MethodUtil;

/**
 * sn.mini.util.lang.reflect.MMethod.java
 * @author XChao
 */
public final class SNMethod {
	private final Map<String, SNParameter> maps = new ConcurrentHashMap<>();
	private final SNParameter[] parameters;
	private final Method method;

	public SNMethod(Method method) {
		this.parameters = MethodUtil.getSNParameter(method);
		for (SNParameter mParameter : parameters) {
			this.maps.put(mParameter.getName(), mParameter);
		}
		this.method = method;
	}

	public SNParameter[] getParameters() {
		return this.parameters;
	}

	public SNParameter getParameter(String name) {
		if(this.maps.containsKey(name)) {
			return this.maps.get(name);
		}
		throw new RuntimeException("Parameter name ‘" + name + "’ not found.");
	}

	public SNParameter getParameter(int index) {
		if(index >= 0 && index < this.parameters.length) {
			return parameters[index];
		}
		throw new ArrayIndexOutOfBoundsException(index);
	}

	public Object getParameterValue(String name) {
		return this.getParameter(name).getValue();
	}

	public Object getParameterValue(int index) {
		return this.getParameter(index).getValue();
	}

	public <T> T getParameterValue(String name, Class<T> clazz) {
		return clazz.cast(this.getParameterValue(name));
	}

	public <T> T getParameterValue(int index, Class<T> clazz) {
		return clazz.cast(this.getParameterValue(index));
	}

	public void setParameterValue(String name, Object value) {
		this.getParameter(name).setValue(value);
	}

	public void setParameterValue(int index, Object value) {
		this.getParameter(index).setValue(value);
	}

	public Object[] getParameterValues() {
		List<Object> values = new ArrayList<>();
		for (SNParameter mParameter : parameters) {
			values.add(mParameter.getValue());
		}
		return values.toArray();
	}

	public Class<?> getDeclaringClass() {
		return this.method.getDeclaringClass();
	}

	public String getName() {
		return this.method.getName();
	}

	public int getModifiers() {
		return this.method.getModifiers();
	}

	public TypeVariable<Method>[] getTypeParameters() {
		return this.method.getTypeParameters();
	}

	public Class<?> getReturnType() {
		return this.method.getReturnType();
	}

	public Type getGenericReturnType() {
		return this.method.getGenericReturnType();
	}

	public Class<?>[] getParameterTypes() {
		return this.method.getParameterTypes();
	}

	public int getParameterCount() {
		return this.method.getParameterCount();
	}

	public Type[] getGenericParameterTypes() {
		return this.method.getGenericParameterTypes();
	}

	public Class<?>[] getExceptionTypes() {
		return this.method.getExceptionTypes();
	}

	public Type[] getGenericExceptionTypes() {
		return this.method.getGenericExceptionTypes();
	}

	public boolean equals(Object obj) {
		return this.method.equals(obj);
	}

	public int hashCode() {
		return this.method.hashCode();
	}

	public String toString() {
		return this.method.toString();
	}

	public String toGenericString() {
		return this.method.toGenericString();
	}

	public Object invoke(Object obj, Object... args) {
		try {
			return this.method.invoke(obj, args);
		} catch (InvocationTargetException exception) {
			Throwable throwable = exception.getTargetException();
			throw new RuntimeException(throwable.getMessage(), throwable);
		} catch (Throwable throwable) {
			throw new RuntimeException(throwable.getMessage(), throwable);
		}
	}

	public Object invoke(Object obj) {
		return this.invoke(obj, this.getParameterValues());
	}

	public boolean isBridge() {
		return this.method.isBridge();
	}

	public boolean isVarArgs() {
		return this.method.isVarArgs();
	}

	public boolean isSynthetic() {
		return this.method.isSynthetic();
	}

	public boolean isDefault() {
		return this.method.isDefault();
	}

	public Object getDefaultValue() {
		return this.method.getDefaultValue();
	}

	public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		return this.method.getAnnotation(annotationClass);
	}

	public Annotation[] getDeclaredAnnotations() {
		return this.method.getDeclaredAnnotations();
	}

	public Annotation[][] getParameterAnnotations() {
		return this.method.getParameterAnnotations();
	}

	public AnnotatedType getAnnotatedReturnType() {
		return this.method.getAnnotatedReceiverType();
	}

}
