package com.viteksu.kursach.core.messageSystem.messages.userDataService.userProfile;

import com.viteksu.kursach.core.messageSystem.Abonent;
import com.viteksu.kursach.core.messageSystem.addressService.Address;
import com.viteksu.kursach.core.messageSystem.messages.Message;
import com.viteksu.kursach.web.backEnd.accounts.UserDataService;
import com.viteksu.kursach.web.backEnd.accounts.UserDataServiceImpl;

public abstract class UserProfileMsg extends Message {
    private final String login;

    @Override
    public void execute(Abonent abonent) {
            execute((UserDataServiceImpl) abonent);

    }

    abstract void execute(UserDataServiceImpl accountService);

    UserProfileMsg(Address from, Address to, String login) {
        super(from, to);
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
