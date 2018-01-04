/**
 * Created the com.cfinal.web.core.CFAbstract.java
 * @created 2017年8月22日 上午9:29:36
 * @version 1.0.0
 */
package com.cfinal.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cfinal.util.cast.CFCastUtil;
import com.cfinal.util.logger.CFLog;

/**
 * com.cfinal.web.core.CFAbstract.java
 * @author XChao
 */
public interface CFService {

	default void onStartup(CFServletContext context) throws Exception {
		context.addBean(this);
		CFLog.debug("Scanner Service： " + this.getClass().getName());
	}

	default CFServletContext getContext() {
		return CFServletContext.getInstance();
	}

	default String getParameter(String name) {
		return getContext().getInitParameter(name);
	}

	default Long getParameterToLong(String name) {
		if(getParameter(name) == null) {
			return null;
		}
		return CFCastUtil.castLong(getParameter(name));
	}

	default Integer getParameterToInt(String name) {
		if(getParameter(name) == null) {
			return null;
		}
		return CFCastUtil.castInt(getParameter(name));
	}

	default Short getParameterToShort(String name) {
		if(getParameter(name) == null) {
			return null;
		}
		return CFCastUtil.castShort(getParameter(name));
	}

	default Byte getParameterToByte(String name) {
		if(getParameter(name) == null) {
			return null;
		}
		return CFCastUtil.castByte(getParameter(name));
	}

	default Double getParameterToDouble(String name) {
		if(getParameter(name) == null) {
			return null;
		}
		return CFCastUtil.castDouble(getParameter(name));
	}

	default Float getParameterToFloat(String name) {
		if(getParameter(name) == null) {
			return null;
		}
		return CFCastUtil.castFloat(getParameter(name));
	}

	default long getParameterToLongVal(String name) {
		return CFCastUtil.castLong(getParameter(name));
	}

	default int getParameterToIntVal(String name) {
		return CFCastUtil.castInt(getParameter(name));
	}

	default short getParameterToShortVal(String name) {
		return CFCastUtil.castShort(getParameter(name));
	}

	default byte getParameterToByteVal(String name) {
		return CFCastUtil.castByte(getParameter(name));
	}

	default double getParameterToDoubleVal(String name) {
		return CFCastUtil.castDouble(getParameter(name));
	}

	default float getParameterToFloatVal(String name) {
		return CFCastUtil.castFloat(getParameter(name));
	}

	//////////////////////////////////////////////////////////////////

	default String getParameter(HttpServlet servlet, String name) {
		return servlet.getInitParameter(name);
	}

	default Long getParameterToLong(HttpServlet servlet, String name) {
		if(getParameter(servlet, name) == null) {
			return null;
		}
		return CFCastUtil.castLong(getParameter(servlet, name));
	}

	default Integer getParameterToInt(HttpServlet servlet, String name) {
		if(getParameter(servlet, name) == null) {
			return null;
		}
		return CFCastUtil.castInt(getParameter(servlet, name));
	}

	default Short getParameterToShort(HttpServlet servlet, String name) {
		if(getParameter(servlet, name) == null) {
			return null;
		}
		return CFCastUtil.castShort(getParameter(servlet, name));
	}

	default Byte getParameterToByte(HttpServlet servlet, String name) {
		if(getParameter(servlet, name) == null) {
			return null;
		}
		return CFCastUtil.castByte(getParameter(servlet, name));
	}

	default Double getParameterToDouble(HttpServlet servlet, String name) {
		if(getParameter(servlet, name) == null) {
			return null;
		}
		return CFCastUtil.castDouble(getParameter(servlet, name));
	}

	default Float getParameterToFloat(HttpServlet servlet, String name) {
		if(getParameter(servlet, name) == null) {
			return null;
		}
		return CFCastUtil.castFloat(getParameter(servlet, name));
	}

	default long getParameterToLongVal(HttpServlet servlet, String name) {
		return CFCastUtil.castLong(getParameter(servlet, name));
	}

	default int getParameterToIntVal(HttpServlet servlet, String name) {
		return CFCastUtil.castInt(getParameter(servlet, name));
	}

	default short getParameterToShortVal(HttpServlet servlet, String name) {
		return CFCastUtil.castShort(getParameter(servlet, name));
	}

	default byte getParameterToByteVal(HttpServlet servlet, String name) {
		return CFCastUtil.castByte(getParameter(servlet, name));
	}

	default double getParameterToDoubleVal(HttpServlet servlet, String name) {
		return CFCastUtil.castDouble(getParameter(servlet, name));
	}

	default float getParameterToFloatVal(HttpServlet servlet, String name) {
		return CFCastUtil.castFloat(getParameter(servlet, name));
	}

	//////////////////////////////////////////////////////////////////

	default String getParameter(HttpServletRequest request, String name) {
		return request.getParameter(name);
	}

	default Long getParameterToLong(HttpServletRequest request, String name) {
		if(getParameter(request, name) == null) {
			return null;
		}
		return CFCastUtil.castLong(getParameter(request, name));
	}

	default Integer getParameterToInt(HttpServletRequest request, String name) {
		if(getParameter(request, name) == null) {
			return null;
		}
		return CFCastUtil.castInt(getParameter(request, name));
	}

	default Short getParameterToShort(HttpServletRequest request, String name) {
		if(getParameter(request, name) == null) {
			return null;
		}
		return CFCastUtil.castShort(getParameter(request, name));
	}

	default Byte getParameterToByte(HttpServletRequest request, String name) {
		if(getParameter(request, name) == null) {
			return null;
		}
		return CFCastUtil.castByte(getParameter(request, name));
	}

	default Double getParameterToDouble(HttpServletRequest request, String name) {
		if(getParameter(request, name) == null) {
			return null;
		}
		return CFCastUtil.castDouble(getParameter(request, name));
	}

	default Float getParameterToFloat(HttpServletRequest request, String name) {
		if(getParameter(request, name) == null) {
			return null;
		}
		return CFCastUtil.castFloat(getParameter(request, name));
	}

	default long getParameterToLongVal(HttpServletRequest request, String name) {
		return CFCastUtil.castLong(getParameter(request, name));
	}

	default int getParameterToIntVal(HttpServletRequest request, String name) {
		return CFCastUtil.castInt(getParameter(request, name));
	}

	default short getParameterToShortVal(HttpServletRequest request, String name) {
		return CFCastUtil.castShort(getParameter(request, name));
	}

	default byte getParameterToByteVal(HttpServletRequest request, String name) {
		return CFCastUtil.castByte(getParameter(request, name));
	}

	default double getParameterToDoubleVal(HttpServletRequest request, String name) {
		return CFCastUtil.castDouble(getParameter(request, name));
	}

	default float getParameterToFloatVal(HttpServletRequest request, String name) {
		return CFCastUtil.castFloat(getParameter(request, name));
	}

	default void setAttribute(String name, Object value) {
		getContext().setAttribute(name, value);
	}

	default void setAttribute(HttpSession session, String name, Object value) {
		session.setAttribute(name, value);
	}

	default void setAttribute(HttpServletRequest request, String name, Object value) {
		request.setAttribute(name, value);
	}

	default <T> T getAttribute(String name, Class<T> clazz) {
		return clazz.cast(getContext().getAttribute(name));
	}

	default <T> T getAttribute(HttpSession session, String name, Class<T> clazz) {
		return clazz.cast(session.getAttribute(name));
	}

	default <T> T getAttribute(HttpServletRequest request, String name, Class<T> clazz) {
		return clazz.cast(request.getAttribute(name));
	}
}
