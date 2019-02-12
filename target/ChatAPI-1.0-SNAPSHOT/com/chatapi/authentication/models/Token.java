package com.chatapi.authentication.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "TOKENS" )
public class Token implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String token;
    private String user;
    @Lob
    private byte[] tokenKey;

    public Token() {}
    public Token(String token, String user, byte[] key) {
        this.token = token;
        this.user = user;
        this.tokenKey = key;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public String getUser() { return user; }

    public void setUser(String user) { this.user = user; }

    public  byte[] getKey() { return tokenKey; }

    public void setKey(byte[] key) { this.tokenKey = key; }
}
