package sn.mini.java.web.listener;

import sn.mini.java.jdbc.DaoManager;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.util.lang.MethodUtil;
import sn.mini.java.util.lang.reflect.SNParameter;
import sn.mini.java.util.logger.Log;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map.Entry;

public abstract class DaoServletContextListener implements ServletContextListener {

    protected IDao getDao(String name) {
        return DaoManager.getDao(name);
    }

    @Override
    public final void contextInitialized(ServletContextEvent event) {
        try {
            SNParameter[] parameter = MethodUtil.getSNParameter(this.getClass().getMethod("contextInitialized", IDao.class, ServletContextEvent.class));
            if (parameter.length > 0 && parameter[0] != null) {
                this.contextInitialized(DaoManager.getDao(parameter[0].getName()), event);
            }
        } catch (Exception e) {
            Log.error("Init error. ", e);
        } finally {
            for (Entry<String, IDao> entry : DaoManager.getCurrentDao().entrySet()) {
                try (IDao connection = entry.getValue()) {
                    Log.debug("close jdbc before:" + connection);
                } catch (Exception e) {
                    Log.error("Close Dao fail. ", e);
                }

            }
        }
    }

    public abstract void contextInitialized(IDao dao, ServletContextEvent event);

    @Override
    public void contextDestroyed(ServletContextEvent event) {

    }

}
