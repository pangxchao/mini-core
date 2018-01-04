/**
 * Created the sn.mini.jsp.compiler.node.Root.java
 * @created 2017年11月29日 下午4:25:41
 * @version 1.0.0
 */
package sn.mini.jsp.compiler.node;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import sn.mini.jsp.compiler.Mark;

/**
 * 根节点， jsp 文档
 * @author XChao
 */
public final class Root extends Node {
	private final List<Node> imports = new ArrayList<>();
	private final List<Node> declaration = new ArrayList<>();

	private String packageName;
	private String className;
	
	public Root(Mark start) {
		super(start);
	}

	/**
	 * @return the imports
	 */
	public List<Node> getImports() {
		return imports;
	}

	public void addImport(Node node) {
		this.imports.add(node);
	}

	/**
	 * @return the declaration
	 */
	public List<Node> getDeclaration() {
		return declaration;
	}

	public void addDeclaration(Node node) {
		this.declaration.add(node);
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public void generator(Writer writer) throws Exception {
		writer.write("package ");
		writer.write(this.packageName);
		writer.write("; \r\n\r\n");

		writer.write("import java.util.Map; \r\n");
		writer.write("import java.util.HashMap; \r\n");
		writer.write("import sn.mini.jsp.JspSession; \r\n");
		writer.write("import sn.mini.jsp.compiler.JspWriter; \r\n");
		writer.write("import sn.mini.jsp.compiler.tag.JspTag; \r\n");
		for (Node node : this.imports) {
			node.generator(writer);
		}
		writer.write("\r\n");

		writer.write("public class ");
		writer.write(className);
		writer.write(" extends sn.mini.jsp.JspTemplate { \r\n");
		
		
		writer.write("\tprivate final Map<String, Object> context = new HashMap<>();\r\n");
		for (Node node : this.declaration) {
			node.generator(writer);
		}

		writer.write("\tpublic void service(final JspSession session, final JspWriter out) " //
			+ " throws Exception { \r\n");
		for (Node node : this.children()) {
			node.generator(writer);
		}
		writer.write("\t}\r\n");
		writer.write("}\r\n");
	}
}
