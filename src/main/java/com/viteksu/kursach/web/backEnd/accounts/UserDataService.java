package com.viteksu.kursach.web.backEnd.accounts;

import com.viteksu.kursach.core.messageSystem.Abonent;
import com.viteksu.kursach.core.messageSystem.MessageSystem;

import java.util.List;

public interface UserDataService extends Abonent, Runnable {
    boolean addNewUser(UserProfile userProfile);

    UserProfile getUserById(long id);

    UserProfile getUserByLogin(String login);

    MessageSystem getMessageSystem();

    void addMessages(List<Message> messages);

    List<Message> getMessageById(long from, long to);


    List<Message> getMessagesBySender(String sender);

    List<Message> getMessagesByRecipient(String recipient);
}

