package com.viteksu.kursach.web.backEnd.accounts;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "message")
public class Message implements Serializable {
    private static final long serialVersionUID = -7611543623321789238L;

    @Id
    @Column(name = "mess_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "sender", nullable = false)
    private String sender;
    @Column(name = "recipient", nullable = false)
    private String recipient;
    @Column(name = "message", nullable = false)
    private String message;

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

    public Long getId() {
        return id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setType(String type) {
        this.type = type;
    }

}

