package api;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserAuthenticationTest {
    @Test
    public void authenticationTest(){
        DataManager.loadUsersFromFile();
        UserAuthentication.authentication(DataManager.usersList.get(0).getUsername(),DataManager.usersList.get(0).getPassword());
        assertTrue(UserAuthentication.authentication(DataManager.usersList.get(0).getUsername(),DataManager.usersList.get(0).getPassword()));
        assertFalse(UserAuthentication.authentication("nonExistentName","no password"));
    }

    @Test
    public void isUsernameUniqueTest(){
        DataManager.loadUsersFromFile();
        assertFalse(UserAuthentication.isUsernameUnique(DataManager.usersList.get(0).getUsername()));
        assertTrue(UserAuthentication.isUsernameUnique("newUserName"));
    }
}
