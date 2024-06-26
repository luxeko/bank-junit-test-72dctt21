package org.app;

import org.app.Entities.Account;
import org.app.Entities.Transaction;
import org.app.Services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class AccountTest {
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        accountService = new AccountService();
    }

    @Test
    public void testCreateAccountLimits() {
        Account visaAccount = new Account(1, 1, "ACC001", "VISA", "ACTIVE", 30000.0, 5000.0);
        Account normalAccount = new Account(2, 1, "ACC002", "NORMAL", "ACTIVE", 0.0, 0.0);
        accountService.createAccount(visaAccount);
        accountService.createAccount(normalAccount);

        assertThrows(IllegalArgumentException.class, () -> {
            Account extraAccount = new Account(3, 1, "ACC003", "VISA", "ACTIVE", 30000.0, 5000.0);
            accountService.createAccount(extraAccount);
        });
    }

    @Test
    public void testAccountTypeLimit() {
        Account visaAccount = new Account(1, 1, "ACC001", "VISA", "ACTIVE", 30000.0, 5000.0);
        accountService.createAccount(visaAccount);

        assertThrows(IllegalArgumentException.class, () -> {
            Account anotherVisaAccount = new Account(2, 1, "ACC002", "VISA", "ACTIVE", 30000.0, 5000.0);
            accountService.createAccount(anotherVisaAccount);
        });
    }

    @Test
    public void testCreateVisaAccount() {
        Account visaAccount = new Account(1, 1, "ACC001", "VISA", "ACTIVE", 30000.0, 5000.0);
        accountService.createAccount(visaAccount);

        Account createdAccount = accountService.getAccountByNumber("ACC001");
        assertEquals(30000, createdAccount.getAmountOfMoney());
        assertEquals(5000, createdAccount.getLimitOfMoney());
    }

    @Test
    public void testCreateNormalAccount() {
        Account normalAccount = new Account(2, 1, "ACC002", "NORMAL", "ACTIVE", 0.0, 1000.0);
        accountService.createAccount(normalAccount);

        Account createdAccount = accountService.getAccountByNumber("ACC002");
        assertEquals(0, createdAccount.getAmountOfMoney());
        assertEquals(1000, createdAccount.getLimitOfMoney());
    }

    @Test
    public void testTransferMoney() {
        Account fromAccount = new Account(1, 1, "ACC001", "VISA", "ACTIVE", 30000.0, 5000.0);
        Account toAccount = new Account(2, 2, "ACC002", "NORMAL", "ACTIVE", 0.0, 1000.0);
        accountService.createAccount(fromAccount);
        accountService.createAccount(toAccount);

        accountService.transferMoney("ACC001", "ACC002", 5000);

        assertEquals(25000, accountService.getAccountByNumber("ACC001").getAmountOfMoney());
        assertEquals(5000, accountService.getAccountByNumber("ACC002").getAmountOfMoney());
    }

    @Test
    public void testWithdrawMoneyFromNormalAccount() {
        Account normalAccount = new Account(3, 1, "ACC003", "NORMAL", "ACTIVE", 20000.0, 1000.0);
        accountService.createAccount(normalAccount);

        accountService.withdrawMoney("ACC003", 500.0);
        assertEquals(19500.0, accountService.getAccountByNumber("ACC003").getAmountOfMoney());

        assertThrows(IllegalArgumentException.class, () -> {
            accountService.withdrawMoney("ACC003", 20000.0);
        });
    }

    @Test
    @DisplayName("Kiểm thử gửi tiền vào tài khoản")
    public void testDeposit() {
        Account account = new Account(1, 1, "123456", "VISA", "ACTIVE", 30000.0, 10000.0);
        accountService.createAccount(account);
        accountService.deposit("123456", 1000.0);
        assertEquals(31000.0, accountService.getAccountByNumber("123456").getAmountOfMoney());
    }

    @Test
    @DisplayName("Kiểm thử lấy danh sách giao dịch của tài khoản")
    public void testGetTransactions() {
        Account account = new Account(1, 1, "123456", "VISA", "ACTIVE", 0.0, 10000.0);
        accountService.createAccount(account);
        accountService.deposit("123456", 1000.0);
        accountService.withdrawMoney("123456", 500.0);
        List<Transaction> transactions = accountService.getTransactions("123456");
        assertEquals(2, transactions.size());
    }

    @Test
    public void testCRUDAccount() {
        Account account = new Account(1, 1, "ACC001", "VISA", "ACTIVE", 30000.0, 5000.0);
        accountService.createAccount(account);

        // Read
        Account retrievedAccount = accountService.getAccountByNumber("ACC001");
        assertNotNull(retrievedAccount);
        assertEquals(30000.0, retrievedAccount.getAmountOfMoney());

        // Update
        account.setAmountOfMoney(60000.0);
        accountService.updateAccount(account);
        Account updatedAccount = accountService.getAccountByNumber("ACC001");
        assertEquals(60000.0, updatedAccount.getAmountOfMoney());

        // Delete
        accountService.deleteAccount("ACC001");
        Account deletedAccount = accountService.getAccountByNumber("ACC001");
        assertNull(deletedAccount);
    }

    @Test
    public void testAccountValidation() {
        Account account = new Account(1, 1, "ACC001", "VISA", "ACTIVE", -1000.0, 5000.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validateAccount(account);
        });
        assertEquals("Amount of money cannot be negative", exception.getMessage());
    }

    private void validateAccount(Account account) {
        if (account.getAmountOfMoney() < 0) {
            throw new IllegalArgumentException("Amount of money cannot be negative");
        }
        // Add more validation rules here
    }
}
