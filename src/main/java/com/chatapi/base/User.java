package com.chatapi.base;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import java.io.Serializable;

@Entity
@Table( name = "USERS" )
public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    @ElementCollection
    private List<Message> messages;

    public User() {}
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.messages = new ArrayList<>();
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String newPassword) { this.password = newPassword; }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    @Transient
    public Map<String, List<Message>> getAllMessages() {
        Map<String, List<Message>> messagesByUser = new HashMap<>();

        for (Message message : this.messages) {
            if (!messagesByUser.containsKey(message.getOrigin().getUsername())) {
                messagesByUser.put(message.getOrigin().getUsername(), new ArrayList<>());
            }
            messagesByUser.get(message.getOrigin().getUsername()).add(message);
        }

        return messagesByUser;
    }

    @Transient
    public List<Message> getMessagesByUsername(String username) {
        return this.messages.stream().filter(message -> message.getOrigin().getUsername() == username).collect(Collectors.toList());
    }
}
