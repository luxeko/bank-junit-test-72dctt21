package org.app;

import org.app.Entities.Account;
import org.app.Entities.Customer;
import org.app.Entities.Transaction;
import org.app.Entities.User;
import org.app.Services.AccountService;
import org.app.Services.CustomerService;
import org.app.Services.TransactionService;
import org.app.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTest {
    private UserService userService;
    private CustomerService customerService;
    private AccountService accountService;
    private TransactionService transactionService;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @BeforeEach
    public void setUp() throws ParseException {
        transactionService = new TransactionService();
        accountService = new AccountService();
        customerService = new CustomerService(accountService, userService);
        userService = new UserService(customerService, accountService, transactionService);

        User user = new User(1, "admin", "password1", "CUSTOMER");
        userService.register(user);
        Customer customer = new Customer(1, 1, "CUST001", "John Doe", "123456789", "0123456789", "john@example.com", formatter.parse("28/02/1999"), "Male", "123 Main St", "NORMAL");
        customerService.addCustomer(customer);
    }

    @Test
    public void testRegisterAndLogin() {
        User user = new User(1, "admin", "password123", "ADMIN");
        userService.register(user);

        User loggedInUser = userService.login("admin", "password123");
        assertNotNull(loggedInUser);
        assertEquals("admin", loggedInUser.getUsername());
    }

    @Test
    public void testLoginWithWrongPassword() {
        User user = new User(1, "admin", "password123", "ADMIN");
        userService.register(user);

        User loggedInUser = userService.login("admin", "wrongpassword");
        assertNull(loggedInUser);
    }

    @Test
    public void testLoginWithNonExistentUser() {
        User loggedInUser = userService.login("nonexistent", "password123");
        assertNull(loggedInUser);
    }

    @Test
    public void testUserFields() {
        User user = new User(1, "admin", "password123", "ADMIN");

        assertEquals(1, user.getId());
        assertEquals("admin", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("ADMIN", user.getRole());
    }

    @Test
    public void testCRUDUser() {
        User user = new User(1, "admin", "password123", "ADMIN");
        userService.register(user);

        // Read
        User retrievedUser = userService.getUserById(1);
        assertNotNull(retrievedUser);
        assertEquals("admin", retrievedUser.getUsername());

        // Update
        user.setPassword("newpassword");
        userService.updateUser(user);
        User updatedUser = userService.getUserById(1);
        assertEquals("newpassword", updatedUser.getPassword());
    }
    @Test
    public void testDeleteUserWithRelatedEntities() {
        Account account = new Account(1, 1, "ACC001", "VISA", "ACTIVE", 30000.0, 5000.0);
        accountService.createAccount(account);
        Transaction transaction = new Transaction(1, "ACC001", "Deposit", 500.0, new Date());
        transactionService.addTransaction(transaction);

        userService.deleteUser(1);

        assertNull(userService.getUserById(1));
        assertNull(customerService.getCustomerById(1));
        assertNull(accountService.getAccountByNumber("ACC001"));
        assertNull(transactionService.getTransactionById(1));
    }

    @Test
    public void testDeleteUserWithoutCustomer() {
        User user = new User(2, "user2", "password2", "ADMIN");
        userService.register(user);

        userService.deleteUser(2);

        assertNull(userService.getUserById(2));
    }
    @Test
    public void testUserValidation() {
        User user = new User(1, "", "password123", "ADMIN");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validateUser(user);
        });
        assertEquals("Username cannot be empty", exception.getMessage());
    }

    private void validateUser(User user) {
        if (user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        // Add more validation rules here
    }

    // thêm các testcase login với để password/username trống
}
