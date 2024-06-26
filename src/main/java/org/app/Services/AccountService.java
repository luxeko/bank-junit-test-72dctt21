package org.app.Services;

import org.app.Entities.Account;
import org.app.Entities.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountService {
    private final List<Account> accounts = new ArrayList<>();
    private final List<Transaction> transactions = new ArrayList<>();

    public void createAccount(Account account) {
        List<Account> customerAccounts = getAccountsByCustomerId(account.getCustomerId());

        if (customerAccounts.size() >= 2) {
            throw new IllegalArgumentException("Customer can only have 2 accounts: one VISA and one NORMAL");
        }

        for (Account existingAccount : customerAccounts) {
            if (existingAccount.getAccountType().equals(account.getAccountType())) {
                throw new IllegalArgumentException("Customer already has an account of type: " + account.getAccountType());
            }
        }

        if (account.getAccountType().equals("VISA")) {
            account.setLimitOfMoney(5000.0);
            account.setAmountOfMoney(30000.0);
        } else if (account.getAccountType().equals("NORMAL")) {
            account.setLimitOfMoney(1000.0);
            account.setAmountOfMoney(account.getAmountOfMoney());
        }

        accounts.add(account);
    }

    public List<Account> getAccountsByCustomerId(int customerId) {
        List<Account> customerAccounts = new ArrayList<>();
        for (Account account : accounts) {
            if (account.getCustomerId() == customerId) {
                customerAccounts.add(account);
            }
        }
        return customerAccounts;
    }

    public void updateAccount(Account updatedAccount) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(updatedAccount.getAccountNumber())) {
                accounts.set(i, updatedAccount);
                return;
            }
        }
    }

    public void deleteAccount(String accountNumber) {
        accounts.removeIf(account -> account.getAccountNumber().equals(accountNumber));
    }

    public Account getAccountByNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public void deposit(String accountNumber, double amount) {
        Account account = getAccountByNumber(accountNumber);
        if (account != null) {
            account.setAmountOfMoney(account.getAmountOfMoney() + amount);
            transactions.add(new Transaction(0, accountNumber, "Deposit", amount, new Date()));
        }
    }

    public void withdrawMoney(String accountNumber, double amount) {
        Account account = getAccountByNumber(accountNumber);

        if (account.getAccountType().equals("NORMAL") && account.getAmountOfMoney() < amount) {
            throw new IllegalArgumentException("Insufficient balance in NORMAL account");
        }

        account.setAmountOfMoney(account.getAmountOfMoney() - amount);
        transactions.add(new Transaction(0, accountNumber, "Withdrawal", amount, new Date()));
    }

    public void transferMoney(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = getAccountByNumber(fromAccountNumber);
        Account toAccount = getAccountByNumber(toAccountNumber);

        if (fromAccount.getAccountType().equals("NORMAL") && fromAccount.getAmountOfMoney() < amount) {
            throw new IllegalArgumentException("Insufficient balance in NORMAL account");
        }

        fromAccount.setAmountOfMoney(fromAccount.getAmountOfMoney() - amount);
        toAccount.setAmountOfMoney(toAccount.getAmountOfMoney() + amount);
    }

    public List<Transaction> getTransactions(String accountNumber) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getAccountNumber().equals(accountNumber)) {
                result.add(transaction);
            }
        }
        return result;
    }
}
