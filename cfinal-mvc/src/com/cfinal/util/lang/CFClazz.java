/**
 * Created the com.cfinal.util.lang.CFClazz.java
 * @created 2016年8月14日 上午10:22:25
 * @version 1.0.0
 */
package com.cfinal.util.lang;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;

import org.apache.commons.lang.StringUtils;

import com.cfinal.util.logger.CFLogger;

/**
 * java.lang.Class工具类
 * @author xchao
 */
public class CFClazz {
	/**
	 * 调用 Class.forName() 并转换成 clazz 对应类的子类Class对象
	 * @param className
	 * @param clazz
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static <T> Class<? extends T> forName(String className, Class<T> clazz) throws Exception {
		return Class.forName(className, false, CFClazz.getDefaultClassLoader()).asSubclass(clazz);
	}

	/**
	 * 将 加载 类名为 clazzName 的类,将强制转换成 T的子类Class对象,然后创建该对象实例返回
	 * @param clazzName
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> T newInstence(String clazzName, Class<T> clazz) throws Exception {
		return Class.forName(clazzName, false, CFClazz.getDefaultClassLoader()).asSubclass(clazz).newInstance();
	}

	/**
	 * 获取默认类加载器
	 * @return Thread.currentThread().getContextClassLoader() || XClassUtil.class.getClassLoader()
	 */
	public static ClassLoader getDefaultClassLoader() {
		try {
			return Thread.currentThread().getContextClassLoader();
		} catch (Exception e) {
			return CFClazz.class.getClassLoader();
		}
	}

	/**
	 * 扫描所有普通包下的类，非jar包下的类。 扫描时会跳过内部类，和不是以 publick 修饰符修改的类。
	 * @param packageName 指定包名，扫描所有包时传null或者 ""
	 * @param recursive 是否递归扫描， 如果为true:则扫描当前包及当前包下的所有子包
	 * @param annotation 指定注解，如果不为空，返回结果中的类只包含有当前解决的类
	 */
	public static List<Class<?>> scanning(String packageName, boolean recursive,
		Class<? extends Annotation> annotation) {
		if(StringUtils.isBlank(packageName)) {
			packageName = "";
		}
		List<Class<?>> clazzList = new ArrayList<Class<?>>();
		scanning(clazzList, packageName, recursive, annotation);
		return clazzList;
	}

	/**
	 * 扫描所有jar包中的类，普通包下的class无法扫描 。 扫描时会跳过内部类，和不是以 publick 修饰符修改的类。
	 * @param packageName 指定包名，扫描所有包时传null或者 ""
	 * @param recursive 是否递归扫描， 如果为true:则扫描当前包及当前包下的所有子包
	 * @param annotation 指定注解，如果不为空，返回结果中的类只包含有当前解决的类
	 */
	public static List<Class<?>> scanningjars(String packageName, boolean recursive,
		Class<? extends Annotation> annotation) {
		if(StringUtils.isBlank(packageName)) {
			packageName = "";
		}
		List<Class<?>> clazzList = new ArrayList<Class<?>>();
		scanning_jar(clazzList, packageName, recursive, annotation);
		return clazzList;
	}

	/**
	 * 扫描所有普通包和jar包中的类。 扫描时会跳过内部类，和不是以 publick 修饰符修改的类。
	 * @param packageName 指定包名，扫描所有包时传null或者 ""
	 * @param recursive 是否递归扫描， 如果为true:则扫描当前包及当前包下的所有子包
	 * @param annotation 指定注解，如果不为空，返回结果中的类只包含有当前解决的类
	 */
	public static List<Class<?>> scanningall(String packageName, boolean recursive,
		Class<? extends Annotation> annotation) {
		if(StringUtils.isBlank(packageName)) {
			packageName = "";
		}
		List<Class<?>> clazzList = new ArrayList<Class<?>>();
		scanning(clazzList, packageName, recursive, annotation);
		scanning_jar(clazzList, packageName, recursive, annotation);
		return clazzList;
	}

