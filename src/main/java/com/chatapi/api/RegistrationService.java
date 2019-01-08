package com.chatapi.api;

import com.chatapi.authentication.PasswordEncryptionService;
import com.chatapi.authentication.models.User;
import com.chatapi.base.DatabaseManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Path("/register")
public class RegistrationService {
    private DatabaseManager dbManager = new DatabaseManager();
    private PasswordEncryptionService encryptService = new PasswordEncryptionService();

    @POST
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String registerUser(@FormParam("username") String username, @FormParam("password") String password) {
        try {
            byte[] salt = encryptService.generateSalt();
            byte[] encryptedPassword = encryptService.getEncryptedPassword(password, salt);

            User user = new User(username, encryptedPassword, salt);
            dbManager.addUser(user);

            return "Successfully created user " + username;
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getStackTrace());
            return e.getMessage();
        } catch (InvalidKeySpecException e) {
            System.out.println(e.getStackTrace());
            return e.getMessage();
        }
    }
}
