/**
 * Created the sn.mini.web.listener.DaoServletContextListener.java
 * @created 2017年11月17日 下午3:32:13
 * @version 1.0.0
 */
package sn.mini.web.listener;

import java.util.Map.Entry;

import javax.servlet.ServletContextEvent;

import sn.mini.dao.DaoManager;
import sn.mini.dao.IDao;
import sn.mini.util.logger.Log;

/**
 * sn.mini.web.listener.DaoServletContextListener.java
 * @author XChao
 */
public abstract class DaoServletContextListener implements SNServletContextListener {

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
