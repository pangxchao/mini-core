/**
 * Created the sn.mini.jsp.engine.FileJspEngine.java
 * @created 2017年11月28日 下午1:03:12
 * @version 1.0.0
 */
package sn.mini.jsp.engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import sn.mini.jsp.JspConfig;
import sn.mini.jsp.JspContext;
import sn.mini.jsp.JspEngine;
import sn.mini.jsp.JspTemplate;
import sn.mini.jsp.compiler.JspParser;
import sn.mini.jsp.compiler.node.Root;
import sn.mini.util.javac.JdkCompilerUtil;
import sn.mini.util.lang.ClassUtil;
import sn.mini.util.lang.StringUtil;

/**
 * sn.mini.jsp.engine.FileJspEngine.java
 * @author XChao
 */
public class FileJspEngine extends JspEngine {
	private final Map<String, JspContext> contextMap = new ConcurrentHashMap<>();

	public FileJspEngine(JspConfig config) throws IOException, Exception {
		super(config);
	}

	@Override
	protected JspContext getContext(String jspFile) {
		if(StringUtil.isBlank(jspFile) || !jspFile.toLowerCase().endsWith(".jsp")) {
			throw new RuntimeException("The jsp path does not end with '.jsp'. ");
		}
		JspContext context = contextMap.get(jspFile);
		if(context == null || !context.isLatest()) {
			context = compileAndLoad(jspFile);
			contextMap.put(jspFile, context);
		}
		return context;
	}

	private JspContext compileAndLoad(String jspFile) {
		try {
			File classDir = new File(getConfig().getJspClassesDir()); // class java 公共路径
			File jspSourceDir = new File(getConfig().getJspSourceDir()); // jsp原文件公共路径

			StringTokenizer tokenizer = new StringTokenizer(jspFile, "/\\");
			List<String> names = new ArrayList<String>();
			for (; tokenizer.hasMoreTokens();) {
				names.add(tokenizer.nextToken());
			}
			String jspName = names.get(names.size() - 1); // jsp 文件名
			String javaClassName = getJavaClassName(jspName); // java类开

			names.remove(names.size() - 1); // 删除最后一级名称（jsp文件名）
			String javaPackName = getJavaPackName(names); // 获取 java包名

			File classParent = new File(classDir, StringUtil.join(names, "/")); // class文件所有文件夹路径
			if(!classParent.exists() && classParent.mkdirs()) {
			} // 如果class文件所在路径不存在，则创建

			JspContext context = new JspContext();
			context.setName(javaClassName); // java 类名 java 文件名
			context.setPack(javaPackName); // java 类包名， jsp文件相对路径
			context.setClazz(new File(classParent, javaClassName + ".class"));
			context.setSordir(classParent); // 存放 class/java 文件的公共文件夹地址
			context.setJspdir(jspSourceDir); // jsp 文件公共地址，jsp所有最上层地址
			context.setJavair(new File(classParent, javaClassName + ".java"));
			context.setJspFile(new File(jspSourceDir, jspFile)); // jsp文件绝对路径
			Root root = JspParser.parser(context.getJspFile(), getConfig().getJspEncoding(), jspFile);
			root.setClassName(context.getName());
			root.setPackageName(context.getPack());

			try (Writer writer = new OutputStreamWriter(new FileOutputStream(context.getJavair()),
				getConfig().getJspEncoding())) {
				root.generator(writer);
			}

			Class<?> classes = JdkCompilerUtil.compile(javaPackName, javaClassName, context.getJavair(),
				getConfig().getJspEncoding());
			context.setTemplate(ClassUtil.newInstence(classes, JspTemplate.class));
			return context;
		} catch (Throwable e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 通过传入jsp文件名， 获取对应jsp的java类名
	 * @param jspFile
	 * @return
	 */
	private static String getJavaClassName(String jspName) {
		StringBuilder builder = new StringBuilder("SN_");
		for (char ch : jspName.toCharArray()) {
			if(Character.isLetterOrDigit(ch) || ch == '_') {
				builder.append(ch);
			} else {
				builder.append(Integer.toHexString(ch));
			}
		}
		return builder.toString();
	}

	/**
	 * 通过传入名称获取jsp的包名
	 * @param jspFile
	 * @return
	 */
	private static String getJavaPackName(List<String> jspPaths) {
		for (int i = 0; i < jspPaths.size(); i++) {
			StringBuilder builder = new StringBuilder("");
			for (char ch : jspPaths.get(i).toCharArray()) {
				if(Character.isLetterOrDigit(ch) || ch == '_') {
					builder.append(ch);
				} else {
					builder.append(Integer.toHexString(ch));
				}
			}
			if(keywords.contains(builder.toString())) {
				jspPaths.set(i, builder.toString() + "0");
			} else {
				jspPaths.set(i, builder.toString());
			}
		}
		return StringUtil.join(jspPaths, ".");
	}

	private static List<String> keywords = Arrays.asList("abstract", "boolean", "break", "byte", "case", "catch",
		"char", "class", "const", "continue", "default", "do", "double", "else", "extends", "final", "finally", "float",
		"for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new",
		"package", "private", "protected", "public", "return", "short", "static", "super", "switch", "synchronized",
		"this", "throw", "throws", "transient", "try", "void", "volatile", "while");
}
