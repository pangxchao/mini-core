package com.mini.web.test.controller;

import com.mini.core.util.PKGenerator;
import com.mini.core.web.annotation.Action;
import com.mini.core.web.annotation.Controller;
import com.mini.core.web.model.JsonModel;
import com.mini.core.web.model.PageModel;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

@Singleton
@Controller(path = "web_socket", url = "web_socket")
public class WebSocketController {

	@Action(value = PageModel.class)
	public void index(PageModel model, HttpServletRequest request) {
		model.addData("username", PKGenerator.uuid());
	}

	@Action(value = JsonModel.class)
	public void sendMessage(JsonModel model, String userName, String message) {
		String[] users = StringUtils.split(userName, ",");
		WebSocketMain.sendMessage(message, users);
	}
}
