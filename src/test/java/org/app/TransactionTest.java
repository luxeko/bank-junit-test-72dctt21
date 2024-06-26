package org.app;

import org.app.Entities.Transaction;
import org.app.Services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        transactionService = new TransactionService();
    }

    @Test
    public void testReadTransaction() {
        Transaction transaction = new Transaction(1, "ACC001", "Deposit", 500.0, new Date());
        transactionService.addTransaction(transaction);
        Transaction retrievedTransaction = transactionService.getTransactionById(1);
        assertNotNull(retrievedTransaction);
        assertEquals(500.0, retrievedTransaction.getAmountOfMoney());
    }

    @Test
    public void testAddTransaction() {
        Transaction transaction = new Transaction(1, "ACC001", "Deposit", 500.0, new Date());
        transactionService.addTransaction(transaction);

        Transaction retrievedTransaction = transactionService.getTransactionById(1);
        assertNotNull(retrievedTransaction);
        assertEquals(500.0, retrievedTransaction.getAmountOfMoney());
    }

    @Test
    public void testUpdateTransaction() {
        Transaction transaction = new Transaction(1, "ACC001", "Deposit", 500.0, new Date());
        transactionService.addTransaction(transaction);

        transaction.setAmountOfMoney(600.0);
        transactionService.updateTransaction(transaction);

        Transaction updatedTransaction = transactionService.getTransactionById(1);
        assertNotNull(updatedTransaction);
        assertEquals(600.0, updatedTransaction.getAmountOfMoney());
    }

    @Test
    public void testDeleteTransaction() {
        Transaction transaction = new Transaction(1, "ACC001", "Deposit", 500.0, new Date());
        transactionService.addTransaction(transaction);

        transactionService.deleteTransaction(1);

        Transaction deletedTransaction = transactionService.getTransactionById(1);
        assertNull(deletedTransaction);
    }

    @Test
    public void testDeleteTransactionsByAccountNumber() {
        Transaction transaction1 = new Transaction(1, "ACC001", "Deposit", 500.0, new Date());
        Transaction transaction2 = new Transaction(2, "ACC001", "Withdraw", 200.0, new Date());
        transactionService.addTransaction(transaction1);
        transactionService.addTransaction(transaction2);

        transactionService.deleteTransactionsByAccountNumber("ACC001");

        assertNull(transactionService.getTransactionById(1));
        assertNull(transactionService.getTransactionById(2));
    }

    @Test
    public void testDeleteNonExistentTransaction() {
        assertDoesNotThrow(() -> transactionService.deleteTransaction(999));
    }

    @Test
    public void testUpdateNonExistentTransaction() {
        Transaction transaction = new Transaction(999, "ACC001", "Deposit", 500.0, new Date());
        assertDoesNotThrow(() -> transactionService.updateTransaction(transaction));
    }
}
