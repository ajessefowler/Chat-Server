package com.chatapi.authentication;

import com.chatapi.authentication.models.Token;
import com.chatapi.base.DatabaseService;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class AuthenticationService {
    private DatabaseService dbManager = new DatabaseService();

    // Issue a token (can be a random String persisted to a database or a JWT token)
    // The issued token must be associated to a user
    // Return the issued token
    public String issueToken(String username) {
        Random random = new SecureRandom();
        String tokenString = new BigInteger(130, random).toString(32);
        Token token = new Token(tokenString, username);
        dbManager.addToken(token);
        return tokenString;
    }
}
