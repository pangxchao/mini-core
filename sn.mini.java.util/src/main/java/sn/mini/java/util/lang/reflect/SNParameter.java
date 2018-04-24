/**
 * Created the sn.mini.util.lang.reflect.MParameter.java
 * @created 2017年9月1日 上午11:03:58
 * @version 1.0.0
 */
package sn.mini.java.util.lang.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

/**
 * sn.mini.util.lang.reflect.MParameter.java
 * @author XChao
 */
public class SNParameter {
	private final Parameter paramter;
	private final String name;
	private Object value;

	public SNParameter(Parameter paramter, String name) {
		this.paramter = paramter;
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean equals(Object obj) {
		return this.paramter.equals(obj);
	}

	public int hashCode() {
		return this.paramter.hashCode();
	}

	public boolean isNamePresent() {
		return this.paramter.isNamePresent();
	}

	public String toString() {
		return this.paramter.toString();
	}

	public Executable getDeclaringExecutable() {
		return this.paramter.getDeclaringExecutable();
	}

	public int getModifiers() {
		return this.paramter.getModifiers();
	}

	public String getName() {
		return this.name;
	}

	public Type getParameterizedType() {
		return this.paramter.getParameterizedType();
	}

	public Class<?> getType() {
		return this.paramter.getType();
	}

	public AnnotatedType getAnnotatedType() {
		return this.paramter.getAnnotatedType();
	}

	public boolean isImplicit() {
		return this.paramter.isImplicit();
	}

	public boolean isSynthetic() {
		return this.paramter.isSynthetic();
	}

	public boolean isVarArgs() {
		return this.paramter.isVarArgs();
	}

	public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		return this.paramter.getAnnotation(annotationClass);
	}

	public <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {
		return this.paramter.getAnnotationsByType(annotationClass);
	}

	public Annotation[] getDeclaredAnnotations() {
		return this.paramter.getDeclaredAnnotations();
	}

	public <T extends Annotation> T getDeclaredAnnotation(Class<T> annotationClass) {
		return this.paramter.getDeclaredAnnotation(annotationClass);
	}

	public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> annotationClass) {
		return this.paramter.getDeclaredAnnotationsByType(annotationClass);
	}

	public Annotation[] getAnnotations() {
		return this.paramter.getAnnotations();
	}
}
