package com.chatapi.base;

import java.util.*;
import javax.websocket.*;
import javax.websocket.server.*;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class Endpoint {
    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println("New session created: " + session.getId());
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println("New message: " + message);
        /*if (!sessions.containsKey(message.getOrigin().getUsername())) {
            sessions.put(message.getOrigin().getUsername(), session);
        }

        if (sessions.containsKey(message.getRecipient().getUsername())) {
            sessions.get(message.getRecipient().getUsername()).getAsyncRemote().sendObject(message);
        } else {
            // Send notification to recipient
        }*/
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("Session " + session.getId() + " closed");
    }

    @OnError
    public void onError(Session session, Throwable exception) {
        System.out.println(exception.getStackTrace());
    }
}
