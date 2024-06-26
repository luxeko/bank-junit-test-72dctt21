package org.app.Services;

import org.app.Entities.Account;
import org.app.Entities.Customer;
import org.app.Entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final List<User> users = new ArrayList<>();
    private final CustomerService customerService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    public UserService(CustomerService customerService, AccountService accountService, TransactionService transactionService) {
        this.customerService = customerService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }
    public void register(User user) {
        users.add(user);
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User getUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public void updateUser(User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == updatedUser.getId()) {
                users.set(i, updatedUser);
                return;
            }
        }
    }

    public void deleteUser(int userId) {
        User user = getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Customer customer = customerService.getCustomerByUserId(userId);
        if (customer != null) {
            List<Account> accounts = accountService.getAccountsByCustomerId(customer.getId());
            for (Account account : accounts) {
                transactionService.deleteTransactionsByAccountNumber(account.getAccountNumber());
                accountService.deleteAccount(account.getAccountNumber());
            }
            customerService.deleteCustomer(customer.getId());
        }

        users.removeIf(u -> u.getId() == userId);
    }
}
