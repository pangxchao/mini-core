/**
 * Created the sn.mini.jsp.JspConfig.java
 * @created 2017年11月28日 上午11:58:37
 * @version 1.0.0
 */
package sn.mini.jsp;

import java.util.Properties;

/**
 * sn.mini.jsp.JspConfig.java
 * @author XChao
 */
public class JspConfig extends Properties {
	private static final long serialVersionUID = 7434036483154628811L;
	public static final String JSP_SOURCE_DIR = "jsp.source.dir";
	public static final String JSP_CLASSES_DIR = "jsp.classes.dir";
	public static final String JSP_ENCODING = "jsp.encoding";

	public String getJspSourceDir() {
		return this.getProperty(JSP_SOURCE_DIR);
	}

	public void setJspSourceDir(String jspSourceDir) {
		this.setProperty(JSP_SOURCE_DIR, jspSourceDir);
	}

	public String getJspClassesDir() {
		return this.getProperty(JSP_CLASSES_DIR);
	}

	public void setJspClassesDir(String jspClassesDir) {
		this.setProperty(JSP_CLASSES_DIR, jspClassesDir);
	}

	public String getJspEncoding() {
		return this.getProperty(JSP_ENCODING);
	}

	public void setJspEncoding(String jspEncoding) {
		this.setProperty(JSP_ENCODING, jspEncoding);
	}
}
