/**
 * Created the sn.mini.java.util.javac.URIClassLoader.java
 * @created 2017年12月13日 下午7:09:19
 * @version 1.0.0
 */
package sn.mini.java.util.javac;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * sn.mini.java.util.javac.URIClassLoader.java
 * @author XChao
 */
public class URIClassLoader extends ClassLoader {
	private String name;
	private final File classFile;

	public URIClassLoader(String classPath, ClassLoader parent) {
		this(new File(classPath), parent);
	}

	public URIClassLoader(File classFile, ClassLoader parent) {
		super(parent);
		this.classFile = classFile;
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if(this.name == null || this.name.equals(name)) {
			return findClass(this.name = name);
		}
		return super.loadClass(name);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try (InputStream stream = new FileInputStream(classFile);) {
			byte[] bytes = new byte[(int) classFile.length()];
			stream.read(bytes); // 读取 class 内容字节码
			Class<?> clazz = defineClass(name, bytes, 0, bytes.length);
			resolveClass(clazz); // 连接class
			return clazz; // 返回结果
		} catch (IOException e) {
			throw new ClassNotFoundException(e.getMessage(), e);
		}
	}
}
