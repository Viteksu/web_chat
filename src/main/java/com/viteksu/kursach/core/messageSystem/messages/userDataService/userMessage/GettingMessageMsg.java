package com.viteksu.kursach.core.messageSystem.messages.userDataService.userMessage;

import com.viteksu.kursach.core.messageSystem.Abonent;
import com.viteksu.kursach.core.messageSystem.addressService.Address;
import com.viteksu.kursach.core.messageSystem.addressService.AddressService;
import com.viteksu.kursach.web.backEnd.accounts.Message;
import com.viteksu.kursach.web.backEnd.accounts.UserDataService;
import com.viteksu.kursach.web.backEnd.accounts.UserProfile;

import java.util.List;

public class GettingMessageMsg extends com.viteksu.kursach.core.messageSystem.messages.Message {
    public static final String RECIPIENT = "recipient";
    public static final String SENDER = "SENDER";

    private String user;
    private String who;

    public GettingMessageMsg(Address from, Address to, String user, String who) {
        super(from, to);
        this.user = user;
        this.who = who;
    }

    @Override

    public void execute(Abonent abonent) {
        if (abonent instanceof UserDataService) {
            execute((UserDataService) abonent);
        }
    }

    public void execute(UserDataService userDataService) {
        List<Message> messages = null;
        if (who.equals(RECIPIENT)) {
            messages = userDataService.getMessagesByRecipient(user);
        } else if (who.equals(SENDER)) {
            messages = userDataService.getMessagesBySender(user);
        }


        AddressService.getInstance().getMessageSystem().sendMessage(new SettingMessageMsg(getTo(), getFrom(), user, messages));
    }

    public String getWho() {
        return who;
    }

    public String getLogin() {
        return user;
    }
}
