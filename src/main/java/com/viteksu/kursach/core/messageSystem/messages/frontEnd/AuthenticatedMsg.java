package com.viteksu.kursach.core.messageSystem.messages.frontEnd;

import com.viteksu.kursach.core.messageSystem.addressService.Address;
import com.viteksu.kursach.web.backEnd.accounts.UserProfile;
import com.viteksu.kursach.web.frontEnd.FrontEndService;
import com.viteksu.kursach.web.frontEnd.FrontEndServiceImpl;

public class AuthenticatedMsg extends FrontEndMsg {

    public AuthenticatedMsg(Address from, Address to, UserProfile userProfile) {
        super(from, to, userProfile);
        System.err.println("Sending back mess");

    }

    @Override
    public void execute(FrontEndService frontEnd) {
        frontEnd.setAuthenticated(getUserProfile());
    }
}