	/**
	 * 扫描所有普通包下的类，非jar包下的类
	 * @param clazzList 扫描结果容器
	 * @param packageName 指定包名，扫描所有包时传null或者 ""
	 * @param recursive 是否递归扫描， 如果为true:则扫描当前包及当前包下的所有子包
	 * @param annotation 指定注解，如果不为空，返回结果中的类只包含有当前解决的类
	 */
	private static void scanning(List<Class<?>> clazzList, String packageName, //
		boolean recursive, Class<? extends Annotation> annotation) {
		try {
			ClassLoader classLoader = getDefaultClassLoader();
			String packagePath = packageNameToFilePath(packageName);
			Enumeration<URL> urls = classLoader.getResources(packagePath);
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				if("file".equals(url.getProtocol())) {
					scanning(clazzList, url.toURI(), packageName, recursive, annotation);
				}
			}
		} catch (Exception e) {
			CFLogger.severe(e.getMessage(), e);
		}
	}

	/**
	 * 扫描所有jar包中的类，普通包下的class无法扫描
	 * @param clazzList 扫描结果容器
	 * @param packageName 指定包名，扫描所有包时传null或者 ""
	 * @param recursive 是否递归扫描， 如果为true:则扫描当前包及当前包下的所有子包
	 * @param annotation 指定注解，如果不为空，返回结果中的类只包含有当前解决的类
	 */
	private static void scanning_jar(List<Class<?>> clazzList, String packageName, //
		boolean recursive, Class<? extends Annotation> annotation) {
		ClassLoader classLoader = getDefaultClassLoader();
		scanning_jar(classLoader, clazzList, packageName, recursive, annotation);
	}

	/**
	 * 获取所有普通包（文件夹）里的所有类
	 * @param clazzList 扫描结果容器
	 * @param packageName 包名： com.ms.mvc.controller
	 * @param classPath 类所属路径
	 * @param recursive 是否递归查询所有类 ， true: 是 ， false： 否
	 * @param annotation 指定注解, null： 不指定
	 */
	private static void scanning(List<Class<?>> clazzList, URI packageURI, String packageName, //
		boolean recursive, Class<? extends Annotation> annotation) {
		File[] files = getClassFileOrDirector(packageURI);
		if(files == null || files.length == 0) {
			return;
		}
		for (File file : files) {
			scanningToClazzList(clazzList, file, packageURI, packageName, //
				recursive, annotation);
		}
	}

	/**
	 * 获取所有普通包（文件夹）里的所有类
	 * @param clazzList 扫描结果容器
	 * @param file 文件或者文件夹对象
	 * @param packageName 包名： com.ms.mvc.controller
	 * @param classPath 类所属路径
	 * @param recursive 是否递归查询所有类 ， true: 是 ， false： 否
	 * @param annotation 指定注解, null： 不指定
	 */
	private static void scanningToClazzList(List<Class<?>> clazzList, File file, URI packageURI, //
		String packageName, boolean recursive, Class<? extends Annotation> annotation) {
		try {
			String fileName = file.getName();
			if(file.isFile() && file.getName().endsWith(".class")) {
				classNameToResultList(clazzList, getClassAllNameByFilePath(//
					packageName, fileName), annotation);
			} else if(recursive && file.isDirectory()) {
				scanning(clazzList, file.toURI(), new StringBuffer(packageName).append(".")//
					.append(fileName).toString(), recursive, annotation);
			}
		} catch (Exception e) {
			CFLogger.severe(e.getMessage(), e);
		}
	}

	/**
	 * 扫描所有jar包中的类，普通包下的class无法扫描
	 * @param classLoader 类加载器
	 * @param clazzList 扫描结果容器
	 * @param packageName 指定包名，扫描所有包时传null或者 ""
	 * @param recursive 是否递归扫描， 如果为true:则扫描当前包及当前包下的所有子包
	 * @param annotation 指定注解，如果不为空，返回结果中的类只包含有当前解决的类
	 */
	private static void scanning_jar(ClassLoader classLoader, List<Class<?>> clazzList, String packageName,
		boolean recursive, Class<? extends Annotation> annotation) {
		try {
			if(classLoader instanceof URLClassLoader) {
				for (URL url : ((URLClassLoader) classLoader).getURLs()) {
					if(url.getFile().endsWith(".jar")) {
						URL jarUrl = new URL("jar:" + url.toString() + "!/");
						scanning_jar(clazzList, jarUrl, packageName, recursive, annotation);
					}
				}
			}
			if(classLoader != null) {
				scanning_jar(classLoader.getParent(), clazzList, packageName, recursive, annotation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 扫描所有jar包中的类，普通包下的class无法扫描
	 * @param clazzList 扫描结果容器
	 * @param packageName 指定包名，扫描所有包时传null或者 ""
	 * @param jarUrl jar包URL路径
	 * @param recursive 是否递归扫描， 如果为true:则扫描当前包及当前包下的所有子包
	 * @param annotation 指定注解，如果不为空，返回结果中的类只包含有当前解决的类
	 */
	private static void scanning_jar(List<Class<?>> clazzList, URL jarUrl, String packageName, boolean recursive,
		Class<? extends Annotation> annotation) throws IOException {
		try {
			Enumeration<JarEntry> jarEntries = ((JarURLConnection) jarUrl.openConnection()).getJarFile().entries();
			while (jarEntries.hasMoreElements()) {
				JarEntry jarEntry = jarEntries.nextElement();
				if(jarEntry.getName() != null && jarEntry.getName().endsWith(".class")) {
					String clazzName = jarEntry.getName().replaceAll("(\\/|\\\\)", ".");
					clazzName = clazzName.substring(0, clazzName.length() - 6);
					int lastIndex = clazzName.lastIndexOf(".");
					String prefix = clazzName.substring(0, ((lastIndex < 0) ? 0 : lastIndex));
					if((recursive && prefix.startsWith(packageName)) || prefix.equals(packageName)) {
						classNameToResultList(clazzList, clazzName, annotation);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将 Class 添加到 clazzList
	 * @param clazzList
	 * @param clazzName
	 * @param annotation
	 */
	private static void classNameToResultList(List<Class<?>> clazzList, String clazzName,
		Class<? extends Annotation> annotation) {
		try {
			if(StringUtils.isNotBlank(clazzName)) {
				Class<?> clazz = Class.forName(clazzName, false, getDefaultClassLoader());
				if(clazz != null && clazzName.equals(clazz.getCanonicalName())
					&& Modifier.isPublic(clazz.getModifiers())) {
					if(annotation != null && clazz.getAnnotation(annotation) == null) {
						return;
					}
					clazzList.add(clazz);
				}
			}
		} catch (ClassNotFoundException e) {
			CFLogger.severe("java.lang.ClassNotFoundException: class " + //
				clazzName, e);
		} catch (NoClassDefFoundError e) {
			CFLogger.severe("java.lang.NoClassDefFoundError: " + //
				e.getMessage() + " class " + clazzName, e);
		} catch (ExceptionInInitializerError e) {
			CFLogger.severe("java.lang.ExceptionInInitializerError: " + //
				e.getMessage() + " class " + clazzName, e);
		} catch (UnsatisfiedLinkError e) {
			CFLogger.severe("java.lang.UnsatisfiedLinkError: " + //
				e.getMessage() + " class " + clazzName, e);
		} catch (Exception e) {
			CFLogger.severe("java.lang.Exception: " + e.getMessage() + //
				" class " + clazzName, e);
		} catch (Error e) {
			CFLogger.severe("java.lang.Error: " + e.getMessage() + //
				" class " + clazzName, e);
		}
	}

	/**
	 * 将包名转换成包路径
	 * @param packageName
	 */
	private static String packageNameToFilePath(String packageName) {
		if(StringUtils.isBlank(packageName)) {
			return "";
		}
		return packageName.replaceAll("[.]", "/");
	}

	/**
	 * 根据包名和 .class 文件名获取当前 .class 文件所在类的全名
	 * @param packageName 包名
	 * @param fileName .class 文件名
	 */
	private static String getClassAllNameByFilePath(String packageName, String fileName) {
		String className = fileName.substring(0, fileName.length() - 6);
		return new StringBuffer(packageName).append(".").append(className).toString();
	}

	/**
	 * 获取当前包路径下的所有类文件和文件夹
	 * @param packagePath
	 */
	private static File[] getClassFileOrDirector(URI packageURI) {
		return new File(packageURI).listFiles(new FileFilter() {
			public boolean accept(File file) {
				return (file.isFile() && file.getName().endsWith(".class")) || //
				file.isDirectory();
			}
		});
	}

	public static void main(String[] args) {
		CFLogger.setHandler(CFLogger.HANDLE_CONSOLE);
		List<Class<?>> list = scanningall("com", true, null);
		System.out.println(list);
	}
}
