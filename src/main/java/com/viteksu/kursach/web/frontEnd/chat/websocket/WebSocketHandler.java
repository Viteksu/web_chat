package com.viteksu.kursach.web.frontEnd.chat.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.viteksu.kursach.web.backEnd.accounts.Message;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketHandler {
    private static WebSocketHandler webSocketHandler = new WebSocketHandler();
    private Map<WebSocket, String> users = new ConcurrentHashMap<>();

    private WebSocketHandler() {
    }

    public void addUser(WebSocket webSocket) {
        users.put(webSocket, "");
    }

    public void removeUser(WebSocket webSocket) {
        users.remove(webSocket);
    }

    public void setName(WebSocket webSocket, String name) {
        users.put(webSocket, name);

        System.err.println("name: " + name + "-");

        String type = "UPDATE_ADD";
        String sender = "SERVER";
        String recipient = "ALL";


        for (Map.Entry<WebSocket, String> entry : users.entrySet()) {
            String login = entry.getValue();


            Message message = new Message(type, sender, recipient, login);
            Gson gson = new Gson();

            System.err.println("From: " + sender);
            System.err.println("Mess:" + login);

            String answerJson = gson.toJson(message);

            sendToClient(answerJson, recipient, sender);
        }


    }

    private void sendToClient(String jsonMess, String recipient, String sender) {
        if (recipient.equals("ALL") || recipient.equals("ERR")) {
            for (Map.Entry<WebSocket, String> entry : users.entrySet()) {
                try {
                    entry.getKey().sendBack(jsonMess);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (Map.Entry<WebSocket, String> entry : users.entrySet()) {
                if (entry.getValue().equals(recipient) || entry.getValue().equals(sender)) {

                    try {
                        entry.getKey().sendBack(jsonMess);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public void send(String jsonMessage) {

        String type;
        String sender;
        String recipient;
        String message;
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(jsonMessage).getAsJsonObject();

            recipient = jsonObject.get("recipient").getAsString();
            sender = jsonObject.get("sender").getAsString();
            type = jsonObject.get("type").getAsString();
            message = jsonObject.get("message").getAsString();
        } catch (Exception e) {
            // need logger!!
            type = "ERR";
            recipient = "ALL";
            sender = "UNKNOWN";
            message = e.toString();
        }

        Message mess = new Message(type, sender, recipient, message);
        Gson gson = new Gson();

        System.err.println("From: " + sender);
        System.err.println("Mess:" + message);

        sendToClient(gson.toJson(mess), recipient, sender);
    }

    public static WebSocketHandler getInsance() {
        return webSocketHandler;
    }
}
