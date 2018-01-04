/**
 * Created the sn.mini.jsp.compiler.node.ExpressionNode.java
 * @created 2017年11月30日 下午12:01:22
 * @version 1.0.0
 */
package sn.mini.jsp.compiler.node;

import java.io.Writer;

import sn.mini.jsp.compiler.Mark;

/**
 * 原生java输入 代码 &lt;%= [code] %&gt;
 * @author XChao
 */
public class Expression extends JspETag {

	public Expression(Mark start) {
		super(start);
	}

	public Expression(Mark start, String text) {
		super(start, text);
	}

	@Override
	public void generator(Writer writer) throws Exception {
		writer.write("\t\tout.print(" + this.text() + "); \r\n");
	}

	@Override
	public String generatorETag(Writer writer) throws Exception {
		int ix = this.index();
		writer.write("\t\t\r\n");
		writer.write("\t\t// <%= %> 输入标签开始 \r\n");
		writer.write("\t\tJspTag _jsp_tag_ix_" + ix + " = new sn.mini.jsp.compiler.tag.TextTag(); \r\n");
		writer.write("\t\t_jsp_tag_ix_" + ix + ".engine(getEngine()).jspSession(session).context(context);\r\n");
		writer.write("\t\t_jsp_tag_ix_" + ix + ".text(" + this.text() + "); \r\n");
		writer.write("\t\t// <%= %> 输入标签结束\r\n");
		writer.write("\t\t\r\n");
		return "_jsp_tag_ix_" + ix;
	}

}
