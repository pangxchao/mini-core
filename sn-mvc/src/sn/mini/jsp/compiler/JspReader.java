/**
 * Created the sn.mini.jsp.reader.JspReader.java
 * @created 2017年11月28日 下午2:15:20
 * @version 1.0.0
 */
package sn.mini.jsp.compiler;

import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * sn.mini.jsp.reader.JspReader.java
 * @author XChao
 */
public class JspReader {
	final char[] stream;
	Mark current = new Mark();

	protected JspReader(File file, String encoding, String filePath) throws ParseException {
		try (InputStreamReader reader = new InputStreamReader( //
			new FileInputStream(file), encoding)) {
			// 读取输入流
			try (CharArrayWriter caw = new CharArrayWriter();) {
				char buf[] = new char[1024];
				for (int i = 0; (i = reader.read(buf)) != -1;) {
					caw.write(buf, 0, i);
				}
				this.stream = caw.toCharArray();
			}
		} catch (IOException e) {
			throw new ParseException(e.getMessage() + ":" + current.toError(filePath), e);
		}
	}

	protected JspReader(byte[] bytes, String filePath) throws ParseException {
		try (InputStreamReader reader = new InputStreamReader( //
			new ByteArrayInputStream(bytes))) {
			// 读取输入流
			try (CharArrayWriter caw = new CharArrayWriter();) {
				char buf[] = new char[1024];
				for (int i = 0; (i = reader.read(buf)) != -1;) {
					caw.write(buf, 0, i);
				}
				this.stream = caw.toCharArray();
			}
		} catch (IOException e) {
			throw new ParseException(e.getMessage() + ":" + current.toError(filePath), e);
		}
	}

	/**
	 * 返回当前游标位置的一个新对象
	 * @return
	 */
	protected Mark mark() {
		return new Mark(current);
	}

	/**
	 * 重新定位cursor
	 * @param mark
	 */
	protected void update(Mark mark) {
		current.update(mark);
	}

	/**
	 * 是否还有更多字符可以读取
	 * @return
	 * @throws ParseException
	 */
	protected boolean hasMoreInput() throws ParseException {
		return stream != null && current.cursor < stream.length;
	}

	/**
	 * 读取下一个字符
	 * @return
	 * @throws ParseException
	 */
	protected int readNextChar() throws ParseException {
		return hasMoreInput() ? this.stream[current.cursor] : -1;
	}

	/**
	 * 读取下一个字符，并将游标移到一个字符位置
	 * @return
	 * @throws ParseException
	 */
	protected int skipNextChar() throws ParseException {
		if(this.hasMoreInput()) {
			int ch = stream[current.cursor];
			current.cursor++;
			if(ch == '\n') {
				current.line++;
				current.column = 1;
			} else {
				current.column++;
			}
			return ch;
		}
		return -1;
	}

	/**
	 * 读取下一个字符，并将游标移到一个字符位置
	 * @param mark 保留当前游标位置
	 * @return
	 * @throws ParseException
	 */
	protected int skipNextChar(Mark mark) throws ParseException {
		mark.update(current);
		return this.skipNextChar();
	}

	/**
	 * 判断下一个字符是否为空格
	 * @return
	 * @throws ParseException
	 */
	protected final boolean isSpace() throws ParseException {
		return readNextChar() <= ' ';
	}

	/**
	 * 将游标移动到下一个非空格位置
	 * @return 跳过的空格个数
	 */
	protected int skipSpaces() throws ParseException {
		int i = 0;
		for (; isSpace(); i++) {
			skipNextChar();
		}
		return i;
	}

	/**
	 * 跳过指定数量的字符
	 * @param count
	 * @throws ParseException
	 */
	protected void skip(int count) throws ParseException {
		while (hasMoreInput() && --count >= 0) {
			skipNextChar();
		}
	}

	/**
	 * 判断最近字符是否与传入字符一致
	 * @param string
	 * @return
	 * @throws ParseException
	 */
	protected boolean matches(String string) throws ParseException {
		Mark mark = mark();
		for (int i = 0; i < string.length(); i++) {
			if(skipNextChar() != string.charAt(i)) {
				current.update(mark);
				return false;
			}
		}
		current.update(mark);
		return true;
	}

