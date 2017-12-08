package com.viteksu.kursach.web.backEnd.accounts;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "messages")
public class Message implements Serializable {
    transient private static final long serialVersionUID = -7611543623321789238L;

    transient public static final String TYPE_HISTORY = "HISTORY";

    @Id
    @Column(name = "id", nullable = false)
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

    public Message() {
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

    public static Message setType(Message message, String type) {
        Message result = new Message();
        result.type = type;
        result.message = message.message;
        result.sender = message.sender;
        result.recipient = message.recipient;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        Message message = (Message) obj;

        if (message.recipient.equals(recipient) && message.sender.equals(sender)
                && message.type.equals(type) && message.message.equals(this.message)) {
            return true;
        }

        return false;

    }

    @Override
    public int hashCode() {
        return type.hashCode() * message.hashCode() / sender.hashCode() - (recipient.hashCode()) - 4341;
    }

    @Override
    public String toString() {
        return recipient + " " + sender + " " + recipient + " " + message;
    }
}
