package com.viteksu.kursach.core.messageSystem.messages.accountService;


import com.viteksu.kursach.core.messageSystem.addressService.Address;
import com.viteksu.kursach.core.messageSystem.messages.frontEnd.RegistredMsg;
import com.viteksu.kursach.web.backEnd.accounts.AccountServiceImpl;
import com.viteksu.kursach.web.backEnd.accounts.UserProfile;

public class RegistrationMsg extends AccountServiceMsg {
    private String userId;
    private String pass;

    public RegistrationMsg(Address from, Address to, String login, String pass, String id) {
        super(from, to, login);
        this.userId = id;
        this.pass = pass;
    }

    @Override
    public void execute(AccountServiceImpl accountService) {
        UserProfile userProfile = new UserProfile(getLogin(), pass);

        boolean result = accountService.addNewUser(userProfile);
        accountService.getMessageSystem().sendMessage(new RegistredMsg(getTo(), getFrom(), userProfile, result));

    }

    public String getUserId() {
        return userId;
    }

    public String getPass() {
        return pass;
    }
}