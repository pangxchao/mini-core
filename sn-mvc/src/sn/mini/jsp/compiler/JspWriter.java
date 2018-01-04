/**
 * Created the sn.mini.jsp.writer.JspWriter.java
 * @created 2017年11月28日 下午12:02:00
 * @version 1.0.0
 */
package sn.mini.jsp.compiler;

import java.io.IOException;
import java.io.Writer;

/**
 * sn.mini.jsp.writer.JspWriter.java
 * @author XChao
 */
public class JspWriter extends Writer {

	private final Writer writer;

	public JspWriter(Writer writer) {
		this.writer = writer;
	}

	@Override
	public void write(int c) throws IOException {
		writer.write(c);
	}

	@Override
	public void write(char[] cbuf) throws IOException {
		writer.write(cbuf);
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		writer.write(cbuf, off, len);
	}

	@Override
	public void write(String str) throws IOException {
		writer.write(str);
	}

	@Override
	public void write(String str, int off, int len) throws IOException {
		writer.write(str, off, len);
	}

	@Override
	public Writer append(CharSequence csq) throws IOException {
		return writer.append(csq);
	}

	@Override
	public Writer append(CharSequence csq, int start, int end) throws IOException {
		return writer.append(csq, start, end);
	}

	@Override
	public Writer append(char c) throws IOException {
		return writer.append(c);
	}

	@Override
	public void flush() throws IOException {
		writer.flush();
	}

	@Override
	public void close() throws IOException {
		writer.close();
	}

	public void newLine() throws IOException {
		write(System.lineSeparator());
	}

	public void print(boolean b) throws IOException {
		write(b ? "true" : "false");
	}

	public void print(char c) throws IOException {
		write(String.valueOf(c));
	}

	public void print(int i) throws IOException {
		write(String.valueOf(i));
	}

	public void print(long l) throws IOException {
		write(String.valueOf(l));
	}

	public void print(float f) throws IOException {
		write(String.valueOf(f));
	}

	public void print(double d) throws IOException {
		write(String.valueOf(d));
	}

	public void print(char s[]) throws IOException {
		write(s);
	}

	public void print(String s) throws IOException {
		write(s == null ? "null" : s);
	}

	public void print(Object obj) throws IOException {
		write(String.valueOf(obj));
	}

	public void println() throws IOException {
		newLine();
	}

	public void println(boolean x) throws IOException {
		print(x);
		println();
	}

	public void println(char x) throws IOException {
		print(x);
		println();
	}

	public void println(int x) throws IOException {
		print(x);
		println();
	}

	public void println(long x) throws IOException {
		print(x);
		println();
	}

	public void println(float x) throws IOException {
		print(x);
		println();
	}

	public void println(double x) throws IOException {
		print(x);
		println();
	}

	public void println(char x[]) throws IOException {
		print(x);
		println();
	}

	public void println(String x) throws IOException {
		print(x);
		println();
	}

	public void println(Object x) throws IOException {
		print(x);
		println();
	}
}
