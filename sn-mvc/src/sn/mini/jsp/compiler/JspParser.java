/**
 * Created the sn.mini.jsp.compiler.JspParser.java
 * @created 2017年11月29日 下午1:12:36
 * @version 1.0.0
 */
package sn.mini.jsp.compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;

import sn.mini.jsp.JspEngine;
import sn.mini.jsp.compiler.node.Comment;
import sn.mini.jsp.compiler.node.Declaration;
import sn.mini.jsp.compiler.node.Directive;
import sn.mini.jsp.compiler.node.ELExpression;
import sn.mini.jsp.compiler.node.ETag;
import sn.mini.jsp.compiler.node.Expression;
import sn.mini.jsp.compiler.node.Node;
import sn.mini.jsp.compiler.node.Root;
import sn.mini.jsp.compiler.node.Scriptlet;
import sn.mini.jsp.compiler.node.TextNode;
import sn.mini.util.lang.StringUtil;

/**
 * sn.mini.jsp.compiler.JspParser.java
 * @author XChao
 */
public class JspParser {
	private final JspReader reader;
	private Mark start, tstart = null;
	private final String filePath;

	public JspParser(JspReader reader, String filePath) {
		this.reader = reader;
		this.filePath = filePath;
	}

	public static Root parser(File file, String encoding, String filePath) throws ParseException {
		JspReader jspReader = new JspReader(file, encoding, filePath);
		JspParser parser = new JspParser(jspReader, filePath);
		Root root = new Root(parser.reader.mark());
		while (parser.reader.hasMoreInput()) {
			parser.parseElements(root);
		}
		return root;
	}

	/**
	 * 解析文档
	 * @param node
	 * @param content
	 * @throws ParseException
	 */
	private static void parser(Node node, String content, String filePath) throws ParseException {
		try {
			byte[] bytes = content.getBytes("UTF-8");
			JspReader jspReader = new JspReader(bytes, filePath);
			JspParser parser = new JspParser(jspReader, filePath);
			parser.tstart = parser.reader.mark();
			parser.start = parser.reader.mark();
			while (parser.reader.hasMoreInput()) {
				parser.parseElements(node);
			}
		} catch (Exception e) {
			throw new ParseException(e.getMessage(), e);
		}
	}

	/**
	 * 读取节点
	 * @param parent
	 * @throws ParseException
	 */
	private void parseElements(Node parent) throws ParseException {
		start = reader.mark();
		if(reader.matches("<%--")) {
			parseText(tstart, parent);
			reader.skipUntil("<%--");
			parseComment(parent);
			tstart = reader.mark();
		} else if(reader.matches("<%@")) {
			parseText(tstart, parent);
			reader.skipUntil("<%@");
			parseDirective(parent);
			tstart = reader.mark();
		} else if(reader.matches("<%!")) {
			parseText(tstart, parent);
			reader.skipUntil("<%!");
			parseDeclaration(parent);
			tstart = reader.mark();
		} else if(reader.matches("<%=")) {
			parseText(tstart, parent);
			reader.skipUntil("<%=");
			parseExpression(parent);
			tstart = reader.mark();
		} else if(reader.matches("<%")) {
			parseText(tstart, parent);
			reader.skipUntil("<%");
			parseScriptlet(parent);
			tstart = reader.mark();
		} else if(reader.matches("<jsp:")) {
			parseText(tstart, parent);
			reader.skipUntil("<jsp:");
			parseETag(parent);
			tstart = reader.mark();
		} else if(reader.matches("${")) {
			parseText(tstart, parent);
			reader.skipUntil("${");
			parseELExpression(parent);
			tstart = reader.mark();
		} else {
			reader.skipNextChar();
			if(!reader.hasMoreInput()) {
				parseText(tstart, parent);
			}
		}
	}

	/**
	 * 读取标签之间的字符串
	 * @param parent
	 * @throws ParseException
	 */
	private void parseText(Mark tstart, Node parent) throws ParseException {
		Mark stop = reader.mark();
		if(tstart != null && !tstart.equals(stop)) {
			parent.child(new TextNode(tstart, reader.getText(tstart, stop)));
		}
	}

	/**
	 * <%-- --%>
	 * @param parent
	 * @throws ParseException
	 */
	private void parseComment(Node parent) throws ParseException {
		start = reader.mark();
		Mark stop = reader.skipUntil("--%>");
		if(stop == null) {
			throw new ParseException("Unterminated: " + start.toError(filePath));
		}
		parent.child(new Comment(start, reader.getText(start, stop)));
	}

	/**
	 * <%@ %>
	 * @param parent
	 * @throws ParseException
	 */
	private void parseDirective(Node parent) throws ParseException {
		reader.skipSpaces();
		start = reader.mark();
		if(reader.matches("page")) {
			reader.skipUntil("page"); //
			Directive node = new Directive(start);
			this.parseAttributes(node);
			do {
				if(parent instanceof Root) {
					((Root) parent).addImport(node);
				}
				parent = parent.parent();
			} while (parent != null);
		}
		reader.skipSpaces();
		if(!reader.matches("%>")) {
			throw new ParseException("Unterminated: " + start.toError(filePath));
		}
		reader.skipUntil("%>");
	}

