package com.viteksu.kursach.web.backEnd.accounts;

import com.viteksu.kursach.core.messageSystem.Abonent;
import com.viteksu.kursach.core.messageSystem.MessageSystem;

public interface AccountService extends Abonent, Runnable {
    boolean addNewUser(UserProfile userProfile);

    UserProfile getUserById(long id);

    UserProfile getUserByLogin(String login);

    MessageSystem getMessageSystem();
}

