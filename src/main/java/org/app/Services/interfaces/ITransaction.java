package org.app.Services.interfaces;

public interface ITransaction {
    void createTransaction(String accountNo, String transactionType, Double amountOfMoney);
}
