package com.viteksu.kursach.web.frontEnd;

import com.viteksu.kursach.core.messageSystem.MessageSystem;
import com.viteksu.kursach.core.messageSystem.addressService.Address;
import com.viteksu.kursach.core.messageSystem.addressService.AddressService;
import com.viteksu.kursach.core.messageSystem.messages.accountService.AuthorizationMsg;
import com.viteksu.kursach.core.messageSystem.messages.accountService.RegistrationMsg;
import com.viteksu.kursach.web.backEnd.accounts.UserProfile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class FrontEndServiceImpl implements FrontEndService {
    private final AddressService addressService;
    private final MessageSystem messageSystem;

    private Set<String> registredUsers = ConcurrentHashMap.newKeySet();
    private Map<String, UserProfile> authenticatedUsers = new ConcurrentHashMap<>();


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
        if (userProfile != null)
            registredUsers.add(userProfile.getLogin());

        synchronized (regLock) {
            regLock.notifyAll();
        }
    }

    public void setAuthenticated(UserProfile userProfile) {

        if (userProfile != null) {
            authenticatedUsers.put(userProfile.getLogin(), userProfile);
        }

        synchronized (authLock) {
            authLock.notifyAll();
        }
    }

    @Override
    public void authenticate(String name, String password) {
        try {
            messageSystem.sendMessage(new AuthorizationMsg(this.getAddress(), addressService.getAccountService().getAddress(), name, password));
        } catch (Exception e) {
            System.err.println("Auth");
        }
    }

    @Override
    public UserProfile isAuthenticated(String login) {
        synchronized (authLock) {
            try {
                System.err.println("isAuthenticated BEFORE wait ");
                authLock.wait();
                System.err.println("isAuthenticated AFTER wait ");
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
        messageSystem.sendMessage(new RegistrationMsg(this.getAddress(), addressService.getAccountService().getAddress(),
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