package com.viteksu.kursach.web.backEnd.dataBase;

import com.viteksu.kursach.web.backEnd.accounts.UserProfile;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Лень реалтзововать методы
abstract public class DBMock implements DBservice {
    private static int id = 0;

    private Map<Integer, UserProfile> map = new ConcurrentHashMap<>();
    private Map<String, UserProfile> map2 = new ConcurrentHashMap<>();

    @Override
    public long addUser(UserProfile userProfile) {
        map.put(id, userProfile);
        map2.put(userProfile.getLogin(), userProfile);
        return id++;
    }

    @Override
    public UserProfile getUserById(long id) {
        return map.get(id);
    }

    @Override
    public void update(UserProfile userProfile) {

    }

    @Override
    public UserProfile getUserByLogin(String login) {
        return map2.get(login);
    }
}
