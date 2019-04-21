package com.chatapi.authentication.models;

import com.chatapi.base.models.Conversation;
import com.chatapi.base.models.Message;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.*;
import java.io.Serializable;

@Entity
@Table( name = "USERS" )
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonIgnore
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private UserCredentials credentials;
    @OneToOne
    @JsonSerialize
    @JsonDeserialize
    private Token token;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "USER_CONVERSATIONS",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "conversation_id") }
    )
    private List<Conversation> conversations;
    @OneToMany(mappedBy = "origin")
    private List<Message> sentMessages;

    public User() {
        this.conversations = new ArrayList<>();
    }

    public User(String username) {
        this.username = username;
        this.conversations = new ArrayList<>();
    }

    public User(String username, String firstName, String lastName, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.conversations = new ArrayList<>();
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public UserCredentials getCredentials() { return credentials; }

    public void setCredentials(UserCredentials credentials) { this.credentials = credentials; }

    public Token getToken() { return token; }

    public void setToken(Token token) { this.token = token; }

    public List<Conversation> getConversations() { return conversations; }

    public void setConversations(List<Conversation> conversations) { this.conversations = conversations; }

    public void addConversation(Conversation conversation) { this.conversations.add(conversation); }

    public List<Message> getSentMessages() { return sentMessages; }

    public void setSentMessages(List<Message> sentMessages) { this.sentMessages = sentMessages; }

    public void addSentMessage(Message message) { this.sentMessages.add(message); }
}
