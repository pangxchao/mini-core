/**
 * Created the sn.mini.jsp.compiler.tag.ElseTag.java
 * @created 2017年12月12日 下午12:53:33
 * @version 1.0.0
 */
package sn.mini.jsp.compiler.tag;

import sn.mini.jsp.compiler.JspWriter;

/**
 * sn.mini.jsp.compiler.tag.ElseTag.java
 * @author XChao
 */
public class ElseTag extends JspTag {

	@Override
	public void doTag(JspWriter out) throws Exception {
		throw new RuntimeException("The else tag must be a if-else sublabel.");
	}

	protected void doElseTag(JspWriter out) throws Exception {
		this.writerBody(out);
	}

}
