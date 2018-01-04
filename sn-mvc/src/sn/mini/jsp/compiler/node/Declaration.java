/**
 * Created the sn.mini.jsp.compiler.node.DeclarationNode.java
 * @created 2017年11月30日 上午11:53:56
 * @version 1.0.0
 */
package sn.mini.jsp.compiler.node;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.Writer;

import sn.mini.jsp.compiler.Mark;

/**
 * 声明式代码 &lt;%! [code] %&gt;
 * @author XChao
 */
public class Declaration extends Node {

	public Declaration(Mark start) {
		super(start);
	}

	public Declaration(Mark start, String text) {
		super(start, text);
	}

	@Override
	public void generator(Writer writer) throws Exception {
		try (BufferedReader reader = new BufferedReader(new StringReader(this.text()));) {
			String lines = null;
			while ((lines = reader.readLine()) != null) {
				writer.write("\t" + lines.trim() + " \r\n ");
			}
		}
	}
}
