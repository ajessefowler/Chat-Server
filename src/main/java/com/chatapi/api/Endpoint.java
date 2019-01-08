package com.chatapi.api;

import com.chatapi.authentication.interfaces.Secured;
import com.chatapi.base.models.Message;
import com.chatapi.base.MessageDecoder;
import com.chatapi.base.MessageEncoder;

import java.util.*;
import javax.ejb.Singleton;
import javax.websocket.*;
import javax.websocket.server.*;
import java.io.IOException;

//@Secured
@Singleton
@ServerEndpoint(value = "/chat", decoders = { MessageDecoder.class }, encoders = { MessageEncoder.class })
public class Endpoint {
    private static Map<String, Session> sessions = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println("New session created: " + session.getId());
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        if (message.getType().equals("connect")) {
            sessions.put(message.getOrigin(), session);
            Message confirmConnection = new Message("connected", message.getOrigin(), message.getRecipient(), new Date(), null);
            session.getAsyncRemote().sendObject(confirmConnection);
        }

        if (message.getType().equals("chat")) {
            if (sessions.containsKey(message.getRecipient())) {
                sessions.get(message.getRecipient()).getAsyncRemote().sendObject(message);
            } else {
                // Send notification to recipient
            }

            // Store message in database
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
