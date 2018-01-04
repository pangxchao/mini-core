/**
 * Created the sn.mini.jsp.compiler.node.JspETag.java
 * @created 2017年12月4日 下午6:18:45
 * @version 1.0.0
 */
package sn.mini.jsp.compiler.node;

import java.io.Writer;

import sn.mini.jsp.compiler.Mark;

/**
 * sn.mini.jsp.compiler.node.JspETag.java
 * @author XChao
 */
public abstract class JspETag extends Node {
	public JspETag(Mark start) {
		super(start);
	}

	public JspETag(Mark start, String text) {
		super(start, text);
	}

	public abstract String generatorETag(Writer writer) throws Exception;
}
