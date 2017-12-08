package com.viteksu.kursach.web.frontEnd;

import com.viteksu.kursach.core.messageSystem.MessageSystem;
import com.viteksu.kursach.core.messageSystem.addressService.Address;
import com.viteksu.kursach.core.messageSystem.addressService.AddressService;
import com.viteksu.kursach.core.messageSystem.messages.userDataService.userProfile.Authorization;
import com.viteksu.kursach.core.messageSystem.messages.userDataService.userProfile.Registration;
import com.viteksu.kursach.web.backEnd.accounts.Message;
import com.viteksu.kursach.web.backEnd.accounts.UserProfile;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FrontEndServiceImpl implements FrontEndService {
    private final AddressService addressService;
    private final MessageSystem messageSystem;


    private Set<String> registredUsers = ConcurrentHashMap.newKeySet();
    private Map<String, UserProfile> authenticatedUsers = new ConcurrentHashMap<>();
    private Map<String, LinkedHashSet<Message>> userMassages = new ConcurrentHashMap<>();


    private final Address address = new Address();

    private final Object regLock = new Object();
    private final Object authLock = new Object();

    @Override
    public Address getAddress() {
        return address;
    }

    public FrontEndServiceImpl(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        addressService = AddressService.getInstance();

        messageSystem.addService(this);
        addressService.registerFrontEndServ(this);
    }


    public void setRegistred(UserProfile userProfile, boolean result) {
        if (userProfile != null) {
            registredUsers.add(userProfile.getLogin());
        }
        synchronized (regLock) {
            regLock.notifyAll();
        }
    }

    public void setAuthenticated(UserProfile userProfile) {

        if (userProfile != null)
            authenticatedUsers.put(userProfile.getLogin(), userProfile);

        synchronized (authLock) {
            authLock.notifyAll();
        }
    }

    // блокировку добавить
    @Override
    public List<Message> getMessages(String login) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Message> messages = new LinkedList<>(userMassages.get(login));
        userMassages.get(login).clear();


        return messages;
    }

    @Override
    public void setUserMessages(String login, List<Message> messages) {

        LinkedHashSet<Message> mess = userMassages.get(login);

        if (mess == null) {
            this.userMassages.put(login, new LinkedHashSet<>(messages));
        } else {
            mess.addAll(messages);
        }

    }

    @Override
    public void authenticate(String name, String password) {
        try {
            messageSystem.sendMessage(new Authorization(this.getAddress(), addressService.getUserDataService().getAddress(), name, password));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserProfile isAuthenticated(String login) {
        synchronized (authLock) {
            try {
                authLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        UserProfile userProfile = authenticatedUsers.get(login);
        if (userProfile != null) {
            authenticatedUsers.remove(login);

            return userProfile;
        }

        return null;
    }

    @Override
    public void register(String login, String pass, String id) {
        messageSystem.sendMessage(new Registration(this.getAddress(), addressService.getUserDataService().getAddress(),
                login, pass, id));
    }

    @Override
    public boolean isRegistered(String login) {
        synchronized (regLock) {
            try {
                regLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return registredUsers.contains(login);
    }

    @Override
    public void run() {
        Thread.currentThread().setName("FrontEnd");

        while (true) {
            messageSystem.executeForAbonent(this);
        }
    }
}