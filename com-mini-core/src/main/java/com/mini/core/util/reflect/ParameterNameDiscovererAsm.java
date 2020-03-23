package com.mini.core.util.reflect;

import com.mini.core.thread.RunnableLinkedBlockingQueue;
import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Objects;

import static java.lang.ClassLoader.getSystemClassLoader;
import static java.util.Comparator.comparingInt;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.objectweb.asm.ClassReader.EXPAND_FRAMES;
import static org.slf4j.LoggerFactory.getLogger;

public class ParameterNameDiscovererAsm implements ParameterNameDiscoverer, Serializable {
	private static final Logger log = getLogger(ParameterNameDiscovererAsm.class);
	private static final long serialVersionUID = 6718496659106769030L;
	private static final String INIT_METHOD = "<init>";
	
	@Nonnull
	@Override
	public String[] getParameterNames(Method method) {
		Class<?>[] parameterTypes = method.getParameterTypes();
		if (parameterTypes.length == 0) return new String[0];
		
		try (InputStream stream = getClassAsStream(method.getDeclaringClass())) {
			String descriptor = Type.getMethodDescriptor(method);
			return getParameterNames(stream, method.getName(), descriptor);
		} catch (Exception | Error e) {
			log.error("ERROR!", e);
		}
		return new String[0];
	}
	
	@Nonnull
	@Override
	public String[] getParameterNames(Constructor<?> constructor) {
		Class<?>[] parameterTypes = constructor.getParameterTypes();
		if (parameterTypes.length == 0) return new String[0];
		
		try (InputStream stream = getClassAsStream(constructor.getDeclaringClass())) {
			String descriptor = Type.getConstructorDescriptor(constructor);
			return getParameterNames(stream, INIT_METHOD, descriptor);
		} catch (Exception | Error e) {
			log.error("ERROR!", e);
		}
		return new String[0];
	}
	
	/**
	 * 根据方法的 declaringClass 获取该类的流信息
	 * @param declaringClass declaringClass 类
	 * @return declaringClass 类流信息
	 */
	@Nonnull
	private InputStream getClassAsStream(Class<?> declaringClass) {
		ClassLoader classLoader = declaringClass.getClassLoader();
		classLoader = defaultIfNull(classLoader, getSystemClassLoader());
		return getClassAsStream(classLoader, declaringClass.getName());
	}
	
	/**
	 * 根据方法的 declaringClass 的类名称获取该类流信息
	 * @param classLoader 类加载器
	 * @param className   declaringClass 类名称
	 * @return declaringClass 类流信息
	 */
	@Nonnull
	private InputStream getClassAsStream(ClassLoader classLoader, String className) {
		String fileName = StringUtils.join(className.replace('.', '/'), ".class");
		InputStream stream = classLoader.getResourceAsStream(fileName);
		return defaultIfNull(stream, getClass().getResourceAsStream(fileName));
	}
	
	/**
	 * 获取参数名列表辅助方法
	 * @param stream   方法所在类的流信息
	 * @param describe 方法描述
	 * @return 参数名称列表
	 */
	@Nonnull
	private String[] getParameterNames(InputStream stream, String name, String describe) throws IOException {
		return this.getClassNode(stream).methods.stream().filter(methodNode -> {
			if (!StringUtils.equals(methodNode.desc, describe)) return false;
			return StringUtils.equals(name, methodNode.name);
		}).findAny().stream().flatMap(methodNode -> {
			Objects.requireNonNull(methodNode.localVariables);
			return methodNode.localVariables.stream();
		}).sorted(comparingInt(v -> v.index)).filter(v -> {
			// 非静态方法的第一个参数为 this，需要将其排除
			return !StringUtils.equals("this", v.name);
		}).map(v -> v.name).toArray(String[]::new);
	}
	
	/**
	 * 获取 ClassNode 对象
	 * @param stream 类流信息
	 * @return ClassNode 对象
	 */
	private ClassNode getClassNode(InputStream stream) throws IOException {
		ClassReader r = new ClassReader(stream);
		ClassNode node = new ClassNode();
		r.accept(node, EXPAND_FRAMES);
		return node;
	}
}
