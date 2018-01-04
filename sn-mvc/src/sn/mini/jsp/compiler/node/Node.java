/**
 * Created the sn.mini.jsp.compiler.node.Node.java
 * @created 2017年11月29日 下午4:10:17
 * @version 1.0.0
 */
package sn.mini.jsp.compiler.node;

import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import sn.mini.jsp.compiler.Mark;

/**
 * sn.mini.jsp.compiler.node.Node.java
 * @author XChao
 */
public abstract class Node {
	private static int index = 0;
	private Mark start;
	private String qName;
	private Node parent;
	private String text;
	private final List<Node> children = new Vector<>();
	private final Map<String, String> attrs = new ConcurrentHashMap<>();

	public final int index() {
//		return start == null ? ++index : start.cursor();
		return ++index;
	}

	public Node(Mark start) {
		this.start = start;
	}

	public Node(Mark start, String text) {
		this.start = start;
		this.text = text;
	}

	public Mark start() {
		return this.start;
	}

	public void start(Mark start) {
		this.start = start;
	}

	public String qName() {
		return this.qName;
	}

	public void qName(String qName) {
		this.qName = qName;
	}

	public Node parent() {
		return parent;
	}

	public void parent(Node parent) {
		this.parent = parent;
	}

	public String text() {
		return text;
	}

	public void text(String text) {
		this.text = text;
	}

	public final Set<String> keySet() {
		return this.attrs.keySet();
	}

	public final String attribute(String name) {
		return this.attrs.get(name);
	}

	public final void attribute(String name, String value) {
		this.attrs.put(name.toLowerCase(), value);
	}

	public final void removeAttribute(String name) {
		this.attrs.remove(name);
	}

	public List<Node> children() {
		return children;
	}

	public void child(Node node) {
		this.children.add(node);
	}

	public final Node firstChild() {
		return this.children.size() > 0 ? this.children.get(0) : null;
	}

	public final Node lastChild() {
		return this.children.size() > 0 ? this.children.get(this.children.size() - 1) : null;
	}

	public abstract void generator(Writer writer) throws Exception;
}
