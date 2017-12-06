package com.viteksu.kursach.core.messageSystem.messages.database;

import com.viteksu.kursach.core.messageSystem.Abonent;
import com.viteksu.kursach.core.messageSystem.addressService.Address;
import com.viteksu.kursach.web.backEnd.accounts.AccountService;
import com.viteksu.kursach.web.backEnd.accounts.AccountServiceImpl;
import com.viteksu.kursach.web.backEnd.accounts.Message;

import java.util.List;

abstract public class AddingMassages extends com.viteksu.kursach.core.messageSystem.messages.Message {
    private List<Message> messages;

    public AddingMassages(Address from, Address to, List<Message> messages) {
        super(from, to);
        this.messages = messages;
    }

    @Override
    public void execute(Abonent abonent) {
        if (abonent instanceof AccountService)
            execute((AccountService) abonent);

    }

    public void execute(AccountService accountService) {

    }

    public List<Message> getMessages() {
        return messages;
    }
}
