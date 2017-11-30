package com.viteksu.kursach.core.messageSystem.messages.accountService;

import com.viteksu.kursach.core.messageSystem.addressService.Address;
import com.viteksu.kursach.core.messageSystem.messages.frontEnd.AuthenticatedMsg;
import com.viteksu.kursach.web.backEnd.accounts.AccountServiceImpl;
import com.viteksu.kursach.web.backEnd.accounts.UserProfile;

public class AuthorizationMsg extends AccountServiceMsg {
    private final String pass;

    public AuthorizationMsg(Address from, Address to, String login, String pass) {
        super(from, to, login);
        this.pass = pass;
    }

    protected String getPass() {
        return pass;
    }

    @Override
    public void execute(AccountServiceImpl accountService) {
        UserProfile user = accountService.getUserByLogin(getLogin());


        System.err.println("Execute auth mess ");
        if (user != null && user.getPass().equals(getPass())) {
            accountService.getMessageSystem().sendMessage(new AuthenticatedMsg(getTo(), getFrom(), user));
            return;
        }


        accountService.getMessageSystem().sendMessage(new AuthenticatedMsg(getTo(), getFrom(), null));
    }
}