package com.chatapi.authentication.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table( name = "TOKENS" )
public class Token implements Serializable {
    private int id;
    private String token;
    private String user;

    public Token() {}
    public Token(String token, String user) {
        this.token = token;
        this.user = user;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public String getUser() { return user; }

    public void setUser(String user) { this.user = user; }
}
