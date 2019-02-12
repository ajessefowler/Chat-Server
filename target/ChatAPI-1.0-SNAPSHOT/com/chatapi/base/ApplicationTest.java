package com.chatapi.base;

import com.chatapi.api.AuthenticationController;
import com.chatapi.authentication.EncryptionService;

import javax.ws.rs.core.Response;

public class ApplicationTest {
    public static void main(String[] args) {
        DatabaseService dbManager = new DatabaseService();
        EncryptionService encryptService = new EncryptionService();
        AuthenticationController authController = new AuthenticationController();

        authController.registerUser("new", "newpass");

        //Response response = authController.authenticateUser("new", "newpass");

        //String jws = response.getEntity().toString();
        //System.out.println(jws);

        //System.out.println(authController.tokenIsActive("new", jws).getEntity().toString());
    }
}
