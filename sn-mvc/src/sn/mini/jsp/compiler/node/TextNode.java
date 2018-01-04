/**
 * Created the sn.mini.jsp.compiler.node.TextNode.java
 * @created 2017年11月30日 下午12:41:56
 * @version 1.0.0
 */
package sn.mini.jsp.compiler.node;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import sn.mini.jsp.compiler.Mark;

/**
 * Jsp文件节点， 除Jsp识别标签和动作之外的文件
 * @author XChao
 */
public class TextNode extends JspETag {
	public TextNode(Mark start) {
		super(start);
	}

	public TextNode(Mark start, String text) {
		super(start, text);
	}

	@Override
	public void generator(Writer writer) throws Exception {
		List<String> array = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new StringReader(this.text()));) {
			String lines = null;
			while ((lines = reader.readLine()) != null) {
				array.add(lines);
			}
		}
		for (int i = 0, len = array.size(); i < len; i++) {
			StringBuilder builder = new StringBuilder();
			for (char ch : array.get(i).toCharArray()) {
				if(ch == '\"') {
					builder.append("\\\"");
				} else {
					builder.append(ch);
				}
			}
			if(i < len - 1) {
				builder.append("\\r\\n");
			}
			writer.write("\t\tout.write(\"" + builder.toString() + "\"); \r\n ");
		}
	}

	@Override
	public String generatorETag(Writer writer) throws Exception {
		List<String> array = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new StringReader(this.text()));) {
			String lines = null;
			while ((lines = reader.readLine()) != null) {
				array.add(lines);
			}
		}

		int ix = this.index();
		writer.write("\t\t\r\n");
		writer.write("\t\t// 文本标签开始  \r\n");
		writer.write("\t\tStringBuilder _jsp_builder_" + ix + " = new StringBuilder(); \r\n");
		for (int i = 0, len = array.size(); i < len; i++) {
			StringBuilder builder = new StringBuilder();
			for (char ch : array.get(i).toCharArray()) {
				if(ch == '\"') {
					builder.append("\\\"");
				} else {
					builder.append(ch);
				}
			}
			if(i < len - 1) {
				builder.append("\\r\\n");
			}
			writer.write("\t\t_jsp_builder_" + ix + ".append(\"" + builder.toString() + "\"); \r\n ");
		}

		writer.write("\t\tJspTag _jsp_tag_ix_" + ix + " = new sn.mini.jsp.compiler.tag.TextTag(); \r\n");
		writer.write("\t\t_jsp_tag_ix_" + ix + ".engine(getEngine()).jspSession(session).context(context);\r\n");
		writer.write("\t\t_jsp_tag_ix_" + ix + ".text(_jsp_builder_" + ix + ".toString()); \r\n");
		writer.write("\t\t// 文本标签结束  \r\n");
		writer.write("\t\t\r\n");
		return "_jsp_tag_ix_" + ix;
	}
}
