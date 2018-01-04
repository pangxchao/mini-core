/**
 * Created the test.sn.mini.contoller.MyController.java
 * @created 2017年10月18日 下午12:21:19
 * @version 1.0.0
 */
package test.sn.mini.contoller;

import java.sql.SQLException;

import sn.mini.dao.IDao;
import sn.mini.web.annotaion.Action;
import sn.mini.web.annotaion.Before;
import sn.mini.web.annotaion.Control;
import sn.mini.web.http.Controller;
import sn.mini.web.http.rander.Page;
import sn.mini.web.model.IModel;
import sn.mini.web.util.IUser;
import test.sn.mini.inters.MyInters;

/**
 * test.sn.mini.contoller.MyController.java
 * @author XChao
 */
@Before({ MyInters.class })
@Control(name = "users/back/my", url = "users/back/my", suffix = ".htm")
public class MyController extends Controller {
	private static final long serialVersionUID = 1L;

	@Action(value = Page.class, login = false, url = "index", suffix = ".htm")
	public void index(IDao dao, IModel model, IUser user) throws SQLException {
		System.out.println("-----------Action-------------------");
		System.out.println(model);
	}

	@Action(value = Page.class, login = false, url = "index1", suffix = ".htm")
	public void index1(IDao dao, IModel model, IUser user) {
		System.out.println("-----------Action1-------------------");
	}
}
