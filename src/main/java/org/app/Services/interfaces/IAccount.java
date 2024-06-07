package org.app.Services.interfaces;

import java.util.List;

public interface IAccount {
    void createAccount();

    void updateAccount();

    void withdrawAccount();

    void payInAccount();

    List<?> showListAccount();
    List<?> showAccountInfoByAccountId();
    List<?> showAccountInfoByCustomerId();
}
