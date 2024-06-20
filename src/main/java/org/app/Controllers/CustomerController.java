package org.app.Controllers;

import org.app.Entities.Customer;
import org.app.Services.interfaces.ICustomer;

public class CustomerController {
    private ICustomer iCustomer;

    public CustomerController() {
    }

    public CustomerController(ICustomer iCustomer) {
        this.iCustomer = iCustomer;
    }

    public Customer getInfo(int userId) {
        return this.iCustomer.getInfo(userId);
    }

    public void addCustomer(Customer customer) {
        // Business logic to add customer
        iCustomer.createCustomer(customer);
    }

    public void updateCustomer(Customer customer) {

    }
}
