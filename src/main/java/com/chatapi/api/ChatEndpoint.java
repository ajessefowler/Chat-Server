package com.chatapi.api;

import com.chatapi.authentication.JWTService;
import com.chatapi.authentication.models.User;
import com.chatapi.base.DatabaseService;
import com.chatapi.base.models.Conversation;
import com.chatapi.base.models.Message;
import com.chatapi.base.MessageDecoder;
import com.chatapi.base.MessageEncoder;

import java.util.*;
import javax.ejb.Singleton;
import javax.websocket.*;
import javax.websocket.server.*;
import java.io.IOException;

@Singleton
@ServerEndpoint(value = "/chat", decoders = { MessageDecoder.class }, encoders = { MessageEncoder.class })
public class ChatEndpoint {

    private JWTService webTokenService = new JWTService();
    private DatabaseService dbService = new DatabaseService();
    private static Map<String, Session> activeSessions = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println("New session created: " + session.getId());
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        String type = message.getType();

        switch (type) {
            case "connect":
                /*if (webTokenService.validateJWS(message.getOrigin().getUsername(), message.getContent())) {
                    activeSessions.put(message.getOrigin().getUsername(), session);
                }*/
                activeSessions.put(message.getOrigin().getUsername(), session);
                break;
            case "chat":
                int conversationId = message.getConversation().getId();
                Conversation conversation = dbService.getConversation(conversationId);
                List<User> recipients = conversation.getUsers();

                message.setRecipients(recipients);

                for (User recipient : recipients) {
                    String username = recipient.getUsername();

                    if (username != message.getOrigin().getUsername()) {
                        if (activeSessions.containsKey(username)) {
                            activeSessions.get(username).getAsyncRemote().sendObject(message);
                        } else {
                            // Send notification to recipient
                        }
                    } else {
                        dbService.getUser(username).addSentMessage(message);
                        // TODO - Update user
                    }
                }

                // Add message and update conversation in database
                conversation.addMessage(message);
                dbService.addMessage(message);
                dbService.updateConversation(conversation);

                break;
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("Session " + session.getId() + " closed");
    }

    @OnError
    public void onError(Session session, Throwable exception) {
        exception.printStackTrace();
    }
}
