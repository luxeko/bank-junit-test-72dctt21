package org.app.Entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private int id;
    private String accountNumber;
    private String actionType;
    private Double amountOfMoney;
    private Date transactionDate;
    private String transactionPlace;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public Transaction() {
    }

    public Transaction(int id, String accountNumber, String actionType, Double amountOfMoney, Date transactionDate, String transactionPlace) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.actionType = actionType;
        this.amountOfMoney = amountOfMoney;
        this.transactionDate = transactionDate;
        this.transactionPlace = transactionPlace;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Double getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(Double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionPlace() {
        return transactionPlace;
    }

    public void setTransactionPlace(String transactionPlace) {
        this.transactionPlace = transactionPlace;
    }
}
