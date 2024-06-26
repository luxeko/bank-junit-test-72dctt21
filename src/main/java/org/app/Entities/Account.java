package org.app.Entities;

public class Account {

    private int id;
    private int customerId;
    private String accountNumber;
    private String accountType;
    private String status;
    private Double amountOfMoney;
    private Double limitOfMoney;

    public Account() {
    }

    public Account(int id, int customerId, String accountNumber, String accountType, String status, Double amountOfMoney, Double limitOfMoney) {
        this.id = id;
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.status = status;
        this.amountOfMoney = amountOfMoney;
        this.limitOfMoney = limitOfMoney;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(Double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public Double getLimitOfMoney() {
        return limitOfMoney;
    }

    public void setLimitOfMoney(Double limitOfMoney) {
        this.limitOfMoney = limitOfMoney;
    }

    public void setCustomerId(Double limitOfMoney) {
        this.limitOfMoney = limitOfMoney;
    }
}
