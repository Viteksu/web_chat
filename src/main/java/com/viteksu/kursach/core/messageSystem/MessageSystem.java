package com.viteksu.kursach.core.messageSystem;

import com.viteksu.kursach.core.messageSystem.addressService.Address;
import com.viteksu.kursach.core.messageSystem.addressService.AddressService;
import com.viteksu.kursach.core.messageSystem.messages.Message;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageSystem {
    private final Map<Address, ConcurrentLinkedQueue<Message>> messages = new HashMap<>();
    private final AddressService addressService = AddressService.getInstance();

    public MessageSystem() {
        addressService.registerMessageSystem(this);
    }

    public void addService(Abonent abonent) {
        messages.put(abonent.getAddress(), new ConcurrentLinkedQueue<>());
    }

    public void sendMessage(Message message) {
        ConcurrentLinkedQueue<Message> queue = messages.get(message.getTo());

        synchronized (queue) {
            queue.add(message);
            queue.notifyAll();
        }
    }

    public void executeForAbonent(Abonent abonent) {
        ConcurrentLinkedQueue<Message> queue = messages.get(abonent.getAddress());

        synchronized (queue) {

            while (!queue.isEmpty()) {
                Message message = queue.poll();
                message.execute(abonent);
            }

            try {
                queue.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
