import java.util.*;
import java.util.stream.Collectors;

public class User {
    private String username;
    private String password;
    private List<Message> messages;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.messages = new ArrayList<>();
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String newPassword) { this.password = newPassword; }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public Map<String, List<Message>> getAllMessages() {
        Map<String, List<Message>> messagesByUser = new HashMap<>();

        for (Message message : this.messages) {
            if (!messagesByUser.containsKey(message.getOrigin().getUsername())) {
                messagesByUser.put(message.getOrigin().getUsername(), new ArrayList<>());
            }
            messagesByUser.get(message.getOrigin().getUsername()).add(message);
        }

        return messagesByUser;
    }

    public List<Message> getMessagesByUsername(String username) {
        return this.messages.stream().filter(message -> message.getOrigin().getUsername() == username).collect(Collectors.toList());
    }
}
