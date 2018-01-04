/**   
 * Created the sn.mini.jsp.compiler.node.ScriptletNode.java
 * @created 2017年11月30日 下午12:05:05 
 * @version 1.0.0 
 */
package sn.mini.jsp.compiler.node;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.Writer;

import sn.mini.jsp.compiler.Mark;

/**   
 * 原生java 代码 &lt;% [code] %&gt;
 * @author XChao  
 */
public class Scriptlet extends Node{
	public Scriptlet(Mark start) {
		super(start);
	}

	public Scriptlet(Mark start, String text) {
		super(start, text);
	}

	@Override
	public void generator(Writer writer) throws Exception {
		try (BufferedReader reader = new BufferedReader(new StringReader(this.text()));) {
			String lines = null;
			while ((lines = reader.readLine()) != null) {
				writer.write("\t\t" + lines.trim() + " \r\n");
			}
		}
	}
}
