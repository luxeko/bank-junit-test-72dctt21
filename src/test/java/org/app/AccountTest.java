package org.app;

import org.app.Entities.Account;
import org.app.Services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        accountService = new AccountService();
    }

    // Test Case 1: Create VISA account with less than 2 existing accounts and no existing VISA account
    @Test
    public void testCreateVisaAccountSuccess() {
        Account visaAccount = new Account(1, 1, "ACC001", "VISA", "ACTIVE", 0.0, 0.0);
        accountService.createAccount(visaAccount);

        Account createdAccount = accountService.getAccountByNumber("ACC001");
        assertNotNull(createdAccount);
        assertEquals(30000.0, createdAccount.getAmountOfMoney());
        assertEquals(5000.0, createdAccount.getLimitOfMoney());
    }

    // Test Case 2: Create NORMAL account with less than 2 existing accounts and no existing NORMAL account
    @Test
    public void testCreateNormalAccountSuccess() {
        Account normalAccount = new Account(2, 1, "ACC002", "NORMAL", "ACTIVE", 5000.0, 0.0);
        accountService.createAccount(normalAccount);

        Account createdAccount = accountService.getAccountByNumber("ACC002");
        assertNotNull(createdAccount);
        assertEquals(5000.0, createdAccount.getAmountOfMoney());
        assertEquals(1000.0, createdAccount.getLimitOfMoney());
    }

    // Test Case 3: Create account with 2 existing accounts
    @Test
    public void testCreateAccountWithTwoExistingAccounts() {
        Account visaAccount = new Account(1, 1, "ACC001", "VISA", "ACTIVE", 0.0, 0.0);
        Account normalAccount = new Account(2, 1, "ACC002", "NORMAL", "ACTIVE", 5000.0, 0.0);
        accountService.createAccount(visaAccount);
        accountService.createAccount(normalAccount);

        assertThrows(IllegalArgumentException.class, () -> {
            Account extraAccount = new Account(3, 1, "ACC003", "VISA", "ACTIVE", 0.0, 0.0);
            accountService.createAccount(extraAccount);
        });
    }

    // Test Case 4: Create VISA account when a VISA account already exists
    @Test
    public void testCreateVisaAccountWhenVisaExists() {
        Account visaAccount = new Account(1, 1, "ACC001", "VISA", "ACTIVE", 0.0, 0.0);
        accountService.createAccount(visaAccount);

        assertThrows(IllegalArgumentException.class, () -> {
            Account anotherVisaAccount = new Account(2, 1, "ACC002", "VISA", "ACTIVE", 0.0, 0.0);
            accountService.createAccount(anotherVisaAccount);
        });
    }

    // Test Case 5: Create NORMAL account when a NORMAL account already exists
    @Test
    public void testCreateNormalAccountWhenNormalExists() {
        Account normalAccount = new Account(2, 1, "ACC002", "NORMAL", "ACTIVE", 5000.0, 0.0);
        accountService.createAccount(normalAccount);

        assertThrows(IllegalArgumentException.class, () -> {
            Account anotherNormalAccount = new Account(3, 1, "ACC003", "NORMAL", "ACTIVE", 5000.0, 0.0);
            accountService.createAccount(anotherNormalAccount);
        });
    }

    // Test Case 6: Withdraw money from NORMAL account with sufficient balance
    @Test
    public void testWithdrawMoneyFromNormalAccountSuccess() {
        Account normalAccount = new Account(1, 1, "ACC001", "NORMAL", "ACTIVE", 2000.0, 1000.0);
        accountService.createAccount(normalAccount);

        accountService.withdrawMoney("ACC001", 500.0);
        assertEquals(1500.0, accountService.getAccountByNumber("ACC001").getAmountOfMoney());
    }

    // Test Case 7: Withdraw money from NORMAL account with insufficient balance
    @Test
    public void testWithdrawMoneyFromNormalAccountInsufficientBalance() {
        Account normalAccount = new Account(2, 1, "ACC002", "NORMAL", "ACTIVE", 2000.0, 1000.0);
        accountService.createAccount(normalAccount);

        assertThrows(IllegalArgumentException.class, () -> {
            accountService.withdrawMoney("ACC002", 2500.0);
        });
    }

    // Test Case 8: Withdraw money from VISA account (no balance check)
    @Test
    public void testWithdrawMoneyFromVisaAccount() {
        Account visaAccount = new Account(3, 1, "ACC003", "VISA", "ACTIVE", 3000.0, 5000.0);
        accountService.createAccount(visaAccount);

        accountService.withdrawMoney("ACC003", 1000.0);
        assertEquals(29000.0, accountService.getAccountByNumber("ACC003").getAmountOfMoney());
    }
}
