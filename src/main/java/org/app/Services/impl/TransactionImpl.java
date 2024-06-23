package org.app.Services.impl;

import java.sql.Connection;
import org.app.Configs.ConnectionDB;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.app.Entities.Transaction;
import org.app.Services.interfaces.ITransaction;

public class TransactionImpl implements ITransaction {

    List<Transaction> inTransactions = new ArrayList<Transaction>();
    Connection con = ConnectionDB.getInstance().getConnection();
    private static Scanner sc = new Scanner(System.in);
    private SimpleDateFormat sdf_sql = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    java.util.Date date_Current = new java.util.Date();

    @Override
    public void createTransaction(String accountNumber, String operationType, Double amountOfMoney) {
        if (con == null) {
            System.out.println("Connection failed!");
        }
        String sql = "insert into Transactions (accountNumber, operationType, amountOfMoney, transactionDate, transactionPlace) values (?, ?, ?, ?, ?)";
        try {
            Date date_sql = Date.valueOf(sdf_sql.format(date_Current));
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, accountNumber);
            ps.setString(2, operationType);
            ps.setDouble(3, amountOfMoney);
            ps.setDate(4, date_sql);
            ps.setString(5, "HaNoi");
            int result = ps.executeUpdate();
            ps.close();
            //B3:XU LY KET QUA
            if (result != 0) {
                System.out.println("Lưu giao dịch thành công");
            } else {
                System.out.println("Lưu giao dịch thất bại!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
