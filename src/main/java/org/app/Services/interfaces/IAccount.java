package org.app.Services.interfaces;

import org.app.Entities.Account;

import java.util.List;

public interface IAccount {
    boolean createAccount(Account account);

    void updateAccount();

    void withdrawAccount();

    void payInAccount();

    List<Account> showListAccount();
    List<Account> showAccountInfoByAccountId();
    List<Account> showAccountInfoByCustomerId();
}
