/**
 * Created the sn.mini.jsp.JspEngine.java
 * @created 2017年11月28日 上午11:58:00
 * @version 1.0.0
 */
package sn.mini.jsp;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import sn.mini.jsp.compiler.tag.JspTag;
import sn.mini.jsp.engine.FileJspEngine;

/**
 * sn.mini.jsp.JspEngine.java
 * @author XChao
 */
public abstract class JspEngine {
	private static final Map<String, Class<? extends JspTag>> bind = //
		new ConcurrentHashMap<String, Class<? extends JspTag>>() {
			private static final long serialVersionUID = 9020419700762802899L;
			{
				put("include", sn.mini.jsp.compiler.tag.IncludeTag.class);
				put("for", sn.mini.jsp.compiler.tag.ForTag.class);
				put("if-else", sn.mini.jsp.compiler.tag.IfElseTag.class);
				put("if", sn.mini.jsp.compiler.tag.IfTag.class);
				put("elseif", sn.mini.jsp.compiler.tag.ElseIfTag.class);
				put("else", sn.mini.jsp.compiler.tag.ElseTag.class);

			}
		};

	/**
	 * 获取所有支持的标签名称
	 * @return
	 */
	public static Set<String> tagKetSet() {
		return JspEngine.bind.keySet();
	}

	/**
	 * 获取标签名称
	 * @param tagName
	 * @return
	 */
	public static Class<? extends JspTag> getTag(String tagName) {
		return JspEngine.bind.get(tagName.toLowerCase());
	}

	/**
	 * 添加标签支持
	 * @param tagName
	 * @param clazz
	 */
	public static void addTag(String tagName, Class<? extends JspTag> clazz) {
		if(JspEngine.bind.containsKey(tagName)) {
			throw new RuntimeException("The tagName has already existed.");
		}
		JspEngine.bind.put(tagName, clazz);
	}

	/****************************** 实例部分 ******************************************/

	private final JspConfig config;

	protected JspEngine(JspConfig config) {
		this.config = config;
	}

	public final JspConfig getConfig() {
		return this.config;
	}

	public final JspTemplate geTemplate(String path) {
		return this.getContext(path).getTemplate().setEngine(this);
	}

	protected abstract JspContext getContext(String path);

	/**
	 * 创建文件实现方式引擎系统
	 * @param config
	 * @return
	 */
	public static JspEngine createFileEngine(JspConfig config) {
		try {
			return new FileJspEngine(config);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
