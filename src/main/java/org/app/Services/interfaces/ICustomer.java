package org.app.Services.interfaces;

import org.app.Entities.Customer;

import java.util.List;

public interface ICustomer {
    boolean createCustomer(Customer customer);

    Customer getInfo(int userId);

    boolean updateCustomer(Customer customer);
    int getUserIdByCustomerCode(String customerCode);

    void deleteCustomer();

    List<?> showListCustomer();

    boolean checkExistType(String value, String dataType, int id);
}
