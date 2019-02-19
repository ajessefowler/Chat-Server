package com.chatapi.authentication;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

// https://www.javacodegeeks.com/2012/05/secure-password-storage-donts-dos-and.html
// TODO - General improvement of this class, specifically the security mechanisms

public class EncryptionService {
    public boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Encrypt the clear-text password using the same salt that was used to encrypt the original password
        byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);

        // Authentication succeeds if encrypted password that the user entered is equal to the stored hash
        return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);

    }

    public byte[] getEncryptedPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
            // PBKDF2 with SHA-1 as the hashing algorithm. Note that the NIST specifically names SHA-1 as an acceptable hashing algorithm for PBKDF2
            String algorithm = "PBKDF2WithHmacSHA1";
            int derivedKeyLength = 160;
            int iterations = 10000;

            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);

            SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);

            return f.generateSecret(spec).getEncoded();
    }

    public byte[] generateSalt() {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

            // Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
            byte[] salt = new byte[8];
            random.nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getStackTrace());
            return e.getMessage().getBytes(StandardCharsets.UTF_8);
        }
    }

}
