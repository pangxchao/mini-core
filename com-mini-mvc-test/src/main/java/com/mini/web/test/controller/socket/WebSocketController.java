package com.mini.web.test.controller.socket;

import com.mini.util.PKGenerator;
import com.mini.util.StringUtil;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.model.MapModel;
import com.mini.web.model.PageModel;


import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

@Singleton
@Controller(path = "web_socket", url = "web_socket")
public class WebSocketController {

    @Action(url = "index.htm")
    public void index(PageModel model, HttpServletRequest request) {
        model.addData("username", PKGenerator.uuid());
    }

    @Action(value = MapModel.class, url = "sendMessage.htm")
    public void sendMessage(MapModel model, String userName, String message) {
        String[] users = StringUtil.split(userName, ",");
        WebSocketMain.sendMessage(message, users);
    }
}
