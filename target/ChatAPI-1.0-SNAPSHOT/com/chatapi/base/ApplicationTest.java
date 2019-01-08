package com.chatapi.base;

import com.chatapi.authentication.AuthenticationService;
import com.chatapi.authentication.PasswordEncryptionService;
import com.chatapi.authentication.models.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class ApplicationTest {
    public static void main(String[] args) {
        DatabaseService dbManager = new DatabaseService();
        PasswordEncryptionService encryptService = new PasswordEncryptionService();
        AuthenticationService authService = new AuthenticationService();

        try {
            byte[] salt1 = encryptService.generateSalt();
            byte[] salt2 = encryptService.generateSalt();

            User user1 = new User("jesse", encryptService.getEncryptedPassword("test", salt1), salt1);
            User user2 = new User("test", encryptService.getEncryptedPassword("test2", salt2), salt2);

            dbManager.addUser(user1);
            dbManager.addUser(user2);

            //String tokenString = authService.authenticateUser("jesse", "test");
            //System.out.println(tokenString);

            //System.out.println("Retrieved user " + dbManager.getUser("jesse").getUsername());
            //System.out.println("Retrieved token " + dbManager.getToken("jesse").getToken() + " for user " + dbManager.getToken("jesse").getUser());
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getStackTrace());
        } catch (InvalidKeySpecException e) {
            System.out.println(e.getStackTrace());
        }
    }
}
