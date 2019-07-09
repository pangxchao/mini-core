/**
 * Created the sn.mini.java.web.SNConfig.java
 * @created 2017年10月9日 下午6:07:03
 * @version 1.0.0
 */
package sn.mini.java.web;

import java.util.Optional;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import sn.mini.java.jdbc.DaoManager;
import sn.mini.java.util.PKGenerator;
import sn.mini.java.util.lang.StringUtil;
import sn.mini.java.util.lang.TypeUtil;
import sn.mini.java.web.http.view.IView;
import sn.mini.java.web.util.WebUtil;

/**
 * 配置类使用注意： <br/>
 * 1. 配置类是在程序启动之前执行的，一般用于读取程序配置，绑定第三方框架的使用<br/>
 * 2. 自定义多个配置类时，如果需要按一定顺序执行时，请按顺序继承。比如：<br/>
 * &nbsp; &nbsp;class A extends SNConfiguration<br/>
 * &nbsp; &nbsp;class B extends A<br/>
 * &nbsp; &nbsp;class C extends B<br/>
 * &nbsp; &nbsp;执行顺序为SNConfiguration > A > B > C <br/>
 * &nbsp; &nbsp;如果class C extheds A 的话， B、C两个类的执行先后顺序就无法保证了<br/>
 * 3. 重写initialize(SNServletContext context)方法时，不要调用super内容<br/>
 * 4. 尽量不要在不同的实现类里面设置相同的内容（除非特殊需要），以免后面执行的代码覆盖了前面执行的代码<br/>
 * @author XChao<br/>
 */
public class SNConfig {
	/**
	 * 设置系统编码
	 * @param encoding
	 */
	protected void setEncoding(String encoding) {
		SNInitializer.setEncoding(encoding);
	}

	/**
	 * 设置文件缓冲区大小
	 * @param value
	 */
	protected void setFileSizeThreshold(int value) {
		SNInitializer.setFileSizeThreshold(value);
	}

	/**
	 * 设置文件上传时所有文件大小限制
	 * @param value
	 */
	protected void setMaxRequestSize(long value) {
		SNInitializer.setMaxRequestSize(value);
	}

	/**
	 * 设置文件上传时，单个文件大小限制
	 * @param value
	 */
	protected void setMaxFileSize(long value) {
		SNInitializer.setMaxFileSize(value);
	}

	/**
	 * 设置文件上传的临时目录（如果该目录不存在，post提交的数据或能无法获取参数）
	 * @param value
	 */
	protected void setLocation(String value) {
		SNInitializer.setLocation(value);
	}

	/**
	 * 设置登录地址， 该地址用于登录验证时不通过时的跳转地址
	 * @param loginUrl
	 */
	protected void setLoginUrl(String loginUrl) {
		SNInitializer.setLoginUrl(loginUrl);
	}

	/**
	 * 跨域设置
	 * @param accessControlAllowOrigin
	 */
	public void setAccessControlAllowOrigin(String accessControlAllowOrigin) {
		SNInitializer.setAccessControlAllowOrigin(accessControlAllowOrigin);
	}

	/**
	 * 设置视图实现类
	 * @param clazz
	 */
	protected void setViewClass(Class<? extends IView> clazz) {
		SNInitializer.setView(clazz);
	}

	/**
	 * 初始化配置方法，一般用于读取配置信息和处理第三方框架的绑定
	 * @param context
	 * @throws Exception
	 */
	protected void initialize(ServletContext context) throws Exception {
		Optional<ServletContext> optional = Optional.ofNullable(context);
		// 设置系统编码
		optional.map(v -> WebUtil.getParameter(v, "UTF-8")).ifPresent(this::setEncoding);
		// 设置文件上传缓冲区大小
		optional.map(v -> WebUtil.getParameter(v, "file.size.threshold")) .ifPresent((v) -> setFileSizeThreshold(TypeUtil.castToIntValue(v)));
		// 设置文件上传所有文件大小限制
		optional.map(v -> WebUtil.getParameter(v, "max.request.size")) .ifPresent((v) -> setMaxRequestSize(TypeUtil.castToLongValue(v)));
		// 设置文件上传单个文件大小限制
		optional.map(v -> WebUtil.getParameter(v, "max.file.size")) .ifPresent((v) -> setMaxFileSize(TypeUtil.castToLongValue(v)));
		// 设置文件上传临时目录
		optional.map(v -> WebUtil.getParameter(v, "multipart.location")).ifPresent(this::setLocation);
		// 设置主键生成器的机器唯一码
		optional.map(v -> WebUtil.getParameter(v, "worker-id")) .ifPresent((v) -> PKGenerator.setWorkerId(TypeUtil.castToLongValue(v)));
		// 设置数据源
		if(StringUtil.isNotBlank(WebUtil.getParameter(context, "jdbc-names"))) {
			InitialContext initialContext = new InitialContext();
			for (String daoName : WebUtil.getParameter(context, "jdbc-names").split("[,][\\s]+")) {
				String jndi = WebUtil.getParameter(context, daoName);
				DataSource dataSource = (DataSource) initialContext.lookup(jndi);
				DaoManager.addDataSource(daoName, dataSource);
			}
		}
	}
}
