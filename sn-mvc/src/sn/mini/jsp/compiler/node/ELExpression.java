/**
 * Created the sn.mini.jsp.compiler.node.ELExpression.java
 * @created 2017年11月30日 下午2:42:37
 * @version 1.0.0
 */
package sn.mini.jsp.compiler.node;

import java.io.Writer;

import sn.mini.jsp.compiler.Mark;

/**
 * EL表达式 , 目前只支持 ‘${}’语句的El表达式
 * @author XChao
 */
public class ELExpression extends JspETag {
	public ELExpression(Mark start) {
		super(start);
	}

	public ELExpression(Mark start, String text) {
		super(start, text);
	}

	@Override
	public void generator(Writer writer) throws Exception {
		StringBuilder builder = new StringBuilder();
		for (char ch : this.text().toCharArray()) {
			if(ch == '\"') {
				builder.append("\\\"");
			} else {
				builder.append(ch);
			}

		}
		writer.write("\t\tout.print(this.getELExpression(\"" + builder.toString() + "\", session, context)); \r\n");
	}

	@Override
	public String generatorETag(Writer writer) throws Exception {
		StringBuilder builder = new StringBuilder();
		for (char ch : this.text().toCharArray()) {
			if(ch == '\"') {
				builder.append("\\\"");
			} else {
				builder.append(ch);
			}

		}
		int ix = this.index();
		writer.write("\t\t \r\n");
		writer.write("\t\t// El 表达式标签开始 \r\n");
		writer.write("\t\tJspTag _jsp_tag_ix_" + ix + " = new sn.mini.jsp.compiler.tag.TextTag(); \r\n");
		writer.write("\t\t_jsp_tag_ix_" + ix + ".engine(getEngine()).jspSession(session).context(context);\r\n");
		writer.write("\t\t_jsp_tag_ix_" + ix + ".text(this.getELExpression(\"" //
			+ builder.toString() + "\", session, context)); \r\n");
		writer.write("\t\t// El 表达式标签结束 \r\n");
		writer.write("\t\t \r\n");

		return "_jsp_tag_ix_" + ix;
	}
}
