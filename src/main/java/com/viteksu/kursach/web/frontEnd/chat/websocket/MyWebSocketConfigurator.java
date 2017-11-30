package com.viteksu.kursach.web.frontEnd.chat.websocket;

import javax.websocket.server.ServerEndpointConfig;

public class MyWebSocketConfigurator extends ServerEndpointConfig.Configurator {
    private WebSocketHandler webSocketHandler = WebSocketHandler.getInsance();

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return (T) new WebSocket(webSocketHandler);


    }
}
