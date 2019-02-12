package com.chatapi.api;

import com.chatapi.authentication.EncryptionService;
import com.chatapi.authentication.models.Token;
import com.chatapi.authentication.models.User;
import com.chatapi.base.DatabaseService;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.util.UUID;

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

            User user = new User(username, encryptedPassword, salt);
            dbService.addUser(user);

            return "Successfully created user " + username;
        } catch (Exception e) {
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
            User retrievedUser = dbService.getUser(username);

            // Authenticate the user using the credentials provided
            if (encryptService.authenticate(password, retrievedUser.getPassword(), retrievedUser.getSalt())) {
                String token = webTokenService.buildAndPersistJWS(username);
                return Response.ok(token).build();
            } else {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).build();
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

    // Service to build and validate JSON Web Tokens
    private class JWTService {
        private String buildAndPersistJWS(String username) {
            Key key = generateSecretKey();
            byte[] keyArray = key.getEncoded();
            String id = UUID.randomUUID().toString();

            String jws = Jwts.builder().setId(id).setSubject(username).signWith(key).compact();

            dbService.addToken(new Token(id, username, keyArray));

            return jws;
        }

        private boolean validateJWS(String username, String jws) {
            try {
                Key key = Keys.hmacShaKeyFor(dbService.getToken(username).getKey());
                assert Jwts.parser().setSigningKey(key).parseClaimsJws(jws).getBody().getSubject().equals(username);
                return true;
            } catch (JwtException e) {
                // Display exception
                return false;
            }
        }

        private Key generateSecretKey() {
            return Keys.secretKeyFor(SignatureAlgorithm.HS256);
        }
    }

    private class LoginResponse {
        String user;
        String jws;

        private LoginResponse() {}

        private LoginResponse(String user, String jws) {
            this.user = user;
            this.jws = jws;
        }
    }
}
