package com.viteksu.kursach.web.backEnd.accounts;


import com.viteksu.kursach.core.messageSystem.MessageSystem;
import com.viteksu.kursach.core.messageSystem.addressService.Address;
import com.viteksu.kursach.core.messageSystem.addressService.AddressService;
import com.viteksu.kursach.web.backEnd.dataBase.DBMock;
import com.viteksu.kursach.web.backEnd.dataBase.DBservice;
import com.viteksu.kursach.web.backEnd.dataBase.DBserviceImpl;

public class AccountServiceImpl implements AccountService {
    //    private final DBservice dbService = new DBserviceImpl();
    private final DBservice dbService = new DBMock();

    private final Address address = new Address();

    private final MessageSystem messageSystem;
    private final AddressService addressService;

    public AccountServiceImpl(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        addressService = AddressService.getInstance();

        messageSystem.addService(this);
        addressService.registerAccountServ(this);

    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public UserProfile getUserById(long id) {
        return null;
    }

    @Override
    public boolean addNewUser(UserProfile userProfile) {

        UserProfile user = dbService.getUserByLogin(userProfile.getLogin());

        if (user == null) {
            dbService.addUser(userProfile);
            return true;
        } else
            return false;

    }

    @Override
    public UserProfile getUserByLogin(String login) {
        return dbService.getUserByLogin(login);
    }

    @Override
    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    @Override
    public void run() {

        Thread.currentThread().setName("Account Service");

        try {
            while (true) {
                messageSystem.executeForAbonent(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
