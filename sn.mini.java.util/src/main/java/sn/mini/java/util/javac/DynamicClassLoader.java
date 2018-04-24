/**
 * Created the sn.mini.java.util.javac.JavaFileObjectClassLoader.java
 * @created 2017年12月5日 下午5:33:33
 * @version 1.0.0
 */
package sn.mini.java.util.javac;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.tools.JavaFileObject;

/**
 * sn.mini.java.util.javac.JavaFileObjectClassLoader.java
 * @author XChao
 */
public class DynamicClassLoader extends ClassLoader {
	private final Map<String, JavaFileObject> classes = new ConcurrentHashMap<>();

	public DynamicClassLoader(ClassLoader parent) {
		super(parent);
	}

	public Collection<JavaFileObject> getAllFiles() {
		return classes.values();
	}

	protected synchronized Class<?> findClass(String name) throws ClassNotFoundException {
		if(classes.get(name) != null) {
			byte[] bytes = ((JavaFileObjectImpl) classes.get(name)).getBytes();
			return defineClass(name, bytes, 0, bytes.length);
		}
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException nf) {
		}
		try {
			return Thread.currentThread().getContextClassLoader().loadClass(name);
		} catch (ClassNotFoundException nf) {
		}

		return super.findClass(name);
	}

	public void add(String name, final JavaFileObject file) {
		classes.put(name, file);
	}

	public void clear() {
		this.classes.clear();
	}

	public JavaFileObject getJavaFileObject(String name) {
		return classes.get(name);
	}
}
