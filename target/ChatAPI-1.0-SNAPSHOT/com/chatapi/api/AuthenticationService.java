package com.chatapi.api;

import com.chatapi.authentication.PasswordEncryptionService;
import com.chatapi.authentication.interfaces.Secured;
import com.chatapi.authentication.models.Token;
import com.chatapi.authentication.models.User;
import com.chatapi.base.DatabaseManager;

import javax.annotation.Priority;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.Random;

@Path("/authentication")
public class AuthenticationService {
    private DatabaseManager dbManager = new DatabaseManager();
    private PasswordEncryptionService encryptService = new PasswordEncryptionService();

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("username") String username, @FormParam("password") String password) {
        try {
            // Authenticate the user using the credentials provided
            User retrievedUser = dbManager.getUser(username);
            encryptService.authenticate(password, retrievedUser.getPassword(), retrievedUser.getSalt());

            // Issue a token for the user
            String token = issueToken(username);

            // Return the token on the response
            return Response.ok(token).build();
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    // Issue a token (can be a random String persisted to a database or a JWT token)
    // The issued token must be associated to a user
    // Return the issued token
    private String issueToken(String username) {
        Random random = new SecureRandom();
        String tokenString = new BigInteger(130, random).toString(32);
        Token token = new Token(tokenString, username);
        dbManager.addToken(token);
        return tokenString;
    }

    @Secured
    @Provider
    @Priority(Priorities.AUTHENTICATION)
    public class AuthenticationFilter implements ContainerRequestFilter {

        private static final String REALM = "flare";
        private static final String AUTHENTICATION_SCHEME = "Bearer";

        @Override
        public void filter(ContainerRequestContext requestContext) throws IOException {
            // Get the Authorization header from the request
            String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

            // Validate the Authorization header
            if (!isTokenBasedAuthentication(authorizationHeader)) {
                abortWithUnauthorized(requestContext);
                return;
            }

            // Extract the token from the Authorization header
            String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
            String username = dbManager.getToken(token).getUser();

            final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
            requestContext.setSecurityContext(new SecurityContext() {

                @Override
                public Principal getUserPrincipal() {
                    return () -> username;
                }

                @Override
                public boolean isUserInRole(String role) {
                    return true;
                }

                @Override
                public boolean isSecure() {
                    return currentSecurityContext.isSecure();
                }

                @Override
                public String getAuthenticationScheme() {
                    return AUTHENTICATION_SCHEME;
                }
            });

            try {
                // Validate the token
                validateToken(token);
            } catch (Exception e) {
                abortWithUnauthorized(requestContext);
            }
        }

        private boolean isTokenBasedAuthentication(String authorizationHeader) {
            // Check if the Authorization header is valid
            // It must not be null and must be prefixed with "Bearer" plus a whitespace
            // The authentication scheme comparison must be case-insensitive
            return authorizationHeader != null && authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
        }

        private void abortWithUnauthorized(ContainerRequestContext requestContext) {
            // Abort the filter chain with a 401 status code response
            // The WWW-Authenticate header is sent along with the response
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .header(HttpHeaders.WWW_AUTHENTICATE,
                                    AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"")
                            .build());
        }

        private void validateToken(String token) throws Exception {
            // Check if the token was issued by the server and if it's not expired
            // Throw an Exception if the token is invalid
            if (dbManager.getToken(token) == null) {
                throw new Exception("invalid token");
            }
        }
    }
}
