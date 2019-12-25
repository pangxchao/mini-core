package com.mini.core.web.test.controller;

import com.mini.core.web.annotation.Action;
import com.mini.core.web.annotation.Controller;
import com.mini.core.web.model.PageModel;
import com.mini.core.web.annotation.Action;
import com.mini.core.web.annotation.Controller;
import com.mini.core.web.model.PageModel;

import javax.inject.Singleton;

@Singleton
@Controller(path = "back/main", url = "back/main")
public class MainController {

	@Action(url = "index.htm")
	public void index(PageModel model) {
		model.addData("logo", "//tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg");
		model.addData("userName", "超超");
	}

	@Action(url = "center.htm")
	public void center(PageModel model) {

	}
}
