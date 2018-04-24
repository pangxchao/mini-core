/**
 * Created the sn.mini.java.web.listener.DaoServletContextListener.java
 * @created 2017年11月17日 下午3:32:13
 * @version 1.0.0
 */
package sn.mini.java.web.listener;

import java.util.Map.Entry;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import sn.mini.java.jdbc.DaoManager;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.util.logger.Log;

/**
 * sn.mini.java.web.listener.DaoServletContextListener.java
 * @author XChao
 */
public abstract class DaoServletContextListener implements ServletContextListener {

	protected IDao getDao(String name) {
		return DaoManager.getDao(name);
	}

	@Override
	public final void contextInitialized(ServletContextEvent event) {
		try {
			this.dbContextInitialized(event);
		} finally {
			for (Entry<String, IDao> entry : DaoManager.getCurrentDao().entrySet()) {
				try (IDao connection = entry.getValue()) {
				} catch (Exception e) {
					Log.error("Close Dao fail. ", e);
				}
			}
		}
	}

	public abstract void dbContextInitialized(ServletContextEvent event);

	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}

}
