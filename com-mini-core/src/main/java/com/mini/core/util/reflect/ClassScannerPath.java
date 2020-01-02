package com.mini.core.util.reflect;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class ClassScannerPath implements ClassScanner {
	private static final FileFilter FILE_FILTER = file -> file.isDirectory()  //
		|| StringUtils.endsWith(file.getName().toLowerCase(), ".class");

	@Override
	public Set<Class<?>> scanner(String packageName, Class<? extends Annotation> annotation) {
		HashSet<Class<?>> set = new HashSet<>();
		scanner(set, packageName, annotation);
		return set;
	}

	private void scanner(Set<Class<?>> set, String packageName, Class<? extends Annotation> annotation) {
		try {
			if (StringUtils.isBlank(packageName)) return;

			ClassLoader classLoader = ClassScannerPath.class.getClassLoader();
			String packagePath = ClassScanner.packageNameToFilePath(packageName);
			for (Enumeration<URL> urls = classLoader.getResources(packagePath); urls.hasMoreElements(); ) {
				URL url = urls.nextElement();
				if (!StringUtils.equalsIgnoreCase("file", url.getProtocol())) {
					continue;
				}
				File[] files = new File(url.toURI()).listFiles(FILE_FILTER);
				if (files == null || files.length == 0) {
					continue;
				}
				for (File file : files) {
					try {
						if (file.isFile() && file.getName().toLowerCase().endsWith(".class")) {
							String className = this.getClassName(packageName, file.getName());
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
						}
						// 处理下级包
						scanner(set, packageName + "." + file.getName(), annotation);
					} catch (Exception | Error ignored) {
					}
				}
			}
		} catch (Exception | Error ignored) {
		}
	}

	/**
	 * 根据包名和 .class 文件名获取当前 .class 文件所在类的全名
	 * @param packageName 包名
	 * @param fileName    .class 文件名
	 */
	private String getClassName(String packageName, String fileName) {
		String className = fileName.substring(0, fileName.length() - 6);
		return packageName + "." + className;
	}
}
