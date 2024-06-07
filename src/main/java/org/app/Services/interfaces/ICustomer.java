package org.app.Services.interfaces;

import java.util.List;

public interface ICustomer {
    void createCustomer();

    void updateCustomer();

    void deleteCustomer();

    List<?> showListCustomer();
}
