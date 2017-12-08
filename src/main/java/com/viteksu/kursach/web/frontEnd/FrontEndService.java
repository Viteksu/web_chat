package com.viteksu.kursach.web.frontEnd;

import com.viteksu.kursach.core.messageSystem.Abonent;
import com.viteksu.kursach.web.backEnd.accounts.Message;
import com.viteksu.kursach.web.backEnd.accounts.UserProfile;

import java.util.List;

public interface FrontEndService extends Abonent, Runnable {
    void register(String name, String password, String id);

    boolean isRegistered(String login);

    void setRegistred(UserProfile userProfile, boolean result);

    void authenticate(String name, String password);

    UserProfile isAuthenticated(String login);

    void setAuthenticated(UserProfile userProfile);

    void setUserMessages(String login, List<Message> messages);

    List<Message> getMessages(String login);
}
