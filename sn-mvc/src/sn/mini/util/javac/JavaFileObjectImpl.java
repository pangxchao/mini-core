/**
 * Created the sn.mini.util.javac.JavaFileObjectImpl.java
 * @created 2017年12月13日 下午5:38:45
 * @version 1.0.0
 */
package sn.mini.util.javac;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Optional;

import javax.tools.SimpleJavaFileObject;

import sn.mini.util.lang.StringUtil;

/**
 * sn.mini.util.javac.JavaFileObjectImpl.java
 * @author XChao
 */
public class JavaFileObjectImpl extends SimpleJavaFileObject {

	private final CharSequence source;
	private ByteArrayOutputStream bytes = new ByteArrayOutputStream();

	public JavaFileObjectImpl(final String className, final CharSequence source) {
		super(StringUtil.toURI(className + JdkCompileTask.EXTENSION), Kind.SOURCE);
		this.source = source;
	}

	public JavaFileObjectImpl(final String name, final Kind kind) {
		super(StringUtil.toURI(name), kind);
		source = null;
	}

	public JavaFileObjectImpl(URI uri, Kind kind) {
		super(uri, kind);
		source = null;
	}

	@Override
	public CharSequence getCharContent(final boolean ignoreEncodingErrors) throws UnsupportedOperationException {
		return Optional.ofNullable(source).orElseThrow(() -> new UnsupportedOperationException());
	}

	@Override
	public InputStream openInputStream() {
		return new ByteArrayInputStream(bytes.toByteArray());
	}

	@Override
	public OutputStream openOutputStream() {
		return bytes;
	}

	public byte[] getBytes() {
		return bytes.toByteArray();
	}
}
