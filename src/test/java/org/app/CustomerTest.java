package org.app;


import org.app.Controllers.CustomerController;
import org.app.Entities.Customer;
import org.app.Services.interfaces.ICustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.mockito.Mockito.*;

public class CustomerTest {
    private ICustomer iCustomer;
    private CustomerController customerController;

    @BeforeEach
    public void setUp() {
        iCustomer = Mockito.mock(ICustomer.class);
        customerController = new CustomerController(iCustomer);
    }

    @Test
    public void testAddCustomer() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setCustomerCode("C001");
        customer.setCustomerName("John Doe");
        customer.setCitizenIdentificationNumber("123456789");
        customer.setPhoneNumber("0123456789");
        customer.setEmail("john.doe@example.com");
        customer.setDob(new Date());
        customer.setGender("Male");
        customer.setAddress("123 Main St");
        customer.setCustomerType("Normal");

        customerController.addCustomer(customer);

        verify(iCustomer, times(1)).createCustomer(customer);
    }
}
