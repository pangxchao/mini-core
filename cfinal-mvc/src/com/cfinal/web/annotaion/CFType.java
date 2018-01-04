/**
 * Created the com.cfinal.web.annotaion.CFType.java
 * @created 2017年4月25日 上午8:43:01
 * @version 1.0.0
 */
package com.cfinal.web.annotaion;

import com.cfinal.web.CFServletContext;
import com.cfinal.web.http.render.CFJsonRender;
import com.cfinal.web.http.render.CFPageRender;
import com.cfinal.web.http.render.CFRender;
import com.cfinal.web.http.render.CFStreamRender;

/**
 * com.cfinal.web.annotaion.CFType.java
 * @author XChao
 */
public enum CFType {
	/**
	 * 系统通过页面返回给用户， 该页面根据配置，要以是任何可以作为视图渲染的页面
	 */
	page {
		@Override
		public CFRender getRender(CFAction action, CFServletContext context) {
			return context.getBean(CFPageRender.class);
		}
	},
	/**
	 * 系统通过ajax传回json数据，该json为一个json Object对象
	 */
	json {
		@Override
		public CFRender getRender(CFAction action, CFServletContext context) {
			return context.getBean(CFJsonRender.class);
		}
	},
	/**
	 * 返回一个流文件对象
	 */
	stream {
		public CFRender getRender(CFAction action, CFServletContext context) {
			return context.getBean(CFStreamRender.class);
		}
	},
	/**
	 * 用户自定义视图类型，必须和 defined 联合使用
	 */
	defined {
		public CFRender getRender(CFAction action, CFServletContext context) {
			return context.getBean(action.defined());
		}
	};
	/**
	 * @param actionPorxy
	 * @param context
	 */
	public CFRender getRender(CFAction action, CFServletContext context) {
		return context.getBean(CFPageRender.class);
	}
}
