/**
 * Created the sn.mini.jsp.compiler.tag.TextTag.java
 * @created 2017年12月4日 下午5:38:15
 * @version 1.0.0
 */
package sn.mini.jsp.compiler.tag;

import sn.mini.jsp.compiler.JspWriter;

/**
 * sn.mini.jsp.compiler.tag.TextTag.java
 * @author XChao
 */
public final class TextTag extends JspTag {

	@Override
	public void doTag(JspWriter out) throws Exception {
		out.print(this.text());
	}

}
