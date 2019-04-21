package com.chatapi.base.models;

import com.chatapi.authentication.models.User;
import com.chatapi.base.DatabaseService;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "CONVERSATIONS" )
public class Conversation implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "conversation_id")
    private int id;
    @ManyToMany(mappedBy = "conversations")
    private List<User> users;
    @JsonIgnore
    @OneToMany(mappedBy = "conversation")
    private List<Message> messages;
    @Transient
    private DatabaseService dbService = new DatabaseService();

    public Conversation() {
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public Conversation(List<String> usernames) {
        List<User> users = new ArrayList<>();

        for (String username : usernames) {
            users.add(dbService.getUser(username));
        }

        this.users = users;
        this.messages = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }
}
