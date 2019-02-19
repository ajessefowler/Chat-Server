package com.chatapi.authentication.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "TOKENS" )
public class Token implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @JsonIgnore
    private String id;
    @JsonSerialize
    @JsonDeserialize
    @Transient
    private String token;
    @OneToOne
    @JsonIgnore
    private User user;

    public Token() {}
    public Token(String token) {
        this.token = token;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}
