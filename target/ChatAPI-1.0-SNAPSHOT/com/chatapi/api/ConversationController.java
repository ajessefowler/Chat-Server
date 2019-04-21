package com.chatapi.api;

import com.chatapi.authentication.models.User;
import com.chatapi.base.DatabaseService;
import com.chatapi.base.models.Conversation;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/conversation")
public class ConversationController {

    private DatabaseService dbService = new DatabaseService();

    @POST
    @Path("create")
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createConversation(List<String> users) {
        Conversation conversation = new Conversation(users);

        dbService.addConversation(conversation);

        for (User user : conversation.getUsers()) {
            user.addConversation(conversation);
            dbService.updateUser(user);
        }

        return String.valueOf(conversation.getId());
    }

    /*@POST
    @Path("join")
    public void joinConversation(int conversationId, String username) {

    }

    @POST
    @Path("leave")
    public void leaveConversation(int conversationId, String username) {

    }*/
}
