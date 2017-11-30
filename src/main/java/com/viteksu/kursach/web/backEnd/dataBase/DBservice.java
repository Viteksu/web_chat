package com.viteksu.kursach.web.backEnd.dataBase;

import com.viteksu.kursach.web.backEnd.accounts.UserProfile;

public interface DBservice {
    long addUser(UserProfile userProfile);

    UserProfile getUserById(long id);

    void update(UserProfile userProfile);

    UserProfile getUserByLogin(String login);
}
