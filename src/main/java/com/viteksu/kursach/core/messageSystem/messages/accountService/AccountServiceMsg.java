package com.viteksu.kursach.core.messageSystem.messages.accountService;

import com.viteksu.kursach.core.messageSystem.Abonent;
import com.viteksu.kursach.core.messageSystem.addressService.Address;
import com.viteksu.kursach.core.messageSystem.messages.Message;
import com.viteksu.kursach.web.backEnd.accounts.AccountService;
import com.viteksu.kursach.web.backEnd.accounts.AccountServiceImpl;

public abstract class AccountServiceMsg extends Message {
    private final String login;

    @Override
    public void execute(Abonent abonent) {
        if (abonent instanceof AccountService)
            execute((AccountServiceImpl) abonent);

    }

    abstract void execute(AccountServiceImpl accountService);

    AccountServiceMsg(Address from, Address to, String login) {
        super(from, to);
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
