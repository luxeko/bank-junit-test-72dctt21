package org.app;

import org.app.Entities.Account;
import org.app.Entities.Customer;
import org.app.Entities.User;
import org.app.Services.AccountService;
import org.app.Services.CustomerService;
import org.app.Services.TransactionService;
import org.app.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
    private CustomerService customerService;
    private AccountService accountService;
    private UserService userService;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @BeforeEach
    public void setUp() throws ParseException {
        accountService = new AccountService();
        TransactionService transactionService = new TransactionService();
        userService = new UserService(customerService, accountService, transactionService);
        customerService = new CustomerService(accountService, userService);

        User user = new User(1, "user1", "password1", "CUSTOMER");
        userService.register(user);
        Customer customer = new Customer(1, 1, "CUST001", "John Doe", "123456789", "0123456789", "john@example.com", formatter.parse("28/02/1999"), "Male", "123 Main St", "NORMAL");
        customerService.addCustomer(customer);
    }

    @Test
    public void testReadCustomer() throws ParseException {
        Customer customer = new Customer(1, 1, "CUST001", "John Doe", "123456789", "0123456789", "john@example.com", formatter.parse("28/02/1999"), "Male", "123 Main St", "NORMAL");
        customerService.addCustomer(customer);

        // Read
        Customer retrievedCustomer = customerService.getCustomerById(1);
        assertNotNull(retrievedCustomer);
        assertEquals("John Doe", retrievedCustomer.getCustomerName());
    }

    @Test
    public void testUpdateCustomer() throws ParseException {
        Customer customer = new Customer(1, 1, "CUST001", "John Doe", "123456789", "0123456789", "john@example.com", formatter.parse("28/02/1999"), "Male", "123 Main St", "NORMAL");
        customerService.addCustomer(customer);

        customer.setCustomerName("Jane Doe");
        customerService.updateCustomer(customer);
        Customer updatedCustomer = customerService.getCustomerById(1);
        assertEquals("Jane Doe", updatedCustomer.getCustomerName());
    }

    @Test
    public void testCustomerValidation() throws ParseException {
        Customer customer = new Customer(1, 1, "CUST001", "", "123456789", "0123456789", "john@example.com", formatter.parse("28/02/1999"), "Male", "123 Main St", "NORMAL");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validateCustomer(customer);
        });
        assertEquals("Customer name cannot be empty", exception.getMessage());
    }

    @Test
    public void testDeleteCustomerWithoutAccounts() {
        customerService.deleteCustomer(1);
        Customer deletedCustomer = customerService.getCustomerById(1);
        assertNull(deletedCustomer);
    }

    @Test
    public void testDeleteCustomerWithAccounts() {
        Account account = new Account(1, 1, "ACC001", "VISA", "ACTIVE", 30000.0, 5000.0);
        accountService.createAccount(account);

        assertThrows(IllegalArgumentException.class, () -> {
            customerService.deleteCustomer(1);
        });

        Customer existingCustomer = customerService.getCustomerById(1);
        assertNotNull(existingCustomer);

        User existingUser = userService.getUserById(1);
        assertNotNull(existingUser);
    }

    private void validateCustomer(Customer customer) {
        if (customer.getCustomerName().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }
        // Add more validation rules here
    }


}
