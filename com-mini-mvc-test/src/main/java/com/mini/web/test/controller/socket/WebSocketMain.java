package com.mini.web.test.controller.socket;

import com.mini.thread.RunnableLinkedBlockingQueue;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/socket/{username}")
public class WebSocketMain {
    private static Map<String, WebSocketMain> clients = new ConcurrentHashMap<>();
    private static int onlineCount = 0;
    private Session session;
    private String username;

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) throws IOException {
        this.username = username;
        this.session  = session;
        clients.put(username, this);
        System.out.println("已连接");
    }

    @OnClose
    public void onClose() throws IOException {
        clients.remove(username);
        System.out.println("已关闭");
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        System.out.println("---这是客户端发过来的消息？");
        System.out.println(message);
        System.out.println();
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("出错啦");
        error.printStackTrace();
    }

    public static void sendMessage(String message, String... users) {
        RunnableLinkedBlockingQueue.put(() -> {
            // 传入的用户名不存在，发送所有用户
            if (users == null || users.length == 0) {
                for (WebSocketMain item : clients.values()) {
                    item.session.getAsyncRemote().sendText(message);
                }
                return;
            }
            for (String user : users) {
                WebSocketMain item = clients.get(user);
                if (item != null) {
                    item.session.getAsyncRemote().sendText(message);
                }
            }
        });
    }
}
