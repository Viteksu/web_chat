package com.viteksu.kursach.core.messageSystem.addressService;

import java.util.concurrent.atomic.AtomicInteger;

public class Address {
    private static final AtomicInteger ID_GEN = new AtomicInteger();
    private final int id = ID_GEN.incrementAndGet();

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        return ((Address) o).id == id;
    }
}