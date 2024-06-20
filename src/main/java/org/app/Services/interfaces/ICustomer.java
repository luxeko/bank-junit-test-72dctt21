package org.app.Services.interfaces;

import org.app.Entities.Customer;

import java.util.List;

public interface ICustomer {
    void createCustomer(Customer customer);
    Customer getInfo(int userId);

    void updateCustomer();

    void deleteCustomer();

    List<?> showListCustomer();
}
