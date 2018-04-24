/**
 * Created the sn.mini.java.util.javac.JavaCompilerUtil.java
 * @created 2017年11月9日 下午5:45:26
 * @version 1.0.0
 */
package sn.mini.java.util.javac;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * sn.mini.java.util.javac.JavaCompilerUtil.java
 * @author XChao
 */
public final class JdkCompilerUtil {

	public static Class<?> compile(String packageName, String className, String javaSource) throws Exception {
		return new JdkCompileTask(new ArrayList<String>()).compile(packageName, className, javaSource);
	}

	public static Class<?> compile(String packageName, String className, String javaSource, List<String> options)
		throws Exception {
		return new JdkCompileTask(options).compile(packageName, className, javaSource);
	}

	public static Class<?> compile(String packageName, String className, String javaSource, String encoding)
		throws Exception {
		return new JdkCompileTask(new ArrayList<String>(), encoding).compile(packageName, className, javaSource);
	}

	public static Class<?> compile(String packageName, String className, String javaSource, String encoding,
		List<String> options) throws Exception {
		return new JdkCompileTask(options, encoding).compile(packageName, className, javaSource);
	}

	public static Class<?> compile(String packageName, String className, File javaFile) throws Exception {
		return new JdkCompileTask(new ArrayList<String>()).compile(packageName, className, javaFile);
	}

	public static Class<?> compile(String packageName, String className, File javaFile, List<String> options)
		throws Exception {
		return new JdkCompileTask(options).compile(packageName, className, javaFile);
	}

	public static Class<?> compile(String packageName, String className, File javaFile, String encoding)
		throws Exception {
		return new JdkCompileTask(new ArrayList<String>(), encoding).compile(packageName, className, javaFile);
	}

	public static Class<?> compile(String packageName, String className, File javaFile, String encoding,
		List<String> options) throws Exception {
		return new JdkCompileTask(options, encoding).compile(packageName, className, javaFile);
	}
}
