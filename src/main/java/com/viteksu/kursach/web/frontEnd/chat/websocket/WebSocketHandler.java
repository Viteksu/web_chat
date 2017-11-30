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
    }

    public void send(String jsonMessage) {
        PrintStream printStream = null;

        try {
            printStream = new PrintStream("C:\\proga\\text.txt");

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

            String answerMess = gson.toJson(mess);
            if (recipient.equals("ALL")) {
                for (Map.Entry<WebSocket, String> entry : users.entrySet()) {
                    try {
                        entry.getKey().sendBack(answerMess);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                for (Map.Entry<WebSocket, String> entry : users.entrySet()) {
                    if (entry.getValue().equals(recipient) || entry.getValue().equals(sender)) {
                        printStream.println(entry.getValue() + " " + recipient);
                        try {
                            entry.getKey().sendBack(answerMess);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace(printStream);
        } finally {
            printStream.close();
        }
    }


    public static WebSocketHandler getInsance() {
        return webSocketHandler;
    }
}
