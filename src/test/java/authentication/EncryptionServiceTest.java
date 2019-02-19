package authentication;

import com.chatapi.authentication.EncryptionService;
import com.chatapi.authentication.models.User;
import com.chatapi.base.DatabaseService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EncryptionServiceTest {
    /*@BeforeAll
    public static void setUp() throws NoSuchAlgorithmException, InvalidKeySpecException {
        EncryptionService encryptService = new EncryptionService();
        DatabaseService dbService = new DatabaseService();

        byte[] testSalt = encryptService.generateSalt();
        User testUser = new User("test", encryptService.getEncryptedPassword("test", testSalt), testSalt);
        dbService.addUser(testUser);
    }

    @AfterAll
    public static void tearDown() {
        DatabaseService dbService = new DatabaseService();
        dbService.deleteUserByUsername("test");
    }

    @Test
    @DisplayName("Test Authentication With Valid Credentials")
    public static void TestAuthenticationWithValidCredentials() throws NoSuchAlgorithmException, InvalidKeySpecException {
        DatabaseService dbService = new DatabaseService();
        EncryptionService encryptService = new EncryptionService();

        User testUser = dbService.getUser("test");
        assertTrue(encryptService.authenticate("test", testUser.getPassword(), testUser.getSalt()));
    }

    @Test
    @DisplayName("Test Authentication With Invalid Credentials")
    public static void TestAuthenticationWithInvalidCredentials() throws NoSuchAlgorithmException, InvalidKeySpecException {
        DatabaseService dbService = new DatabaseService();
        EncryptionService encryptService = new EncryptionService();

        User testUser = dbService.getUser("test");
        assertFalse(encryptService.authenticate("invalidpassword", testUser.getPassword(), testUser.getSalt()));
    }*/
}
