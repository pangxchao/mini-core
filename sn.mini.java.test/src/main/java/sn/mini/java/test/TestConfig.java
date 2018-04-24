package sn.mini.java.test;

import javax.servlet.ServletContext;

import sn.mini.java.web.SNConfig;
import sn.mini.java.web.http.view.JspView;

public class TestConfig extends SNConfig {

	@Override
	protected void initialize(ServletContext context) throws Exception {
		super.initialize(context);
		setViewClass(JspView.class);
	}
}
