import java.time.LocalDateTime;

public class Message {
    private User origin;
    private User recipient;
    private LocalDateTime timestamp;
    private String content;

    public Message(User origin, User recipient, LocalDateTime timestamp, String content) {
        this.origin = origin;
        this.recipient = recipient;
        this.timestamp = timestamp;
        this.content = content;
    }

    public User getOrigin() {
        return origin;
    }

    public User getRecipient() {
        return recipient;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }
}
