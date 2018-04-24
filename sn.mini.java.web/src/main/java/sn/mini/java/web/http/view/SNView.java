/**
 * Created the sn.mini.java.web.http.view.SNView.java
 * @created 2017年11月28日 下午1:06:23
 * @version 1.0.0
 */
package sn.mini.java.web.http.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sn.mini.java.web.model.IModel;

/**
 * sn.mini.java.web.http.view.SNView.java
 * @author XChao
 */
public class SNView implements IView {
	// private final JspEngine engine;
	//
	// public SNView() {
	// JspConfig config = new JspConfig();
	// File jspSourceFile = new File(SNInitializer.getWebRealPath(), "WEB-INF");
	// File jspClassesFile = new File(jspSourceFile, "jsp_classes");
	// config.setJspSourceDir(StringUtil.urlDecode(jspSourceFile.getAbsolutePath(), //
	// SNInitializer.getEncoding()));
	// config.setJspClassesDir(StringUtil.urlDecode(jspClassesFile.getAbsolutePath(), //
	// SNInitializer.getEncoding()));
	// config.setJspEncoding(SNInitializer.getEncoding());
	// this.engine = JspEngine.createFileEngine(config);
	// }
	//
	// @Override
	// public final void generator(IModel model, String viewPath, HttpServletRequest request, HttpServletResponse response)
	// throws Exception {
	// JspSession session = new JspSession();
	// request.setAttribute("error", model.getError());
	// request.setAttribute("message", model.getMessage());
	// for (String key : model.keySet()) {
	// request.setAttribute(key, model.getData(key));
	// }
	// JspTemplate template = engine.geTemplate(viewPath + ".jsp");
	// try (Writer writer = response.getWriter();) {
	// template.merge(session, writer); //
	// }
	// }

	@Override
	public final void generator(IModel model, String viewPath, HttpServletRequest request, HttpServletResponse response) throws Exception {
		throw new RuntimeException("Unrealized method. ");
	}
}
