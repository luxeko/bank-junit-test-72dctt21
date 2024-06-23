package org.app.Controllers;

import org.app.Entities.Customer;
import org.app.Services.impl.CustomerImpl;
import org.app.Services.interfaces.ICustomer;

import java.util.List;

public class CustomerController {
    private final CustomerImpl customer = new CustomerImpl();

    public CustomerController() {
    }

    public void getListCustomer() {
        customer.showListCustomer();
    }

    public Customer getInfo(int userId) {
        return this.customer.getInfo(userId);
    }

    public boolean addCustomer(Customer customer) {
        // Business logic to add customer
       return this.customer.createCustomer(customer);
    }

    public boolean updateCustomer(Customer customer) {
        return this.customer.updateCustomer(customer);
    }

    public int getUserIdByCustomerCode(String customerCode) {
        return this.customer.getUserIdByCustomerCode(customerCode);
    }

    public boolean deleteCustomer() {
        return this.customer.deleteCustomer();
    }
}
