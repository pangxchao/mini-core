/**
 * Created the test.sn.mini.context.MyConfigration.java
 * @created 2017年10月13日 上午11:16:28
 * @version 1.0.0
 */
package test.sn.mini.context;

import javax.servlet.ServletContext;

import sn.mini.web.SNConfig;

/**
 * test.sn.mini.context.MyConfigration.java
 * @author XChao
 */
public class MyConfigration extends SNConfig {

	protected void initialize(ServletContext context) throws Exception {
		System.out.println("---------------MyConfigration----------------------");
		setAccessControlAllowOrigin("*");
	}
}
