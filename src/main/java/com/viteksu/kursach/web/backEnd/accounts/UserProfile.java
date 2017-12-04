package com.viteksu.kursach.web.backEnd.accounts;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = -8768971425626132938L;

    private UserProfile() {
        login = " ";
        pass = " ";
    }

    @Override
    public boolean equals(Object o) {
        UserProfile user = (UserProfile) o;

        if (user.login.equals(login) && user.pass.equals(pass))
            return true;
        else
            return false;
    }

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", unique = true, nullable = false)
    private final String login;

    @Column(name = "password", nullable = false)
    private final String pass;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Message.class)
    @JoinTable(name = "user_mess",
            joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "mess_id")})
    private List<Message> messages = new LinkedList<>();

    public UserProfile(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }


    public void addMassage(Message message) {
        messages.add(message);

    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public Long getId() {
        return id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void removeMess(String mess) {
        messages.remove(mess);
    }

    @Override
    public String toString() {
        return "id-" + id + " login-" + login + " pass-" + pass + "\n" + messages;
    }
}
