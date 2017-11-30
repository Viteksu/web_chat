package com.viteksu.kursach.web.backEnd.accounts;


import javax.persistence.*;
import java.io.Serializable;

public class Message {
    private final String type;
    private final String sender;
    private final String recipient;
    private final String message;

    public Message(String type, String sender, String recipient, String mess) {
        this.sender = sender;
        this.message = mess;
        this.recipient = recipient;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getRecipient() {
        return recipient;
    }
}



/*
@Entity
@Table(name = "message")
public class Message implements Serializable {

    private static final long serialVersionUID = -7611543623321789238L;

    @Id
    @Column(name = "mess_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message", nullable = false)
    private final String message;

    public Message() {
        message = "";
    }

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return message;
    }
}
*/
