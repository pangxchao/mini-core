/**
 * Created the sn.mini.jsp.compiler.node.StandardAction.java
 * @created 2017年11月30日 下午12:10:41
 * @version 1.0.0
 */
package sn.mini.jsp.compiler.node;

import java.io.Writer;

import sn.mini.jsp.compiler.Mark;
import sn.mini.jsp.compiler.tag.JspTag;

/**
 * Jsp标签 ， &lt;jsp:name p=""&gt; &lt;/jsp:name&gt; 形式的标签 <br/>
 * 或者 &lt;jsp:name p="" /&gt; 形式的标签
 * @author XChao
 */
public class ETag extends JspETag {
	private final Class<? extends JspTag> classes;

	public ETag(Mark start, Class<? extends JspTag> classes) {
		super(start);
		this.classes = classes;
	}

	@Override
	public final void generator(Writer writer) throws Exception {
		int ix = this.index();
		writer.write("\t\t \r\n");
		writer.write("\t\t// <jsp:tag 自定义标签开始 \r\n");
		writer.write("\t\tJspTag _jsp_tag_ix_" + ix + " = new " + this.classes.getName() + "(); \r\n");
		writer.write("\t\t_jsp_tag_ix_" + ix + ".engine(getEngine()).jspSession(session).context(context);\r\n");
		for (String name : this.keySet()) {
			String value = this.attribute(name);
			if(value == null) {
				value = "null";
			}
			value = value.trim();
			if(value.startsWith("${") && value.endsWith("}")) {
				value = value.substring(2, value.length() - 1);
				StringBuilder builder = new StringBuilder();
				for (char ch : value.toCharArray()) {
					if(ch == '\"') {
						builder.append("\\\"");
					} else {
						builder.append(ch);
					}

				}
				value = "this.getELExpression(\"" + builder.toString() + "\", session, context)";
			} else if(value.startsWith("<%=") && value.endsWith("%>")) {
				value = value.substring(3, value.length() - 2);
			} else {
				value = "\"" + value + "\"";
			}
			writer.write("\t\t_jsp_tag_ix_" + ix + ".attribute(\"" + name + "\", " + value + "); \r\n");
		}
		for (Node node : this.children()) {
			if(node instanceof JspETag) {
				writer.write("\t\t \r\n");
				String child_name = ((JspETag) node).generatorETag(writer);
				writer.write("\t\t// 将子标签加入自定义标签的children \r\n");
				writer.write("\t\t_jsp_tag_ix_" + ix + ".addChild(" + child_name + "); \r\n ");
				writer.write("\t\t \r\n");
			} else {
				node.generator(writer);
			}
		}
		writer.write("\t\t// 调用自定义标签的doTag方法 \r\n");
		writer.write("\t\t_jsp_tag_ix_" + ix + ".doTag(out); \r\n");
		writer.write("\t\t// <jsp:tag 自定义标签结束 \r\n");
		writer.write("\t\t \r\n");
	}

	@Override
	public String generatorETag(Writer writer) throws Exception {
		int ix = this.index();
		writer.write("\t\t \r\n");
		writer.write("\t\t// <jsp:tag 自定义标签开始 \r\n");
		writer.write("\t\tJspTag _jsp_tag_ix_" + ix + " = new " + this.classes.getName() + "(); \r\n");
		writer.write("\t\t_jsp_tag_ix_" + ix + ".engine(getEngine()).jspSession(session).context(context);\r\n");
		for (String name : this.keySet()) {
			String value = this.attribute(name);
			if(value == null) {
				value = "null";
			}
			value = value.trim();
			if(value.startsWith("${") && value.endsWith("}")) {
				value = value.substring(2, value.length() - 1);
				StringBuilder builder = new StringBuilder();
				for (char ch : value.toCharArray()) {
					if(ch == '\"') {
						builder.append("\\\"");
					} else {
						builder.append(ch);
					}

				}
				value = "this.getELExpression(\"" + builder.toString() + "\", session, context)";
			} else if(value.startsWith("<%=") && value.endsWith("%>")) {
				value = value.substring(3, value.length() - 2);
			} else {
				value = "\"" + value + "\"";
			}

			writer.write("\t\t_jsp_tag_ix_" + ix + ".attribute(\"" + name + "\", " + value + "); \r\n");
		}
		for (Node node : this.children()) {
			if(node instanceof JspETag) {
				writer.write("\t\t_jsp_tag_ix_" + ix + ".addChild(" //
					+ ((JspETag) node).generatorETag(writer) + "); \r\n ");
			} else {
				node.generator(writer);
			}
		}
		writer.write("\t\t// <jsp:tag 自定义标签结束 \r\n");
		writer.write("\t\t \r\n");
		return "_jsp_tag_ix_" + ix;
	}

}
