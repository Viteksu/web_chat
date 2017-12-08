package com.viteksu.kursach.core.messageSystem.messages.userDataService.userProfile;

import com.viteksu.kursach.core.messageSystem.addressService.Address;
import com.viteksu.kursach.core.messageSystem.messages.frontEnd.AuthenticatedMsg;
import com.viteksu.kursach.web.backEnd.accounts.UserDataServiceImpl;
import com.viteksu.kursach.web.backEnd.accounts.UserProfile;

public class Authorization extends UserProfileMsg {
    private final String pass;

    public Authorization(Address from, Address to, String login, String pass) {
        super(from, to, login);
        this.pass = pass;
    }

    protected String getPass() {
        return pass;
    }

    @Override
    public void execute(UserDataServiceImpl accountService) {
        UserProfile user = accountService.getUserByLogin(getLogin());


        if (user != null && user.getPass().equals(getPass())) {
            accountService.getMessageSystem().sendMessage(new AuthenticatedMsg(getTo(), getFrom(), user));
            return;
        }


        accountService.getMessageSystem().sendMessage(new AuthenticatedMsg(getTo(), getFrom(), null));
    }
}