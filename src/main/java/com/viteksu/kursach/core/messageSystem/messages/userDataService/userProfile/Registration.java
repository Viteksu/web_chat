package com.viteksu.kursach.core.messageSystem.messages.userDataService.userProfile;


import com.viteksu.kursach.core.messageSystem.addressService.Address;
import com.viteksu.kursach.core.messageSystem.messages.frontEnd.RegistredMsg;
import com.viteksu.kursach.web.backEnd.accounts.UserDataServiceImpl;
import com.viteksu.kursach.web.backEnd.accounts.UserProfile;

public class Registration extends UserProfileMsg {
    private String userId;
    private String pass;

    public Registration(Address from, Address to, String login, String pass, String id) {
        super(from, to, login);
        this.userId = id;
        this.pass = pass;
    }

    @Override
    public void execute(UserDataServiceImpl accountService) {
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