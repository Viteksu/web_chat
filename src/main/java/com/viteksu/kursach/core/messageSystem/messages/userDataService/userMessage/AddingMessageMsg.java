package com.viteksu.kursach.core.messageSystem.messages.userDataService.userMessage;

import com.viteksu.kursach.core.messageSystem.Abonent;
import com.viteksu.kursach.core.messageSystem.addressService.Address;
import com.viteksu.kursach.web.backEnd.accounts.Message;
import com.viteksu.kursach.web.backEnd.accounts.UserDataService;

import java.util.List;

public class AddingMessageMsg extends com.viteksu.kursach.core.messageSystem.messages.Message {
    private List<Message> messages;

    public AddingMessageMsg(Address from, Address to, List<Message> messages) {
        super(from, to);
        this.messages = messages;
    }

    @Override
    public void execute(Abonent abonent) {
        execute((UserDataService) abonent);

    }

    public void execute(UserDataService userDataService) {
        userDataService.addMessages(messages);
    }

    public List<Message> getMessages() {
        return messages;
    }
}
