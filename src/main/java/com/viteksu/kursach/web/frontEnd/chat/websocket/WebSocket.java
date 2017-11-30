package com.viteksu.kursach.web.frontEnd.chat.websocket;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/chatMess",
        configurator = MyWebSocketConfigurator.class)
public class WebSocket {
    private final WebSocketHandler webSocketHandler;
    private Session session;
    private boolean isName = false;

    public WebSocket(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketHandler.addUser(this);
    }

    @OnClose
    public void onClose() {
        webSocketHandler.removeUser(this);
    }

    @OnMessage
    public void onMessage(String message) {
        if (!isName) {
            webSocketHandler.setName(this, message);
            isName = true;
            return;
        }
        webSocketHandler.send(message);
    }

    public void sendBack(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }
}
