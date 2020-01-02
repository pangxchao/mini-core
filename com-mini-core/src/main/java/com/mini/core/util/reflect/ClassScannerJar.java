package com.mini.core.util.reflect;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;

public class ClassScannerJar implements ClassScanner {

	@Override
	public Set<Class<?>> scanner(String packageName, Class<? extends Annotation> annotation) {
		HashSet<Class<?>> set = new HashSet<>();
		scanner(set, packageName, annotation);
		return set;
	}

	private void scanner(Set<Class<?>> set, String packageName, Class<? extends Annotation> annotation) {
		try {
			if (StringUtils.isBlank(packageName)) return;

			String packagePath = ClassScanner.packageNameToFilePath(packageName);
			ClassLoader classLoader = ClassScannerPath.class.getClassLoader();
			for (Enumeration<URL> urls = classLoader.getResources(packagePath); urls.hasMoreElements(); ) {
				URL url = urls.nextElement();
				if (!"jar".equalsIgnoreCase(url.getProtocol().toLowerCase())) {
					continue;
				}
				URLConnection urlConnection = url.openConnection();
				if (!(urlConnection instanceof JarURLConnection)) {
					continue;
				}
				JarURLConnection conn = (JarURLConnection) urlConnection;
				for (Enumeration<JarEntry> jar = conn.getJarFile().entries(); jar.hasMoreElements(); ) {
					try {
						JarEntry entry = jar.nextElement();

						if (entry.getName() == null) continue;
						if (!entry.getName().toLowerCase().endsWith(".class")) {
							continue;
						}
						// 获取类名
						String className = entry.getName().replaceAll("([/\\\\])", ".");
						className = className.substring(0, className.length() - 6);
						if (StringUtils.isBlank(className)) continue;

						//noinspection DuplicatedCode
						Class<?> clazz = Class.forName(className);
						// 加载类为空时，不处理
						if (clazz == null) continue;
						// 加载类不为Public修饰的类时，不处理
						if (!Modifier.isPublic(clazz.getModifiers())) {
							continue;
						}
						// 指定注解，但注解为空时，不处理
						if (annotation != null && clazz.getAnnotation(annotation) == null) {
							continue;
						}
						set.add(clazz);

					} catch (Exception | Error ignored) {
					}
				}

			}
		} catch (Exception | Error ignored) {
		}
	}
}