	/**
	 * <%! %>
	 * @param parent
	 * @throws ParseException
	 */
	private void parseDeclaration(Node parent) throws ParseException {
		start = reader.mark();
		Mark stop = reader.skipUntil("%>");
		if(stop == null) {
			throw new ParseException("Unterminated: " + start.toError(filePath));
		}
		do {
			if(parent instanceof Root) {
				((Root) parent).addDeclaration(new Declaration(start, reader.getText(start, stop)));
			}
			parent = parent.parent();
		} while (parent != null);
	}

	/**
	 * <%= %>
	 * @param parent
	 * @throws ParseException
	 */
	private void parseExpression(Node parent) throws ParseException {
		start = reader.mark();
		Mark stop = reader.skipUntil("%>");
		if(stop == null) {
			throw new ParseException("Unterminated " + ": " + start.toError(filePath));
		}
		parent.child(new Expression(start, reader.getText(start, stop)));
	}

	/**
	 * <% %>
	 * @param parent
	 * @throws ParseException
	 */
	private void parseScriptlet(Node parent) throws ParseException {
		start = reader.mark();
		Mark stop = reader.skipUntil("%>");
		if(stop == null) {
			throw new ParseException("Unterminated: " + start.toError(filePath));
		}
		parent.child(new Scriptlet(start, reader.getText(start, stop)));
	}

	/**
	 * &lt;jsp:xxx&gt; &lt;/jsp:xxx&gt;
	 * @param parent
	 */
	private void parseETag(Node parent) throws ParseException {
		start = reader.mark();
		String tagName = reader.skipUntilETagStart();
		if(StringUtil.isBlank(tagName) || JspEngine.getTag(tagName.toLowerCase()) == null) {
			throw new ParseException("Unsupported: " + start.toError(filePath));
		}
		reader.skipSpaces(); // 路过空格
		ETag node = new ETag(start, JspEngine.getTag(tagName.toLowerCase()));
		parseAttributes(node);
		parent.child(node);
		reader.skipSpaces();
		if(reader.matches("/>")) {
			reader.skipUntil("/>");
			return;
		}
		if(!reader.matches(">")) {
			throw new ParseException("Unterminated: " + start.toError(filePath));
		}
		reader.skipUntil(">");
		reader.skipSpaces();
		Mark start = reader.mark(), stop = reader.skipUntilETagEnd(tagName);
		if(stop == null) {
			throw new ParseException("Unterminated: " + start.toError(filePath));
		}
		// 解析子标签了内容
		JspParser.parser(node, reader.getText(start, stop), filePath);
	}

	/**
	 * ${xxx.xxx}
	 * @param parent
	 * @throws ParseException
	 */
	private void parseELExpression(Node parent) throws ParseException {
		start = reader.mark();
		Mark stop = reader.skipELExpression();
		if(stop == null) {
			throw new ParseException("Unterminated: " + start.toError(filePath));
		}
		parent.child(new ELExpression(start, reader.getText(start, stop)));
	}

	/**
	 * 解析标签属性
	 * @param node
	 * @throws ParseException
	 */
	private void parseAttributes(Node node) throws ParseException {
		reader.skipSpaces();
		do {
			String qName = null, value = null;
			char ch = (char) reader.readNextChar();
			if(Character.isLetter(ch) || ch == '_') {
				StringBuilder buf = new StringBuilder().append(ch);
				reader.skipNextChar();
				ch = (char) reader.readNextChar();
				while (Character.isLetter(ch) || Character.isDigit(ch) || ch == '.' || ch == '_' || ch == '-') {
					buf.append(ch);
					reader.skipNextChar();
					ch = (char) reader.readNextChar();
				}
				qName = buf.toString();
			}
			if(qName == null) {
				break;
			}
			reader.skipSpaces();
			if(!reader.matches("=")) {
				break;
			}
			reader.skipUntil("=");
			reader.skipSpaces();
			char quote = (char) reader.skipNextChar();
			if(quote != '\'' && quote != '"') {
				break;
			}
			String watchString = "";
			if(reader.matches("<%=")) {
				reader.skipUntil("<%=");
				watchString = "%>";
			}
			watchString = watchString + quote;
			start = reader.mark();
			Mark stop = reader.skipUntilIgnoreEsc(watchString);
			value = new String(reader.getCharArray(start, stop));
			// 设置当前标签的的属性
			if(watchString.length() != 1) {
				value = "<%=" + value + "%>";
			}
			node.attribute(qName, value);
			reader.skipSpaces();
		} while (true);

	}

	public static void main(String[] args) throws Exception {
		Root root = JspParser.parser(new File("C:\\workspace\\sn-mvc\\WebContent\\index.jsp"), "UTF-8", "index.jsp");

		root.setPackageName("com.test");
		root.setClassName("Test");

		try (Writer _writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
			root.generator(_writer);
		}
	}
}
