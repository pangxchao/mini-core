/**
 * Created the com.cfinal.web.central.CFBasics.java
 * @created 2017年7月17日 下午4:06:50
 * @version 1.0.0
 */
package com.cfinal.web.central;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cfinal.util.cast.CFCastUtil;

/**
 * com.cfinal.web.central.CFBasics.java
 * @author XChao
 */
public interface CFBasics {
	/**
	 * 获取系统全局上下文
	 * @return the context
	 */
	default CFContext getContext() {
		return CFInitialize.getContext();
	}

	/**
	 * 根据名称， 获取初始化参数
	 * @param name
	 * @return
	 */
	default String init(String name) {
		return getContext().getParameter(name);
	}

	/**
	 * 根据名称， 获取初始化参数，并转换成指定类型
	 * @param name
	 * @param clazz
	 * @return 返回类型只能为8种基础类型和Object类型
	 */
	default <T> T init(String name, Class<T> clazz) {
		return CFCastUtil.cast(getContext().getParameter(name), clazz);
	}

	default int init2int(String name) {
		return CFCastUtil.cast(getContext().getParameter(name), int.class);
	}

	/**
	 * HttpSession.setAttribute(name, value);
	 * @param session
	 * @param name
	 * @param value
	 */
	default void attr(HttpSession session, String name, Object value) {
		session.setAttribute(name, value);
	}

	/**
	 * HttpSession.getAttribute(name);
	 * @param session
	 * @param name
	 * @return
	 */
	default Object attr(HttpSession session, String name) {
		return session.getAttribute(name);
	}

	/**
	 * Class.cast(HttpSession.getAttribute(name))
	 * @param session
	 * @param name
	 * @param clazz
	 * @return
	 */
	default <T> T attr(HttpSession session, String name, Class<T> clazz) {
		return clazz.cast(session.getAttribute(name));
	}

	/**
	 * HttpServletRequest.setAttribute(name, value);
	 * @param request
	 * @param name
	 * @param value
	 */
	default void attr(HttpServletRequest request, String name, Object value) {
		request.setAttribute(name, value);
	}

	/**
	 * HttpServletRequest.getAttribute(name);
	 * @param request
	 * @param name
	 * @return
	 */
	default Object attr(HttpServletRequest request, String name) {
		return request.getAttribute(name);
	}

	/**
	 * Class.cast(HttpServletRequest.getAttribute(name))
	 * @param request
	 * @param name
	 * @param clazz
	 * @return
	 */
	default <T> T attr(HttpServletRequest request, String name, Class<T> clazz) {
		return clazz.cast(request.getAttribute(name));
	}

	/**
	 * HttpServletRequest.getParameter(name);
	 * @param session
	 * @param name
	 * @return
	 */
	default String param(HttpServletRequest request, String name) {
		return request.getParameter(name);
	}

	default <T> T param(HttpServletRequest request, String name, Class<T> clazz) {
		return CFCastUtil.cast(param(request, name), clazz);
	}

	/**
	 * HttpServletRequest.getParameter(name);
	 * @param session
	 * @param name
	 * @return
	 */
	default int param2int(HttpServletRequest request, String name) {
		return CFCastUtil.castInt(request.getParameter(name));
	}

	/**
	 * HttpServletRequest.getParameter(name);
	 * @param session
	 * @param name
	 * @return
	 */
	default long param2long(HttpServletRequest request, String name) {
		return CFCastUtil.castLong(request.getParameter(name));
	}

	/**
	 * HttpServletRequest.getParameter(name);
	 * @param session
	 * @param name
	 * @return
	 */
	default float param2float(HttpServletRequest request, String name) {
		return CFCastUtil.castFloat(request.getParameter(name));
	}

	/**
	 * HttpServletRequest.getParameter(name);
	 * @param session
	 * @param name
	 * @return
	 */
	default double param2double(HttpServletRequest request, String name) {
		return CFCastUtil.castDouble(request.getParameter(name));
	}

	/**
	 * HttpServletRequest.getParameter(name);
	 * @param session
	 * @param name
	 * @return
	 */
	default boolean param2boolean(HttpServletRequest request, String name) {
		return CFCastUtil.castBoolean(request.getParameter(name));
	}

	/**
	 * HttpServletRequest.getParameter(name);
	 * @param session
	 * @param name
	 * @return
	 */
	default short param2short(HttpServletRequest request, String name) {
		return CFCastUtil.castShort(request.getParameter(name));
	}

	/**
	 * HttpServletRequest.getParameter(name);
	 * @param session
	 * @param name
	 * @return
	 */
	default byte param2byte(HttpServletRequest request, String name) {
		return CFCastUtil.castByte(request.getParameter(name));
	}
}
