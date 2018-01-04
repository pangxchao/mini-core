/**
 * Created the sn.mini.jsp.JspTemplate.java
 * @created 2017年11月28日 上午11:59:10
 * @version 1.0.0
 */
package sn.mini.jsp;

import java.io.Writer;
import java.util.Map;
import java.util.function.Supplier;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import sn.mini.jsp.compiler.JspWriter;
import sn.mini.jsp.compiler.tag.JspTag;

/**
 * sn.mini.jsp.JspTemplate.java
 * @author XChao
 */
public abstract class JspTemplate {
	private JspEngine engine;

	public final JspEngine getEngine() {
		return engine;
	}

	public final JspTemplate setEngine(JspEngine engine) {
		this.engine = engine;
		return this;
	}

	/**
	 * 为解决标签生成时变量重复问题
	 * @param supplier
	 * @return
	 */
	protected final JspTag getJspTag(Supplier<JspTag> supplier) {
		return supplier.get();
	}

	public final void merge(JspSession session, Writer writer) {
		try (JspWriter out = new JspWriter(writer)) {
			this.service(session, out);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public abstract void service(final JspSession session, final JspWriter out) throws Exception;

	// 处理el表达式
	private static final ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
	private static final ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");

	public final Object getELExpression(String name, JspSession session, Map<String, Object> data) {
		try {
			return nashorn.eval(name, new SimpleBindings(data));
		} catch (ScriptException e) {
			return this.getELExpression(name, session);
		}
	}

	public final Object getELExpression(String name, JspSession session) {
		try {
			return nashorn.eval(name, new SimpleBindings(session));
		} catch (ScriptException e1) {
			return null;
		}
	}
}
