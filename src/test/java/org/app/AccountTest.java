package org.app;

import org.app.Entities.Account;
import org.app.Entities.Customer;
import org.app.Services.impl.AccountImpl;
import org.app.Services.impl.CustomerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AccountTest {

    private CustomerImpl customerImpl;
    private AccountImpl accountImpl;
    private Scanner sc;
    private Account account;

    @BeforeEach
    public void setUp() {
        customerImpl = mock(CustomerImpl.class);
        accountImpl = mock(AccountImpl.class);
        sc = mock(Scanner.class);
        account = new Account(sc, customerImpl, accountImpl);
    }

    
}
