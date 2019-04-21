package com.chatapi.base;

import com.chatapi.api.AuthenticationController;
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

        authController.registerUser("jesse", "test", "jesse", "fowler", "j@f.com");

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
