/**
 * Created the com.cfinal.web.central.CFContext.java
 * @created 2016年9月25日 下午12:36:26
 * @version 1.0.0
 */
package com.cfinal.web.central;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.cfinal.web.control.CFActionPorxy;
import com.cfinal.web.interceptor.CFInterceptorPorxy;
import com.cfinal.web.preprocessor.CFPreprocessorPorxy;
import com.cfinal.web.render.CFRender;
import com.cfinal.web.scheduler.CFTaskPorxy;
import com.cfinal.web.view.CFViewPorxy;

/**
 * com.cfinal.web.central.CFContext.java
 * @author XChao
 */
public interface CFContext extends ServletContext, CFBasics {
	/**
	 * response.setHeader("Access-Control-Allow-Origin", "*") 处理
	 * @return
	 */
	public String getAccessControl();

	/**
	 * 设置跨域访问
	 * @param accessControlAllowOrigin
	 */
	public void setAccessControl(String accessControlAllowOrigin);

	/**
	 * 得到web应用的物理地址
	 * @return 得到web应用的实际物理根地址
	 */
	public String getWebRealPath();

	/**
	 * 获取web地址的根地址,该问该web项目的根URL
	 * @return web地址的根地址
	 */
	public String getWebRootPath();

	/**
	 * 设置web地址的根地址,该问该web项目的根URL
	 * @param webRootPath
	 */
	public void setWebRootPath(String webRootPath);

	/**
	 * 获取主键生成的机器码唯一ID
	 * @return the workerId
	 */
	public long getWorkerId();

	/**
	 * 设置获取主萧索生成的机器唯一码
	 * @param workerId
	 */
	public void setWorkerId(long workerId);

	/**
	 * 获取使用编码
	 * @return
	 */
	public String getEncoding();

	/**
	 * 设置编码
	 * @param encoding
	 */
	public void setEncoding(String encoding);

	/**
	 * 获取系统url访问后缀
	 * @return
	 */
	public String getActionSuffix();

	/**
	 * 设置系统Url访问后缀,设置后
	 * @param actionSuffix
	 */
	public void setActionSuffix(String actionSuffix);

	/**
	 * 获取一个初始化参数
	 * @param name
	 */
	public String getParameter(String name);

	/**
	 * 获取所有初始化参数
	 * @return
	 */
	public Map<String, String> getParameters();

	/**
	 * 添加一个初始化参数
	 * @param name 参数名称
	 * @param value 参数值
	 */
	public void addParameter(String name, String value);

	/**
	 * 获取初始化类
	 * @return
	 */
	public CFPreprocessorPorxy getPreprocessorPorxy();

	/**
	 * 设置初始化处理类
	 * @param handler
	 */
	public void setPreprocessorPorxy(CFPreprocessorPorxy preprocessor);

	/**
	 * 获取一个拦截器
	 * @param filterName
	 * @return
	 */
	public CFInterceptorPorxy getInterceptor(String interceptorName);

	/**
	 * 添加一个拦截器
	 * @param interceptorName
	 * @param interceptorPorxy
	 */
	public void addInterceptor(String interceptorName, CFInterceptorPorxy interceptorPorxy);

	/**
	 * 添加一个拦截器堆栈
	 * @param name
	 * @return
	 */
	public List<String> getInterceptorStack(String name);

	/**
	 * 添加一个拦截器堆栈
	 * @param name
	 * @param interceptorStack
	 */
	public void addInterceptorStack(String name, List<String> interceptorStack);

	/**
	 * 获取一个定时任务
	 * @param taskName
	 * @return
	 */
	public CFTaskPorxy getTask(String taskName);

	/**
	 * 添加一个定时任务
	 * @param taskName
	 * @param taskPorxy
	 */
	public void addTask(String taskName, CFTaskPorxy taskPorxy);

	/**
	 * 得到一个Action 映射
	 * @param actionName
	 * @return
	 */
	public CFActionPorxy getAction(String actionName);

	/**
	 * 获取所有的 Action 映射
	 * @return
	 */
	public Map<String, CFActionPorxy> getActionMap();

	/**
	 * 添加一个Action 映射
	 * @param actionName
	 * @param actionPorxy
	 */
	public void addAction(String actionName, CFActionPorxy actionPorxy);

	/**
	 * 获取一个处理器
	 * @param clazz
	 * @return
	 */
	public CFRender getRender(Class<? extends CFRender> clazz);

	/**
	 * 添加一个处理器
	 * @param clazz
	 * @param render
	 */
	public void addRender(Class<? extends CFRender> clazz, CFRender render);

	/**
	 * 得到渲染页面的引擎
	 * @param viewName
	 * @return IView
	 */
	public CFViewPorxy getView(String viewName);

	/**
	 * 得到渲染页面的引擎
	 * @param viewName
	 */
	public void addView(String viewName, CFViewPorxy viewPorxy);

	/**
	 * 获取默认的视渲染器名称
	 * @return
	 */
	public String getDefaultView();

	/**
	 * 设置默认的视渲染器名称
	 * @param defaultView
	 */
	public void setDefaultView(String defaultView);

	/**
	 * 设置文件上传配置中， 上传时的 buffer 区大小
	 * @param sizeThreshold
	 */
	public void setFileUploadSizeThreshold(int sizeThreshold);

	/**
	 * 获取文件上传配置中， 上传时 buffer 区大小配置
	 * @return
	 */
	public int getFileUploadSizeThreshold();

	/**
	 * 设置文件上传配置中，上传临时目录
	 * @param repository
	 */
	public void setFileUploadRepository(String repository);

	/**
	 * 获取文件上传配置中， 上传临时目录
	 * @return
	 */
	public String getFileUploadRepository();

	/**
	 * 设置文件上传配置中，一次请求能上传的最大文件限制
	 * @param sizemax
	 */
	public void setFileUploadSizeMax(long sizeMax);

	/**
	 * 获取文件上传配置中，一次请求能上的最大文件限制
	 * @return
	 */
	public long getFileUploadSizeMax();

	/**
	 * 获取登录页面地址
	 * @return
	 */
	public String getLoginUrl();

	/**
	 * 设置登录页面地址
	 * @param loginUrl
	 */
	public void setLoginUrl(String loginUrl);
}
