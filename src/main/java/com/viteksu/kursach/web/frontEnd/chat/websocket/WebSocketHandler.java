package com.viteksu.kursach.web.frontEnd.chat.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.viteksu.kursach.core.messageSystem.addressService.AddressService;
import com.viteksu.kursach.core.messageSystem.messages.userDataService.userMessage.AddingMessageMsg;
import com.viteksu.kursach.core.messageSystem.messages.userDataService.userMessage.GettingMessageMsg;
import com.viteksu.kursach.web.backEnd.accounts.Message;

import java.io.IOException;
import java.util.LinkedHashSet;
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

        sendToClient(message);


    }

    void addUser(WebSocket webSocket, String name) {
        users.put(webSocket, name);

        String type = "UPDATE_ADD";
        String sender = "SERVER";
        String recipient = "ALL";

        for (Map.Entry<WebSocket, String> entry : users.entrySet()) {
            String login = entry.getValue();

            Message message = new Message(type, sender, recipient, login);

            sendToClient(message);
        }

        sendMessageHistory(webSocket, name);
    }


    private void sendMessageHistory(WebSocket webSocket, String name) {
        LinkedHashSet<Message> messages = new LinkedHashSet<>();
        Gson gson = new Gson();

        AddressService addressService = AddressService.getInstance();
        addressService.getMessageSystem().sendMessage(new GettingMessageMsg(addressService.getFrontEnd().getAddress()
                , addressService.getUserDataService().getAddress(), name, GettingMessageMsg.SENDER));
        messages.addAll(addressService.getFrontEnd().getMessages(name));

        addressService.getMessageSystem().sendMessage(new GettingMessageMsg(addressService.getFrontEnd().getAddress()
                , addressService.getUserDataService().getAddress(), name, GettingMessageMsg.RECIPIENT));
        messages.addAll(addressService.getFrontEnd().getMessages(name));

        for (Message m : messages) {
            if (m != null) {
                try {
                    webSocket.sendBack(gson.toJson(m));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void sendToClient(Message message) {
        Gson gson = new Gson();
        String answerJson = gson.toJson(message);

        if (message.getRecipient().equals("ALL") || message.getType().equals("ERR")) {
            for (Map.Entry<WebSocket, String> entry : users.entrySet()) {
                try {
                    entry.getKey().sendBack(answerJson);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (Map.Entry<WebSocket, String> entry : users.entrySet()) {
                if (entry.getValue().equals(message.getRecipient()) || entry.getValue().equals(message.getSender())) {
                    try {
                        entry.getKey().sendBack(answerJson);
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

                AddressService addressService = AddressService.getInstance();

                addressService.getMessageSystem().sendMessage(new AddingMessageMsg(addressService.getFrontEnd().getAddress()
                        , addressService.getUserDataService().getAddress(), lastMess));
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
        sendToClient(mess);
    }

    public static WebSocketHandler getInsance() {
        return webSocketHandler;
    }
}
