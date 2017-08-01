/**
 * Created the com.cfinal.web.central.CFInvocation.java
 * @created 2016年9月28日 上午11:38:53
 * @version 1.0.0
 */
package com.cfinal.web.central;

import javax.servlet.http.HttpSession;

import com.cfinal.db.CFDB;
import com.cfinal.web.CFRequest;
import com.cfinal.web.CFResponse;
import com.cfinal.web.annotaion.CFAction;
import com.cfinal.web.entity.CFUser;
import com.cfinal.web.model.CFModel;

/**
 * Action 调用类。所有的 Action 调用都有该类执行。
 * @author XChao
 */
public interface CFInvocation extends CFBasics{

	/**
	 * 获取Action名称
	 * @return
	 */
	public String getName();

	/**
	 * 获取Action 注解信息
	 * @return
	 */
	public CFAction getAction();

	/**
	 * 获取 HttpServletRequest
	 * @return
	 */
	public CFRequest getRequest();

	/**
	 * 设置HttpServletRequest
	 * @param request
	 */
	public void setRequest(CFRequest request);

	/**
	 * 获取 HttpServletResponse
	 * @return
	 */
	public CFResponse getResponse();

	/**
	 * 设置 HttpServletResponse
	 * @param response
	 */
	public void setResponse(CFResponse response);

	/**
	 * 获取 HttpSession
	 * @return
	 */
	public HttpSession getSession();

	/**
	 * 获取 IMolde 数据
	 * @return
	 */
	public CFModel getModel();

	/**
	 * 获取数据库访问对象
	 * @return
	 */
	public CFDB getDB(String name);

	/**
	 * 获取用户登录Session信息
	 * @return
	 */
	public CFUser getUser();

	/**
	 * 获取Action的参数信息
	 * @return
	 */
	public CFParameter getParameters();

	/**
	 * 根据参数名称 获取Action该参数的值
	 * @param name
	 * @param clazz
	 * @return
	 */
	public <T> T getParameter(String name, Class<T> clazz);

	/**
	 * 执行Action方法
	 * @return
	 * @throws Exception
	 */
	public String invoke() throws Exception;

}
