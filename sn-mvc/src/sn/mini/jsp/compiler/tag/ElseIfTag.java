/**
 * Created the sn.mini.jsp.compiler.tag.ElseIfTag.java
 * @created 2017年12月12日 下午12:52:22
 * @version 1.0.0
 */
package sn.mini.jsp.compiler.tag;

import sn.mini.jsp.compiler.JspWriter;

/**
 * sn.mini.jsp.compiler.tag.ElseIfTag.java
 * @author XChao
 */
public class ElseIfTag extends JspTag {

	private Object test;

	public void setTest(Object test) {
		this.test = test;
	}

	@Override
	public void doTag(JspWriter out) throws Exception {
		throw new RuntimeException("The elseif tag must be a if-else sublabel.");
	}

	protected boolean doTag() throws Exception {
		if(this.test == null) {
			return false;
		}
		if(this.test instanceof String && "false".equals(this.test.toString().trim().toLowerCase())) {
			return false;
		}
		if(this.test instanceof Boolean && !((Boolean) this.test).booleanValue()) {
			return false;
		}
		if(this.test instanceof Number && ((Number) this.test).intValue() == 0) {
			return false;
		}
		return true;
	}

}
