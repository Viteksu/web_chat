package com.viteksu.kursach.core.messageSystem.messages.frontEnd;

import com.viteksu.kursach.core.messageSystem.Abonent;
import com.viteksu.kursach.core.messageSystem.addressService.Address;
import com.viteksu.kursach.core.messageSystem.messages.Message;
import com.viteksu.kursach.web.backEnd.accounts.UserProfile;
import com.viteksu.kursach.web.frontEnd.FrontEndService;

public abstract class FrontEndMsg extends Message {
    private UserProfile userProfile;

    FrontEndMsg(Address from, Address to, UserProfile userProfile1) {
        super(from, to);
        this.userProfile = userProfile1;
    }

    @Override
    public void execute(Abonent abonent) {
        if (abonent instanceof FrontEndService)
            execute((FrontEndService) abonent);
    }

    abstract void execute(FrontEndService frontEnd);

    protected UserProfile getUserProfile() {
        return userProfile;
    }
}