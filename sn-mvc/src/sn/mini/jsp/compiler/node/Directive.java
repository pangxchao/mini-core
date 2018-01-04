/**
 * Created the sn.mini.jsp.compiler.node.DirectiveNode.java
 * @created 2017年11月30日 上午10:59:18
 * @version 1.0.0
 */
package sn.mini.jsp.compiler.node;

import java.io.Writer;

import sn.mini.jsp.compiler.Mark;

/**
 * 动态命令  原生java 代码 &lt;@[actice] attr="" %&gt;
 * @author XChao
 */
public class Directive extends Node {
	
	public Directive(Mark start) {
		super(start);
	}
	
	@Override
	public void generator(Writer writer) throws Exception {
		if(this.attribute("import") != null) {
			writer.write("import ");
			writer.write(this.attribute("import"));
			writer.write(";\r\n");
		}
	}
}
