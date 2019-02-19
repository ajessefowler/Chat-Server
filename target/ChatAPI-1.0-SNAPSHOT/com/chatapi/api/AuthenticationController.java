package com.chatapi.api;

import com.chatapi.authentication.EncryptionService;
import com.chatapi.authentication.JWTService;
import com.chatapi.authentication.models.Token;
import com.chatapi.authentication.models.User;
import com.chatapi.authentication.models.UserCredentials;
import com.chatapi.base.DatabaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;

@Path("/authentication")
public class AuthenticationController {
    private DatabaseService dbService = new DatabaseService();
    private EncryptionService encryptService = new EncryptionService();
    private JWTService webTokenService = new JWTService();

    @POST
    @Path("register")
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String registerUser(@FormParam("username") String username, @FormParam("password") String password) {
        try {
            byte[] salt = encryptService.generateSalt();
            byte[] encryptedPassword = encryptService.getEncryptedPassword(password, salt);

            User user = new User(username);
            UserCredentials credentials = new UserCredentials(encryptedPassword, salt);
            user.setCredentials(credentials);
            credentials.setUser(user);

            dbService.addUser(user);

            return "Successfully created user " + username;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("username") String username, @FormParam("password") String attemptedPassword) {
        User retrievedUser = dbService.getUser(username);

        try {
            byte[] retrievedPassword = retrievedUser.getCredentials().getPassword();
            byte[] retrievedSalt = retrievedUser.getCredentials().getSalt();

            // Authenticate the user using the credentials provided
            if (encryptService.authenticate(attemptedPassword, retrievedPassword, retrievedSalt)) {
                Token token = webTokenService.createJWS(username);
                retrievedUser.setToken(token);

                // Send user object to json to be able to include token string
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String userJson = ow.writeValueAsString(retrievedUser);

                return Response.status(Response.Status.OK).entity(userJson).build();
            } else {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } finally {
            retrievedUser.setToken(null);
        }
    }

    @POST
    @Path("is-active")
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response tokenIsActive(@FormParam("username") String user, @FormParam("jws") String jws) {
        String isActive = String.valueOf(webTokenService.validateJWS(user, jws));
        return Response.ok(isActive).header("Access-Control-Allow-Origin", "*").build();
    }
}
