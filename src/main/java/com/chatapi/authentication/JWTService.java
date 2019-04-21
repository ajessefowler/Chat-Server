package com.chatapi.authentication;

import com.chatapi.authentication.models.Token;
import com.chatapi.authentication.models.User;
import com.chatapi.base.DatabaseService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JWTService {
    private DatabaseService dbService = new DatabaseService();

    // TODO - ASAP - Read from file for consistency
    private Key key = generateSecretKey();

    public Token createJWS(String username) {
        User user = dbService.getUser(username);

        // If a token already exists for the user, delete the existing token
        Token existingToken = dbService.getToken(user);

        if (existingToken != null) {
            user.setToken(null);
            dbService.updateUser(user);
            dbService.deleteToken(existingToken);
        }

        String jws = Jwts.builder().setSubject(username).signWith(key).compact();
        Token token = new Token(jws);

        user.setToken(token);
        token.setUser(user);
        dbService.addToken(token);
        dbService.updateUser(user);

        return token;
    }

    public boolean validateJWS(String username, String jws) {
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(jws).getBody().getSubject().equals(username);
        } catch (JwtException e) {
            // TODO - Display exception
            return false;
        }
    }

    private Key generateSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
}
