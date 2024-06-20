package org.app.Services.impl;

import org.app.Configs.ConnectionDB;
import org.app.Entities.Account;
import org.app.Services.interfaces.IAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Scanner;

public class AccountImpl implements IAccount {
    Connection connection = ConnectionDB.getInstance().getConnection();

    private static Scanner sc = new Scanner(System.in);

    @Override
    public void createAccount(Account account) throws Exception {
        // b1: Kết nối đên database
        if (connection == null) {
            System.out.println("Connection failed!");
        }
        // b2: Thực hiện truy vấn
        String query = "insert into Accounts (accountCode, accountNumber, accountType, status, amountOfMoney, limitOfMoney, createdAt) values (?, ?, ?, ?, ?, ?, convert(date, ?, 103))";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, account.getAccountCode());
            preparedStatement.setString(2, account.getAccountNumber());
            preparedStatement.setString(3, account.getAccountType());
            preparedStatement.setString(4, account.getStatus());
            preparedStatement.setDouble(5, account.getAmountOfMoney());
            preparedStatement.setDouble(6, account.getLimitOfMoney());

            int result = preparedStatement.executeUpdate();
            // b3: Xử lý kết quả
            if (result == 0)
                System.out.println("Thêm mới tài khoản thất bại");
            else
                System.out.println("Thêm mới tài khoản thành công");

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccount() {

    }

    @Override
    public void withdrawAccount() {

    }

    @Override
    public void payInAccount() {

    }

    @Override
    public List<Account> showListAccount() {
        return null;
    }

    @Override
    public List<Account> showAccountInfoByAccountId() {
        return null;
    }

    @Override
    public List<Account> showAccountInfoByCustomerId() {
        return null;
    }

    private static void input() {

    }
}
