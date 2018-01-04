/**
 * Created the sn.mini.jsp.compiler.tag.IncludeTag.java
 * @created 2017年11月29日 上午11:03:21
 * @version 1.0.0
 */
package sn.mini.jsp.compiler.tag;

import sn.mini.jsp.compiler.JspWriter;

/**
 * sn.mini.jsp.compiler.tag.IncludeTag.java
 * @author XChao
 */
public final class IncludeTag extends JspTag {

	private String page;

	/**
	 * @return the page
	 */
	public String getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	@Override
	public void doTag(JspWriter out) throws Exception {
		this.engine().geTemplate(this.page).service(this.jspSession(), out);
	}
}
