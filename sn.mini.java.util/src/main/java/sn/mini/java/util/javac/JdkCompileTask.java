/**
 * Created the sn.mini.java.util.javac.JdkCompileTask.java
 * @created 2017年12月13日 下午5:49:43
 * @version 1.0.0
 */
package sn.mini.java.util.javac;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import sn.mini.java.util.lang.ClassUtil;
import sn.mini.java.util.lang.StringUtil;

/**
 * sn.mini.java.util.javac.JdkCompileTask.java
 * @author XChao
 */
public class JdkCompileTask {
	private static final DynamicClassLoader load = new DynamicClassLoader(ClassUtil.getDefaultClassLoader());
	public static final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	public static final String EXTENSION = ".java", EXTENSION_CLASS = ".class";

	private String encoding = "UTF-8";
	private final List<String> options = new ArrayList<>();

	public JdkCompileTask(List<String> options) {
		Objects.requireNonNull(compiler, "Can't find java compiler , pls check tools.jar");
		this.options.addAll(options);
	}

	public JdkCompileTask(List<String> options, String encoding) {
		Objects.requireNonNull(compiler, "Can't find java compiler , pls check tools.jar");
		this.options.addAll(options);
		this.encoding = encoding;
	}

	public synchronized Class<?> compile(String packageName, String className, final CharSequence javaSource)
		throws ClassCastException, ClassNotFoundException, IOException {
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		// 获取 StandardJavaFileManager， 并添加classPath路径
		StandardJavaFileManager fileManager = getStandardFileManager(diagnostics, load);
		JavaFileManagerImpl javaFileManager = new JavaFileManagerImpl(fileManager, load);
		JavaFileObjectImpl source = new JavaFileObjectImpl(className, javaSource);
		javaFileManager.putFileForInput(StandardLocation.SOURCE_PATH, packageName, className + EXTENSION, source);
		Boolean result = compiler.getTask(null, javaFileManager, null, options, null, Arrays.asList(source)).call();
		if(result == null || !result.booleanValue()) {
			throw new ClassCastException("Compilation failed.");
		}
		return (Class<?>) load.loadClass(packageName + "." + className);
	}

	public synchronized Class<?> compile(String packageName, String className, final File javaFile)
		throws ClassCastException, ClassNotFoundException, IOException {
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		// 获取 StandardJavaFileManager， 并添加classPath路径
		StandardJavaFileManager fileManager = getStandardFileManager(diagnostics, load);
		Iterable<? extends JavaFileObject> sources = fileManager.getJavaFileObjects(javaFile);
		List<String> list = new ArrayList<String>();
		Boolean result = compiler.getTask(null, fileManager, null, options, list, sources).call();
		if(result == null || !result.booleanValue()) {
			throw new ClassCastException("Compilation failed.");
		}
		return new URIClassLoader(new File(javaFile.getParentFile(), className + EXTENSION_CLASS), load)
			.loadClass(packageName + "." + className);
	}

	/**
	 * 设置编译时的 classpath
	 * @param diagnostics
	 * @param classLoader
	 * @return
	 * @throws IOException
	 */
	private StandardJavaFileManager getStandardFileManager(DiagnosticCollector<JavaFileObject> diagnostics,
		ClassLoader classLoader) throws IOException {
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
		List<File> classPathList = new ArrayList<File>();
		do {
			if(classLoader instanceof URLClassLoader) {
				for (URL url : ((URLClassLoader) classLoader).getURLs()) {
					classPathList.add(new File(StringUtil.urlDecode(url.getFile(), encoding)));
				}
				fileManager.setLocation(StandardLocation.CLASS_PATH, classPathList);
			}

			classLoader = classLoader.getParent();
		} while (classLoader != null);
		return fileManager;
	}
}
