package com.viteksu.kursach.core.messageSystem.addressService;

import com.viteksu.kursach.core.messageSystem.MessageSystem;
import com.viteksu.kursach.web.backEnd.accounts.UserDataService;
import com.viteksu.kursach.web.frontEnd.FrontEndService;

public class AddressService {
    private static AddressService addressService = new AddressService();

    private UserDataService userDataService;
    private FrontEndService frontEnd;
    private MessageSystem messageSystem;

    private AddressService() {
    }

    public void registerMessageSystem(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public void registerAccountServ(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    public void registerFrontEndServ(FrontEndService frontEnd) {
        this.frontEnd = frontEnd;
    }

    public UserDataService getUserDataService() {
        return userDataService;
    }

    public FrontEndService getFrontEnd() {
        return frontEnd;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public static AddressService getInstance() {
        return addressService;
    }
}