	/**
	 * 跳转到指定的地方，并返回该地方的游标
	 * @param limit
	 * @return
	 * @throws ParseException
	 */
	protected Mark skipUntil(String limit) throws ParseException {
		Mark mark = mark();
		int length = limit.length();
		int ch = skipNextChar();
		skip:
		for (; ch != -1; mark = mark(), ch = skipNextChar()) {
			if(ch == limit.charAt(0)) {
				for (int i = 1; i < length; i++) {
					if(skipNextChar() != limit.charAt(i)) {
						continue skip;
					}
				}
				return mark;
			}
		}
		return null;
	}

	/**
	 * 跳转到指定的地方，并返回该地方的游标,跳转时跳过el表达式
	 * @param limit
	 * @return
	 * @throws ParseException
	 */
	protected Mark skipUntilIgnoreEsc(String limit) throws ParseException {
		Mark mark = mark();
		int length = limit.length();
		int ch, prev = -1;
		char first = limit.charAt(0);
		skip:
		for (ch = skipNextChar(mark); ch != -1; prev = ch, ch = skipNextChar(mark)) {
			if(prev == '\\') {
				if(ch == '\\') {
					ch = 0;
				}
				continue;
			} else if(ch == '$' && readNextChar() == '{') {
				skipNextChar();
				skipELExpression();
			} else if(ch == first) {
				for (int i = 1; i < length; i++, skipNextChar()) {
					if(readNextChar() != limit.charAt(i)) {
						continue skip;
					}
				}
				return mark;
			}
		}
		return null;
	}

	/**
	 * 跳转到el表达式结束
	 * @return
	 * @throws ParseException
	 */
	protected Mark skipELExpression() throws ParseException {
		Mark mark = mark();
		int nesting = 0, ch;
		boolean sing = false, doub = false;
		do {
			ch = skipNextChar(mark);
			while (ch == '\\' && (sing || doub)) {
				skipNextChar();
				ch = readNextChar();
			}
			if(ch == -1) {
				return null;
			}
			if(ch == '"' && !sing) {
				doub = !doub;
			} else if(ch == '\'' && doub) {
				sing = !sing;
			} else if(ch == '{' && !sing && !doub) {
				nesting++;
			} else if(ch == '}' && !sing && !doub) {
				nesting--;
			}
		} while (ch != '}' || nesting >= 0);
		return mark;
	}

	/**
	 * 读取<jsp:之后的标签名称
	 * @return 标签名称
	 * @throws ParseException
	 */
	protected String skipUntilETagStart() throws ParseException {
		char ch = (char) readNextChar();
		if(Character.isLetter(ch)) {
			StringBuilder buf = new StringBuilder();
			buf.append(ch);
			skipNextChar();
			ch = (char) readNextChar();
			while (Character.isLetter(ch) || Character.isDigit(ch) || //
				ch == '_' || ch == '-') {
				buf.append(ch);
				skipNextChar();
				ch = (char) readNextChar();
			}
			return buf.toString();
		}
		return null;
	}

	/**
	 * 跳转到标签结束
	 * @param tagName
	 * @return
	 * @throws ParseException
	 */
	protected Mark skipUntilETagEnd(String tagName) throws ParseException {
		Mark mark = mark();
		int nesting = 0, ch = 0;
		do {
			if(matches("<jsp:" + tagName)) {
				nesting++;
				mark = skipUntil("<jsp:" + tagName);
			} else if(matches("</jsp:" + tagName)) {
				nesting--;
				mark = skipUntil("</jsp:" + tagName);
			} else {
				ch = skipNextChar(mark);
			}
			if(ch == -1) {
				return null;
			}
			skipSpaces();
		} while (nesting >= 0);
		return skipNextChar() == '>' ? mark : null;
	}

	/**
	 * 读取start 到 stop 之间的所有字符
	 * @param start
	 * @param stop
	 * @return
	 * @throws ParseException
	 */
	protected char[] getCharArray(Mark start, Mark stop) throws ParseException {
		Mark mark = mark();
		current.update(start);
		CharArrayWriter caw = new CharArrayWriter();
		while (hasMoreInput() && current.cursor < stop.cursor) {
			caw.write(skipNextChar());
		}
		// TODO: BufferedReader
		// BufferedReader reader = new BufferedReader(new CharArrayReader(caw.toCharArray()));
		current.update(mark);
		return caw.toCharArray();
	}

	/**
	 * 读取start 到 stop 之间的所有字符
	 * @param start
	 * @param stop
	 * @return
	 * @throws ParseException
	 */
	protected String getText(Mark start, Mark stop) throws ParseException {
		return new String(getCharArray(start, stop));
	}
}
