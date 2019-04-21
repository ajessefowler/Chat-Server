package com.chatapi.base;

import com.chatapi.api.AuthenticationController;
import com.chatapi.api.ConversationController;
import com.chatapi.authentication.EncryptionService;
import com.chatapi.authentication.models.User;
import com.chatapi.base.models.Conversation;

import java.util.ArrayList;
import java.util.List;

public class ApplicationTest {
    public static void main(String[] args) {
        DatabaseService dbManager = new DatabaseService();
        EncryptionService encryptService = new EncryptionService();
        AuthenticationController authController = new AuthenticationController();
        ConversationController cController = new ConversationController();

        /*authController.registerUser("test2", "test", "jesse", "fowler", "j@f.com");
        authController.registerUser("test3", "test", "jesse", "fowler", "j@f.com");*/

        List<String> usernames = new ArrayList<>();

        usernames.add("test2");
        usernames.add("test3");

        System.out.println(cController.createConversation(usernames));

        //System.out.println(dbManager.getUser("jesse").getUsername());

        /*try {
            Conversation conversation = new Conversation(users);
            dbManager.addConversation(conversation);

            user1.addConversation(conversation);
            user2.addConversation(conversation);

            dbManager.updateUser(user1);
            dbManager.updateUser(user2);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //System.out.println(authController.tokenIsActive("new", jws).getEntity().toString());
    }
}
