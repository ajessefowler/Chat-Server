package com.chatapi.authentication.models;

import com.chatapi.base.models.Message;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import java.io.Serializable;

@Entity
@Table( name = "USERS" )
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    @Lob
    private byte[] password;
    @Lob
    private byte[] salt;
    @ElementCollection
    private List<Message> messages;

    public User() {}

    public User(String username, byte[] password, byte[] salt) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.messages = new ArrayList<>();
    }

    public User(String username, String firstName, String lastName, String email, byte[] password, byte[] salt) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.messages = new ArrayList<>();
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

    public byte[] getPassword() { return password; }

    public void setPassword(byte[] newPassword) { this.password = newPassword; }

    public byte[] getSalt() { return salt; }

    public void setSalt(byte[] salt) { this.salt = salt; }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    @Transient
    public Map<String, List<Message>> getAllMessages() {
        Map<String, List<Message>> messagesByUser = new HashMap<>();

        for (Message message : this.messages) {
            if (!messagesByUser.containsKey(message.getOrigin())) {
                messagesByUser.put(message.getOrigin(), new ArrayList<>());
            }
            messagesByUser.get(message.getOrigin()).add(message);
        }

        return messagesByUser;
    }

    @Transient
    public List<Message> getMessagesByUsername(String username) {
        return this.messages.stream().filter(message -> message.getOrigin() == username).collect(Collectors.toList());
    }
}
