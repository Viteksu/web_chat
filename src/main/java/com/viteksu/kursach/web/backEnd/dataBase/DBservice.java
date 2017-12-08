package com.viteksu.kursach.web.backEnd.dataBase;

import com.viteksu.kursach.web.backEnd.accounts.Message;
import com.viteksu.kursach.web.backEnd.accounts.UserProfile;

import java.util.List;

public interface DBservice {
    long addUser(UserProfile userProfile);

    UserProfile getUserById(long id);

    void update(UserProfile userProfile);

    UserProfile getUserByLogin(String login);

    void addMessages(List<Message> messages);

    List<Message> getMessageById(long from, long to);

    List<Message> getMessagesBySender(String sender);

    List<Message> getMessagesByRecipient(String recipient);
}
