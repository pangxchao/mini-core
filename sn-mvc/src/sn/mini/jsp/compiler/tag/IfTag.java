/**
 * Created the sn.mini.jsp.compiler.tag.IfTag.java
 * @created 2017年12月5日 下午2:46:37
 * @version 1.0.0
 */
package sn.mini.jsp.compiler.tag;

import sn.mini.jsp.compiler.JspWriter;

/**
 * sn.mini.jsp.compiler.tag.IfTag.java
 * @author XChao
 */
public class IfTag extends JspTag {

	private Object test;

	public void setTest(Object test) {
		System.out.println(test);
		this.test = test;
	}

	@Override
	public void doTag(JspWriter out) throws Exception {
		if(this.test == null) {
			return;
		}
		if(this.test instanceof String) {
			if(!"false".equals(this.test.toString().trim().toLowerCase())) {
				this.writerBody(out);
			}
		} else if(this.test instanceof Boolean) {
			if(((Boolean) this.test).booleanValue()) {
				this.writerBody(out);
			}
		} else if(this.test instanceof Number) {
			if(((Number) this.test).intValue() != 0) {
				this.writerBody(out);
			}
		} else {
			this.writerBody(out);
		}
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
