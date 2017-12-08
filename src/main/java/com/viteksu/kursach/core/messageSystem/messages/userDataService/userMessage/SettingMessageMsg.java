package com.viteksu.kursach.core.messageSystem.messages.userDataService.userMessage;

import com.viteksu.kursach.core.messageSystem.Abonent;
import com.viteksu.kursach.core.messageSystem.addressService.Address;
import com.viteksu.kursach.web.backEnd.accounts.Message;
import com.viteksu.kursach.web.frontEnd.FrontEndService;

import java.util.List;

public class SettingMessageMsg extends com.viteksu.kursach.core.messageSystem.messages.Message {
    private String user;
    private List<Message> messages;

    public SettingMessageMsg(Address from, Address to, String user, List<Message> messages) {
        super(from, to);
        this.user = user;
        this.messages = messages;
    }

    @Override

    public void execute(Abonent abonent) {
        execute((FrontEndService) abonent);

    }

    public void execute(FrontEndService frontEndService) {


        frontEndService.setUserMessages(user, messages);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public String getLogin() {
        return user;
    }
}
