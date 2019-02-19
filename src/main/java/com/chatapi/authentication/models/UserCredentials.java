package com.chatapi.authentication.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "USER_CREDENTIALS" )
public class UserCredentials implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = {CascadeType.ALL})
    private User user;
    @Lob
    private byte[] password;
    @Lob
    private byte[] salt;

    public UserCredentials() { }

    public UserCredentials(byte[] password, byte[] salt) {
        this.password = password;
        this.salt = salt;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public byte[] getPassword() { return password; }

    public void setPassword(byte[] password) { this.password = password; }

    public byte[] getSalt() { return salt; }

    public void setSalt(byte[] salt) { this.salt = salt; }
}
