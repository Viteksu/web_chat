package com.viteksu.kursach.core.messageSystem.messages;

import com.viteksu.kursach.core.messageSystem.Abonent;
import com.viteksu.kursach.core.messageSystem.addressService.Address;

public abstract class Message {
    private final Address from;
    private final Address to;

    protected Message(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    protected Address getFrom() {
        return from;
    }


    public Address getTo() {
        return to;
    }

    public abstract void execute(Abonent abonent);
}