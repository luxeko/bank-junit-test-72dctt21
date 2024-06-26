package org.app.Services;

import org.app.Entities.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private final List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public Transaction getTransactionById(int id) {
        for (Transaction transaction : transactions) {
            if (transaction.getId() == id) {
                return transaction;
            }
        }
        return null;
    }
    public void updateTransaction(Transaction updatedTransaction) {
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId() == updatedTransaction.getId()) {
                transactions.set(i, updatedTransaction);
                return;
            }
        }
    }
    public void deleteTransaction(int id) {
        transactions.removeIf(transaction -> transaction.getId() == id);
    }

    public void deleteTransactionsByAccountNumber(String accountNumber) {
        transactions.removeIf(transaction -> transaction.getAccountNumber().equals(accountNumber));
    }


}
