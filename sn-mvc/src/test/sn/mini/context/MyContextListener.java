/**
 * Created the test.sn.mini.context.MyContextListener.java
 * @created 2017年11月17日 下午12:23:52
 * @version 1.0.0
 */
package test.sn.mini.context;

import javax.servlet.ServletContextEvent;

import sn.mini.web.listener.SNServletContextListener;

/**
 * test.sn.mini.context.MyContextListener.java
 * @author XChao
 */
public class MyContextListener implements SNServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("--------------MyContextListener.contextInitialized-------------------");

	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("--------------MyContextListener-------------------");
	}

}
