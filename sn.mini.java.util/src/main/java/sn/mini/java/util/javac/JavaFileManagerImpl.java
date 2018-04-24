/**
 * Created the sn.mini.java.util.javac.JavaFileManagerImpl.java
 * @created 2017年12月13日 下午5:42:42
 * @version 1.0.0
 */
package sn.mini.java.util.javac;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

import sn.mini.java.util.lang.StringUtil;

import javax.tools.StandardLocation;

/**
 * sn.mini.java.util.javac.JavaFileManagerImpl.java
 * @author XChao
 */
public class JavaFileManagerImpl extends ForwardingJavaFileManager<JavaFileManager> {

	private final DynamicClassLoader loader;
	private final Map<URI, JavaFileObject> data = new HashMap<URI, JavaFileObject>();

	public JavaFileManagerImpl(JavaFileManager fileManager, DynamicClassLoader loader) {
		super(fileManager);
		this.loader = loader;
	}

	public void putFileForInput(Location location, String packageName, String relativeName,
		JavaFileObject javaFileObject) {
		data.put(clasURI(location, packageName, relativeName), javaFileObject);
	}

	@Override
	public FileObject getFileForInput(Location location, String packageName, String relativeName) throws IOException {
		FileObject o = data.get(clasURI(location, packageName, relativeName));
		return o == null ? super.getFileForInput(location, packageName, relativeName) : o;
	}

	@Override
	public String inferBinaryName(Location loc, JavaFileObject file) {
		return (file instanceof JavaFileObjectImpl) ? file.getName() : super.inferBinaryName(loc, file);
	}

	@Override
	public JavaFileObject getJavaFileForOutput(Location location, String relativeName, Kind kind, FileObject sibling)
		throws IOException {
		JavaFileObject file = new JavaFileObjectImpl(relativeName, kind);
		loader.add(relativeName, file);
		return file;
	}

	@Override
	public ClassLoader getClassLoader(JavaFileManager.Location location) {
		return loader;
	}

	@Override
	public Iterable<JavaFileObject> list(Location location, String packageName, Set<Kind> kinds, boolean recurse)
		throws IOException {
		Iterable<JavaFileObject> files = super.list(location, packageName, kinds, recurse);
		List<JavaFileObject> result = new ArrayList<JavaFileObject>();
		for (JavaFileObject file : files) {
			result.add(file);
		}
		if(StandardLocation.CLASS_PATH == location && kinds.contains(JavaFileObject.Kind.CLASS)) {
			for (JavaFileObject file : data.values()) {
				if(Kind.CLASS == file.getKind() && file.getName().startsWith(packageName)) {
					result.add(file);
				}
			}
			result.addAll(loader.getAllFiles());
		}

		if(StandardLocation.SOURCE_PATH == location && kinds.contains(JavaFileObject.Kind.SOURCE)) {
			for (JavaFileObject file : data.values()) {
				if(Kind.SOURCE == file.getKind() && file.getName().startsWith(packageName)) {
					result.add(file);
				}
			}
		}
		return result;
	}

	private URI clasURI(Location location, String packageName, String relativeName) {
		return StringUtil.toURI(location.getName() + '/' + packageName + '/' + relativeName);
	}
}
