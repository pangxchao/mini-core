/**
 * Created the sn.mini.dao.DaoManager.java
 * @created 2016年9月24日 下午12:03:31
 * @version 1.0.0
 */
package sn.mini.java.jdbc;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import sn.mini.java.jdbc.implement.DB2Dao;
import sn.mini.java.jdbc.implement.HsqlDao;
import sn.mini.java.jdbc.implement.InformixDao;
import sn.mini.java.jdbc.implement.MysqlDao;
import sn.mini.java.jdbc.implement.OdbcDao;
import sn.mini.java.jdbc.implement.OracleDao;
import sn.mini.java.jdbc.implement.SQLServerDao;
import sn.mini.java.jdbc.implement.SybaseDao;
import sn.mini.java.jdbc.util.DaoThreadLocal;

/**
 * sn.mini.dao.DaoManager.java
 * @author XChao
 */
public final class DaoManager {
	/***************************************** 实体部分 **********************************************************/
	private final String name;
	private final String driver;
	private final DaoFactory factory;
	private final DataSource dataSource;

	public DaoManager(String name, String dirver, DaoFactory factory, DataSource dataSource) {
		this.name = name;
		this.driver = dirver;
		this.factory = factory;
		this.dataSource = dataSource;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the driver
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * @return the factory
	 */
	public DaoFactory getFactory() {
		return factory;
	}

	/**
	 * @return the dataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	public IDao create() throws SQLException {
		return factory.create(this.dataSource);
	}

	/**************************************** 数据源绑定部分 ***********************************************************/
	private static final Map<String, DaoManager> dao_managers = new ConcurrentHashMap<>(); // 保存存所有数据源信息
	private static final DaoThreadLocal dao_connection = new DaoThreadLocal(); //
	private static final Map<String, DaoFactory> dao_factory = new ConcurrentHashMap<String, DaoFactory>() {
		private static final long serialVersionUID = 1L;
		{
			// Myslq: jdbc:mysql://hostip:port/dbname
			put("com.mysql.jdbc.Driver", ds -> new MysqlDao(ds));
			// Oracle: jdbc:oracle:thin:@hostip:port:dbname
			put("oracle.jdbc.driver.OracleDriver", ds -> new OracleDao(ds));
			// jdbc-odbc: jdbc:odbc:datasource_name
			put("sun.jdbc.odbc.JdbcOdbcDriver", ds -> new OdbcDao(ds));
			// DB2: jdbc:db2://hostip:port/dbname
			put("com.ibm.db2.jcc.DB2Driver", ds -> new DB2Dao(ds));
			// SQLServer/Ms-Sql 2000: jdbc:microsoft:sqlserver://<hostip><:port>DatabaseName=<dbname>
			put("com.microsoft.jdbc.sqlserver.SQLServerDriver", ds -> new SQLServerDao(ds));
			// SQLServer/Ms-Sql 2005： jdbc:microsoft:sqlserver://<hostip><:port>DatabaseName=<dbname>
			put("com.microsoft.sqlserver.jdbc.SQLServerDriver", ds -> new SQLServerDao(ds));
			// SQLServer/Ms-Sql 7.0: jdbc:jtds:sqlserver://hostip:1433/dbname
			put("net.sourceforge.jtds.jdbc.Driver", ds -> new SQLServerDao(ds));
			// Sybase: jdbc:sybase:Tds:hostip:port/dbname
			put("com.sybase.jdbc2.jdbc.SybDriver", ds -> new SybaseDao(ds));
			// Informix: jdbc:informix-sqli://hostip:port/dbname:informixserver=<dbservername>
			put("com.informix.jdbc.IfxDriver", ds -> new InformixDao(ds));
			// hsqldb: jdbc:hsqldb:hsql://localhost/dbname
			put("org.hsqldb.jdbcDriver", ds -> new HsqlDao(ds));
		}
	};

	/**
	 * 根据数据源名称 创建一个数据库
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static IDao getDao(String name) {
		try {
			IDao dao = DaoManager.dao_connection.get().get(name);
			if (dao == null || dao.isClosed()) {
				dao = dao_managers.get(name).create();
				dao_connection.get().put(name, dao);
			}
			return dao;
		} catch (Exception e) {
			throw new RuntimeException("Create dao fail, dataSource name is " + name, e);
		}
	}

	/**
	 * 添加一个数据源
	 * @param name
	 * @param dataSource
	 * @throws SQLException
	 */
	public static void addDataSource(String name, DataSource dataSource) throws SQLException {
		DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
		Class<?> driver = DriverManager.getDriver(metaData.getURL()).getClass();
		DaoManager.dao_managers.put(name, new DaoManager(name, driver.getName(), //
				dao_factory.get(driver.getName()), dataSource));
	}

	/**
	 * 获取当前线程所有Dao连接
	 * @return
	 */
	public static Map<String, IDao> getCurrentDao() {
		return dao_connection.get();
	}

	/**
	 * 绑定数据库驱动与数据库工厂实现
	 * @param driverClass
	 * @param factory
	 */
	public static void bindFactory(String driverClass, DaoFactory factory) {
		DaoManager.dao_factory.put(driverClass, factory);
	}
}
