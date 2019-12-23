package com.mini.web.test.context;

import com.google.inject.Binder;
import com.google.inject.Provides;
import com.mini.core.inject.annotation.ComponentScan;
import com.mini.core.inject.annotation.PropertySource;
import com.mini.core.jdbc.JdbcTemplate;
import com.mini.core.jdbc.MysqlJdbcTemplate;
import com.mini.core.jdbc.transaction.JdbcTransManager;
import com.mini.core.jdbc.transaction.TransManager;
import com.mini.core.jdbc.transaction.TransactionEnable;
import com.mini.web.support.WebApplicationInitializer;
import com.mini.web.support.config.Configures;
import com.mini.web.test.R;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import static java.util.Collections.singletonList;

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
@TransactionEnable
@ComponentScan("com.mini.web.test")
@PropertySource("application.properties")
public class MiniWebMvcConfigurer extends WebApplicationInitializer {

	@Override
	protected synchronized void onStartupBinding(Binder binder) {
		binder.requestStaticInjection(R.class);
		// 这里可以绑定依赖注入相关的东西
		// 绑定时无法使用依赖注入相关实例
	}

	@Override
	public void onStartupRegister(ServletContext servletContext, Configures configure) {
		// 自定义Servlet相关设置可以在这里进行
		configure.addServlet(MiniWebMvcServlet.class, registration -> {
			registration.addUrlPatterns("/front/user/group.htm");
			registration.setMultipartEnabled(false);
		});
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
		return new MysqlJdbcTemplate(dataSource);
	}

	/**
	 * 依赖注入声明式事务管理器配置
	 * @param jdbcTemplate JdbcTemplate对象
	 * @return 声明式事务管理器配置
	 */
	@Provides
	@Singleton
	public TransManager getTransactionalManager(JdbcTemplate jdbcTemplate) {
		return new JdbcTransManager(singletonList(jdbcTemplate));
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
