package com.viteksu.kursach.web.frontEnd.chat.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.viteksu.kursach.web.backEnd.accounts.Message;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketHandler {
    private static WebSocketHandler webSocketHandler = new WebSocketHandler();
    private Map<WebSocket, String> users = new ConcurrentHashMap<>();
    private List<Message> messages = new LinkedList<>();

    private WebSocketHandler() {
    }


    public void removeUser(WebSocket webSocket) {
        String type = "UPDATE_DEL";
        String sender = "SERVER";
        String recipient = "ALL";

        String login = users.get(webSocket);
        users.remove(webSocket);

        Message message = new Message(type, sender, recipient, login);
        Gson gson = new Gson();

        String answerJson = gson.toJson(message);

        sendToClient(answerJson, recipient, sender);


    }

    void addUser(WebSocket webSocket, String name) {
        users.put(webSocket, name);

        String type = "UPDATE_ADD";
        String sender = "SERVER";
        String recipient = "ALL";

        for (Map.Entry<WebSocket, String> entry : users.entrySet()) {
            String login = entry.getValue();


            Message message = new Message(type, sender, recipient, login);
            Gson gson = new Gson();


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

    private void addMessage(Message message) {
        if (message.getType().equals("MESSAGE")) {
            messages.add(message);

            if (messages.size() > 1) {
                List<Message> newListMess = new LinkedList<>();
                List<Message> lastMess = new LinkedList<>(messages);
                messages = newListMess;


                for (Message m : lastMess) {
                    System.err.println(m.getMessage() + " -----");
                }
                // работать с lastMess!!


                // отправить в БД
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

        addMessage(mess);

        Gson gson = new Gson();

        sendToClient(gson.toJson(mess), recipient, sender);
    }

    public static WebSocketHandler getInsance() {
        return webSocketHandler;
    }
}
