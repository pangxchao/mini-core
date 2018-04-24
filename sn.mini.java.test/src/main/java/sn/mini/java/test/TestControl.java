package sn.mini.java.test;

import sn.mini.java.web.annotaion.Action;
import sn.mini.java.web.annotaion.Control;
import sn.mini.java.web.http.Controller;

@Control(name = "test_control", url = "test_control")
public class TestControl extends Controller {

	@Action
	public void index() {
		System.out.println("-------------------");
	}

}
