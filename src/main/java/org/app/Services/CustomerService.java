package org.app.Services;

import org.app.Entities.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private final List<Customer> customers = new ArrayList<>();
    private final AccountService accountService;
    private final UserService userService;

    public CustomerService(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Customer getCustomerById(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }

    public Customer getCustomerByUserId(int userId) {
        for (Customer customer : customers) {
            if (customer.getUserId() == userId) {
                return customer;
            }
        }
        return null;
    }

    public void updateCustomer(Customer updatedCustomer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId() == updatedCustomer.getId()) {
                customers.set(i, updatedCustomer);
                return;
            }
        }
    }

    public void deleteCustomer(int id) {
        Customer customer = getCustomerById(id);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }

        if (!accountService.getAccountsByCustomerId(id).isEmpty()) {
            throw new IllegalArgumentException("Cannot delete customer with existing accounts");
        }
        customers.removeIf(c -> c.getId() == id);
    }
}
