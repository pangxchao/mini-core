/**
 * Created the test.sn.mini.inters.MyInters.java
 * @created 2017年10月18日 下午12:24:03
 * @version 1.0.0
 */
package test.sn.mini.inters;

import sn.mini.web.http.ActionInvoke;
import sn.mini.web.http.interceptor.Interceptor;

/**
 * test.sn.mini.inters.MyInters.java
 * @author XChao
 */
public class MyInters implements Interceptor {

	@Override
	public String interceptor(ActionInvoke invoke) throws Exception {
		System.out.println("----------before-----------");
		String result = invoke.invoke();
		System.out.println("----------after-----------");
		return result;
	}

}
