package com.chatapi.api;

import com.chatapi.authentication.AuthenticationService;
import com.chatapi.authentication.PasswordEncryptionService;
import com.chatapi.authentication.models.User;
import com.chatapi.base.DatabaseService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Path("/authentication")
public class AuthenticationEndpoint {
    private DatabaseService dbService = new DatabaseService();
    private PasswordEncryptionService encryptService = new PasswordEncryptionService();
    private AuthenticationService authService = new AuthenticationService();

    @POST
    @Path("register")
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String registerUser(@FormParam("username") String username, @FormParam("password") String password) {
        try {
            byte[] salt = encryptService.generateSalt();
            byte[] encryptedPassword = encryptService.getEncryptedPassword(password, salt);

            User user = new User(username, encryptedPassword, salt);
            dbService.addUser(user);

            return "Successfully created user " + username;
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getStackTrace());
            return e.getMessage();
        } catch (InvalidKeySpecException e) {
            System.out.println(e.getStackTrace());
            return e.getMessage();
        }
    }

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("username") String username, @FormParam("password") String password) {
        try {
            // Authenticate the user using the credentials provided
            User retrievedUser = dbService.getUser(username);

            if (encryptService.authenticate(password, retrievedUser.getPassword(), retrievedUser.getSalt())) {
                // Issue a token for the user
                String token = authService.issueToken(username);

                // Return the token on the response
                return Response.ok(token).build();
            } else {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
}
