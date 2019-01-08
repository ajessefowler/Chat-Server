package com.chatapi.base.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

@Entity
@Table( name = "MESSAGES" )
public class Message implements Serializable {
    private int id;
    private String type;
    private String origin;
    private String recipient;
    @Column(name = "timestamp", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    private String content;

    public Message() {}
    public Message(String type, String origin, String recipient, Date timestamp, String content) {
        this.type = type;
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

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) { this.origin = origin; }

    public String getRecipient() { return recipient; }

    public void setRecipient(String recipient) { this.recipient = recipient; }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    public String getContent() {
        return content;
    }

    public void setContent(String content) { this.content = content; }
}
