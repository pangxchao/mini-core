package com.mini.web.test.context;

import static java.util.Collections.singletonList;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import com.google.inject.Binder;
import com.google.inject.Provides;
import com.mini.inject.annotation.ComponentScan;
import com.mini.inject.annotation.EnableTransaction;
import com.mini.inject.annotation.PropertySource;
import com.mini.jdbc.JdbcTemplate;
import com.mini.jdbc.JdbcTemplateMysql;
import com.mini.jdbc.transaction.TransactionalManager;
import com.mini.jdbc.transaction.TransactionalManagerJdbc;
import com.mini.web.config.Configure;
import com.mini.web.config.ServletElement;
import com.mini.web.config.WebMvcConfigure;
import com.mini.web.servlet.DispatcherHttpServlet;
import com.mini.web.test.R;

/**
 * 自定义配置信息，至少需要一个
 * <ul>
 * <li>@Singleton: 该在依赖注入容器中为单例</li>
 * <li>@EnableTransaction: 开启数据库声明式事务</li>
 * <li>@ComponentScan: Controller 类扫描的包名</li>
 * <li>@PropertySource: 自定义配置文件路径（必须在classpath路径下）</li>
 * </ul>
 * @author xchao
 */
@Singleton
@EnableTransaction
@ComponentScan("com.mini.web.test")
@PropertySource("application.properties")
public class MiniWebMvcConfigurer extends WebMvcConfigure {

	@Override
	protected synchronized void onStartupBinding(Binder binder) {
		binder.requestStaticInjection(R.class);
		// 这里可以绑定依赖注入相关的东西
		// 绑定时无法使用依赖注入相关实例
	}

	@Override
	protected void onStartupRegister(ServletContext servletContext, Configure configure) {
		// 默认的Servlet相关设置可以在这里进行
		configure.addServlet(DispatcherHttpServlet.class);

		// 自定义Servlet相关设置可以在这里进行
		ServletElement el = configure.addServlet(MiniWebMvcServlet.class);
		el.addUrlPatterns("/front/user/group.htm");
		el.setMultipartEnabled(false);
	}

	/**
	 * 依赖注入数据源配置
	 * @param jndiName jndi名称
	 * @return 数据源
	 */
	@Provides
	@Singleton
	public DataSource getDataSource(@Named("mini.datasource.jndi-name") String jndiName) throws NamingException {
		InitialContext context = new InitialContext();
		return (DataSource) context.lookup(jndiName);
	}

	/**
	 * 依赖注入JdbcTemplate对象配置
	 * @param dataSource 数据源
	 * @return JdbcTemplate对象
	 */
	@Provides
	@Singleton
	public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
		return new JdbcTemplateMysql(dataSource);
	}

	/**
	 * 依赖注入声明式事务管理器配置
	 * @param jdbcTemplate JdbcTemplate对象
	 * @return 声明式事务管理器配置
	 */
	@Provides
	@Singleton
	public TransactionalManager getTransactionalManager(JdbcTemplate jdbcTemplate) {
		return new TransactionalManagerJdbc(singletonList(jdbcTemplate));
	}

	///**
	// * 依赖注入JTA事务UserTransaction获取器配置
	// * @return JTA事务UserTransaction获取器
	// */
	//@Provides
	//@Singleton
	//public Provider<UserTransaction> getUserTransactionProvider() {
	//    return com.atomikos.icatch.jta.UserTransactionImp::new;
	//}
}
