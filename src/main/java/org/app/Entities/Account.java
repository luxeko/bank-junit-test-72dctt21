package org.app.Entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Account {
    private int id;
    private String accountCode;
    private String accountNumber;
    private String accountType;
    private String status;
    private Double amountOfMoney;
    private Double limitOfMoney;
    private Date createdAt;
    private Date updatedAt;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private final Scanner sc = new Scanner(System.in);

    public Account() {
    }

    public Account(int id, String accountCode, String accountNumber, String accountType, String status, Double amountOfMoney, Date createdAt, Date updatedAt, Double limitOfMoney) {
        this.id = id;
        this.accountCode = accountCode;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.status = status;
        this.amountOfMoney = amountOfMoney;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.limitOfMoney = limitOfMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Double getLimitOfMoney() {
        return limitOfMoney;
    }

    public void setLimitOfMoney(Double limitOfMoney) {
        this.limitOfMoney = limitOfMoney;
    }

    public void input() {

    }
}
