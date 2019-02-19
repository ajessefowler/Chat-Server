package com.chatapi.base;

import com.chatapi.api.AuthenticationController;
import com.chatapi.authentication.EncryptionService;

import javax.ws.rs.core.Response;

public class ApplicationTest {
    public static void main(String[] args) {
        DatabaseService dbManager = new DatabaseService();
        EncryptionService encryptService = new EncryptionService();
        AuthenticationController authController = new AuthenticationController();

        //authController.registerUser("admin4", "admin4");

        try {
            Response response = authController.authenticateUser("admin4", "admin4");

            System.out.println(response.getEntity().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(authController.tokenIsActive("new", jws).getEntity().toString());
    }
}
