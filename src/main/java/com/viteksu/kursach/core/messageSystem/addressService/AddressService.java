package com.viteksu.kursach.core.messageSystem.addressService;

import com.viteksu.kursach.core.messageSystem.Abonent;
import com.viteksu.kursach.core.messageSystem.MessageSystem;
import com.viteksu.kursach.web.backEnd.accounts.AccountService;
import com.viteksu.kursach.web.frontEnd.FrontEndService;

public class AddressService {
    private static AddressService addressService = new AddressService();

    private AccountService accountService;
    private FrontEndService frontEnd;
    private MessageSystem messageSystem;

    private AddressService() {
    }

    public void registerMessageSystem(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public void registerAccountServ(AccountService accountService) {
        this.accountService = accountService;
    }

    public void registerFrontEndServ(FrontEndService frontEnd) {
        this.frontEnd = frontEnd;
    }

    public AccountService getAccountService() {
        return accountService;
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
