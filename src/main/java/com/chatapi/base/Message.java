package com.chatapi.base;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

@Entity
@Table( name = "MESSAGES" )
public class Message implements Serializable {
    private int id;
    private User origin;
    private User recipient;
    @Column(name = "timestamp", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    private String content;

    public Message() {}
    public Message(User origin, User recipient, Date timestamp, String content) {
        this.origin = origin;
        this.recipient = recipient;
        this.timestamp = timestamp;
        this.content = content;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public User getOrigin() {
        return origin;
    }

    public void setOrigin(User origin) { this.origin = origin; }

    public User getRecipient() { return recipient; }

    public void setRecipient(User recipient) { this.recipient = recipient; }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    public String getContent() {
        return content;
    }

    public void setContent(String content) { this.content = content; }
}
