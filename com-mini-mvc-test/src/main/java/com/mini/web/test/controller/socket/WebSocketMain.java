package com.mini.web.test.controller.socket;

import com.mini.core.logger.Logger;
import com.mini.core.util.Assert;

import javax.websocket.*;
import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.Serializable;
import java.util.EventListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.mini.core.logger.LoggerFactory.getLogger;
import static java.util.Optional.ofNullable;

@ServerEndpoint("/socket/{username}")
public final class WebSocketMain implements Serializable, EventListener {
    private static final Map<String, Session> clients = new ConcurrentHashMap<>();
    private static final Logger logger = getLogger(WebSocketMain.class);
    private static final long serialVersionUID = 5617290669921632800L;

    @OnOpen
    public final void onOpen(Session session, @PathParam("username") String username) {
        System.out.println("---已连接, userName: " + username);
        Assert.isTrue(!clients.containsKey(username));
        clients.put(username, session);
    }

    @OnClose
    public final void onClose(Session session, @PathParam("username") String username) {
        System.out.println("---已关闭, userName: " + username);
        clients.remove(username);
    }

    @OnMessage
    public final void onMessage(Session session, @PathParam("username") String username, String message) {
        System.out.println("---这是客户端发过来的消息, userName: " + username);
        System.out.println(message);
    }

    @OnError
    public final void onError(Session session, @PathParam("username") String username, Throwable error) {
        System.out.println("---出错啦, userName: " + username);
        logger.error(error);
    }

    public static void sendMessage(String message) {
        clients.values().forEach(session -> { //
            Async a = session.getAsyncRemote();
            a.sendText(message);
        });
    }

    public static void sendMessage(String message, String... users) {
        for (int i = 0; users != null && i < users.length; i++) {
            ofNullable(clients.get(users[i])).ifPresent(s -> {
                s.getAsyncRemote().sendText(message); //
            });
        }
    }
}
