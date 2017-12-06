package com.viteksu.kursach.core.messageSystem.messages.frontEnd;

import com.viteksu.kursach.core.messageSystem.addressService.Address;
import com.viteksu.kursach.web.backEnd.accounts.UserProfile;
import com.viteksu.kursach.web.frontEnd.FrontEndService;
import com.viteksu.kursach.web.frontEnd.FrontEndServiceImpl;

public class RegistredMsg extends FrontEndMsg {
    private final boolean result;

    public RegistredMsg(Address from, Address to, UserProfile userProfile1, boolean result) {
        super(from, to, userProfile1);
        this.result = result;
    }

    @Override
    public void execute(FrontEndService frontEnd) {
        frontEnd.setRegistred(getUserProfile(), result);
    }
}