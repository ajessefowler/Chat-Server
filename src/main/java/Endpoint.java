import java.util.*;
import javax.websocket.*;
import javax.websocket.server.*;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat/{username}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class Endpoint {
    Map<String, List<Session>> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        if (!sessions.containsKey(username)) {
            sessions.put(username, new ArrayList<>());
        }
        sessions.get(username).add(session);
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        String recipientUsername = message.getRecipient().getUsername();
        int index = sessions.get(recipientUsername).indexOf(session);

        message.getRecipient().addMessage(message);
        sessions.get(recipientUsername).get(index).getAsyncRemote().sendObject(message);
    }

    @OnClose
    public void onClose(Session session, @PathParam("key") String key) throws IOException {
        // Handle closing here
    }

    @OnError
    public void onError(Session session, Throwable exception) {
        System.out.println(exception.getStackTrace());
    }
}
