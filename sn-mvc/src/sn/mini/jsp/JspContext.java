/**
 * Created the sn.mini.jsp.JspContext.java
 * @created 2017年11月28日 上午11:58:54
 * @version 1.0.0
 */
package sn.mini.jsp;

import java.io.File;

/**
 * sn.mini.jsp.JspContext.java
 * @author XChao
 */
public final class JspContext {

	private File clazz; // .class 文件
	private File sordir; // 存放 class/java 文件的公共文件夹地址
	private File jspdir; // jsp文件公共地址
	private File javair; // java 文件绝对地址
	private File jspFile; // jsp 文件绝对路径
	private String pack; // jsp文件名包
	private String name; // java 类名 java 文件名
	private JspTemplate template; // 模板类实例

	public boolean isLatest() {
		return jspFile != null && clazz != null && //
			clazz.lastModified() > jspFile.lastModified();
	}

	public File getClazz() {
		return clazz;
	}

	public void setClazz(File clazz) {
		this.clazz = clazz;
	}

	public File getSordir() {
		return sordir;
	}

	public void setSordir(File sordir) {
		this.sordir = sordir;
	}

	public File getJspdir() {
		return jspdir;
	}

	public void setJspdir(File jspdir) {
		this.jspdir = jspdir;
	}

	public File getJavair() {
		return javair;
	}

	public void setJavair(File javair) {
		this.javair = javair;
	}

	public File getJspFile() {
		return jspFile;
	}

	public void setJspFile(File jspFile) {
		this.jspFile = jspFile;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JspTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JspTemplate template) {
		this.template = template;
	}
}
