package com.chatapi.base.models;

import com.chatapi.authentication.models.User;
import com.chatapi.base.DatabaseService;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

@Entity
@Table( name = "MESSAGES" )
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String type;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User origin;
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;
    @Column(name = "timestamp", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    private String content;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Transient
    private DatabaseService dbService = new DatabaseService();

    public Message() {
        this.timestamp = new Date();
        this.status = Status.CREATED;
    }

    public Message(String type, String content) {
        this.type = type;
        this.content = content;
        this.timestamp = new Date();
        this.status = Status.CREATED;
    }

    public Message(String type, String origin, String content) {
        this.type = type;
        this.origin = dbService.getUser(origin);
        this.content = content;
        this.timestamp = new Date();
        this.status = Status.CREATED;
    }

    public Message(String type, String origin, String content, int conversationId) {
        this.type = type;
        this.origin = dbService.getUser(origin);
        this.content = content;
        this.conversation = dbService.getConversation(conversationId);
        this.timestamp = new Date();
        this.status = Status.CREATED;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public User getOrigin() { return origin; }

    public void setOrigin(User origin) { this.origin = origin; }

    public Conversation getConversation() { return conversation; }

    public void setConversation(Conversation conversation) { this.conversation = conversation; }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    public String getContent() {
        return content;
    }

    public void setContent(String content) { this.content = content; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }
}
