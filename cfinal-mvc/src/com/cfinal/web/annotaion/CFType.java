/**
 * Created the com.cfinal.web.annotaion.CFType.java
 * @created 2017年4月25日 上午8:43:01
 * @version 1.0.0
 */
package com.cfinal.web.annotaion;

import com.cfinal.web.central.CFContext;
import com.cfinal.web.control.CFActionPorxy;
import com.cfinal.web.render.CFRender;
import com.cfinal.web.render.CFJsonRender;
import com.cfinal.web.render.CFPageRender;
import com.cfinal.web.render.CFStreamRender;

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
		public CFType setRender(CFActionPorxy actionPorxy, CFContext context) {
			actionPorxy.setRender(context.getRender(CFPageRender.class));
			return this;
		}
	},
	/**
	 * 系统通过ajax传回json数据，该json为一个json Object对象
	 */
	json {
		@Override
		public CFType setRender(CFActionPorxy actionPorxy, CFContext context) {
			actionPorxy.setRender(context.getRender(CFJsonRender.class));
			return this;
		}
	},
	/**
	 * 返回一个流文件对象
	 */
	stream {
		@Override
		public CFType setRender(CFActionPorxy actionPorxy, CFContext context) {
			actionPorxy.setRender(context.getRender(CFStreamRender.class));
			return this;
		}
	},
	/**
	 * 用户自定义视图类型，必须和 defined 联合使用
	 */
	defined {
		@Override
		public CFType setRender(CFActionPorxy actionPorxy, CFContext context) {
			try {
				Class<? extends CFRender> clazz = actionPorxy.getAction().defined();
				CFRender instence = context.getRender(clazz);
				if(instence == null) {
					context.addRender(clazz, clazz.newInstance());
				}
				actionPorxy.setRender(context.getRender(clazz));
				return this;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	};
	/**
	 * @param actionPorxy
	 * @param context
	 */
	public CFType setRender(CFActionPorxy actionPorxy, CFContext context) {
		return this;
	}
}
