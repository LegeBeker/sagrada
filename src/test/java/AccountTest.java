package test.java;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import main.java.controller.AccountController;

public class AccountTest {

    private AccountController accountController;

    @Before
    public void setUp() {
        this.accountController = new AccountController();
    }

    @Test
    public void testRegister() {
        assertTrue(accountController.createAccount("JohnDoe", "Doe123"));
        assertNotNull(accountController.getAccount());
    }

    @Test
    public void testLogin() {
        assertTrue(accountController.loginAccount("JohnDoe", "Doe123"));
        assertNotNull(accountController.getAccount());
    }

    @Test
    public void testLogout() {
        assertTrue(accountController.logoutAccount());
        assertNull(accountController.getAccount());
    }
}
