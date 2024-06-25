package org.app.Services.impl;

import java.sql.CallableStatement;
import org.app.Configs.ConnectionDB;
import org.app.Entities.Account;
import org.app.Services.interfaces.IAccount;
import org.app.Controllers.TransactionController;
import org.app.Controllers.CustomerController;
import org.app.Controllers.AccountController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AccountImpl implements IAccount {

    Connection connection = ConnectionDB.getInstance().getConnection();
    private SimpleDateFormat sdf_sql = new SimpleDateFormat("yyyy-MM-dd");
    private static Scanner sc = new Scanner(System.in);

    @Override
    public void createAccount(Account account) {
        boolean input = account.input();

        if (connection == null) {
            System.out.println("Connection failed!");
        }

//        if (input) {
            String query = "insert into Accounts (customerId, accountNumber, accountType, status, amountOfMoney, limitOfMoney, createdAt, updatedAt) values (?, ?, ?, ?, ?, ?,convert(date, ?, 103), convert(date, ?, 103))";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, account.getCustomerId());
                preparedStatement.setString(2, account.getAccountNumber());
                preparedStatement.setString(3, account.getAccountType());
                preparedStatement.setString(4, account.getStatus());

                Double amountOfMoney = account.getAmountOfMoney();
                if (amountOfMoney == null) {
                    amountOfMoney = 0.0;
                }
                preparedStatement.setDouble(5, amountOfMoney);

                Double limitOfMoney = account.getLimitOfMoney();
                if (limitOfMoney == null) {
                    limitOfMoney = 1000000d;
                }
                preparedStatement.setDouble(6, limitOfMoney);

                String createdAt2 = account.getCreatedAt2();
                if (createdAt2 == null) {
                    createdAt2 = "";
                }
                preparedStatement.setString(7, createdAt2);

                String updatedAt2 = account.getUpdatedAt2();
                if (updatedAt2 == null) {
                    updatedAt2 = "";
                }
                preparedStatement.setString(8, updatedAt2);

                int result = preparedStatement.executeUpdate();
                preparedStatement.close();

                if(result != 0) {
                    System.out.println("Tạo thành công");
                } else {
                    System.out.println("Tạo thất bại");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//        }
    }

    @Override
    public void updateAccount() {

    }

    @Override
    public void withdrawAccount() {
        TransactionImpl transaction = new TransactionImpl();
        Double withdrawMoney;
        String accountNumber;
        Date updatedAt;
        String status = "Active";
        String choose = "";
        System.out.println("Nhập số tài khoản: ");
        accountNumber = sc.nextLine();
        java.time.LocalDate currentDate = java.time.LocalDate.now();
        updatedAt = java.sql.Date.valueOf(currentDate);
        System.out.println(updatedAt);
        // Lấy ra số tiền của tài khoản
        Double amount = queryAmountByAccountId(accountNumber);
        Double limitOfMoney = getLimitOfMoneyByAcc(accountNumber);

        // Check TK đã tồn tại chưa
        if (validateAccount(accountNumber) == true) {
            // Check loại TK là thường hay visa
            // 0 - Thường
            // 1 - Visa
            if (checkAccountType(accountNumber) == 0) {
                if (checkActive(accountNumber) == false) {
                    System.out.println("Tài khoản của bạn đang Disable! Bạn có muốn Active? Chọn 'y' để đồng ý");
                    while (true) {
                        choose = sc.nextLine();
                        if (choose.equals("y")) {
                            System.out.println("Active thành công");
                            updateStatus(accountNumber, "Prepay (VISA)", status, updatedAt);
                            break;
                        } else {
                            break;
                        }
                    }
                } else {
                    System.out.println("Số tiền có trong tài khoản: " + (Double) amount);
                    System.out.println("Hạn mức: " + limitOfMoney);
                    System.out.println("Nhập số tiền muốn rút: ");
                    try {
                        withdrawMoney = Double.parseDouble(sc.nextLine());
                        if (withdrawMoney > 0) {
                            if (withdrawMoney <= limitOfMoney) {
//                                if (withdrawMoney <= amount) {
//                                    amount -= (Double) withdrawMoney;
                                limitOfMoney -= withdrawMoney;
                                UpdateLimitByAccountId(accountNumber, limitOfMoney, updatedAt);
                                transaction.createTransaction(accountNumber, "WithdrawMoney", withdrawMoney);

//                                } else {
//                                    System.out.println("Số tiền trong tài khoản không đủ!");
//                                }
                            } else {
                                System.out.println("Bạn không được rút quá hạn mức: " + limitOfMoney);
                            }
                        } else {
                            System.out.println("Số tiền phải là số dương");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Số tiền phải là kiểu số!");
                    }
                }
            } else {
                if (checkActive(accountNumber) == false) {
                    System.out.println("Tài khoản của bạn đang Disable! Bạn có muốn Active? Chọn 'y' để đồng ý");
                    while (true) {
                        choose = sc.nextLine();
                        if (choose.equals("y")) {
                            System.out.println("Active thành công");
                            updateStatus(accountNumber, "Postpaid (VISA)", status, updatedAt);
                            break;
                        } else {
                            break;
                        }
                    }
                } else {
                    System.out.println("Hạn mức: " + limitOfMoney);
                    System.out.println("Nhập số tiền muốn rút: ");
                    try {
                        withdrawMoney = Double.parseDouble(sc.nextLine());
                        if (withdrawMoney > 0) {
                            if (amount >= withdrawMoney && amount >= 0) {
                                amount -= (Double) withdrawMoney;
                                limitOfMoney -= (Double) withdrawMoney;
                                checkLimit(accountNumber, amount, limitOfMoney);
                                transaction.createTransaction(accountNumber, "RutTien", withdrawMoney);
                            } else {
                                System.out.println("Bạn đã rút tối đa hạn mức cho phép!");
                            }
                        } else {
                            System.out.println("Số tiền phải là số dương");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Số tiền phải là kiểu số!");
                    }
                }
            }
        } else {
            System.out.println("Tài khoản không tồn tại!");
        }
    }

    public void checkLimit(String accountNumber, Double amount, Double limitOfMoney) {
        // Connection con = ConnectionDB.getInstance().getConnection();
        if (connection == null) {
            System.out.println("Connection failed");
        }
        String sql = "exec LimitUpdate ?, ?, ?, ?, ?";
        try {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, accountNumber);
            cs.setDouble(2, amount);
            cs.setDouble(3, limitOfMoney);
            cs.registerOutParameter(4, Types.INTEGER);
            cs.registerOutParameter(5, Types.NVARCHAR);
            cs.executeUpdate();
            int code_res = cs.getInt(4);
            String mess_res = cs.getString(5);
            cs.close();
            if (code_res == 0) {
                System.out.println(mess_res);
            } else {
                System.out.println(mess_res);
                System.out.println("Số hạn mức còn trong tk: " + limitOfMoney);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // ConnectionDB.getInstance().closeConnection(con);
    }

    public Double queryAmountByAccountId(String accountNumber) {
        Account acc = new Account();
        if (connection == null) {
            System.out.println("Connection failed");
        }
        String sql = "SELECT accountNumber, amountOfMoney FROM Accounts WHERE accountNumber = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                acc = new Account(rs.getString("accountNumber"), rs.getDouble("amountOfMoney"), null);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acc.getAmountOfMoney();
    }

    public Double getLimitOfMoneyByAcc(String accountNumber) {
        Account acc = new Account();
        if (connection == null) {
            System.out.println("Connection failed");
        }
        String sql = "SELECT accountNumber, limitOfMoney FROM Accounts WHERE accountNumber = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                acc = new Account(rs.getString("accountNumber"), null, rs.getDouble("limitOfMoney"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acc.getLimitOfMoney();
    }

    public boolean validateAccount(String accountID) {
        List<Account> listAccount = displayListAccount();
        for (Account acc : listAccount) {
            if (accountID.equals(acc.getAccountNumber())) {
                return true;
            }
        }
        return false;
    }

    public List<Account> displayListAccount() {
        List<Account> listAcount = new ArrayList<Account>();
        if (connection == null) {
            System.out.println("Connection failed!");
        }

        String sql = "select id, customerId, accountNumber, accountType, status, amountOfMoney, limitOfMoney, createdAt, updatedAt FROM Accounts";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            boolean flag = false;
            while (rs.next()) {
                flag = true;
                Account acc = new Account();
                acc.setId(rs.getInt("id"));
                acc.setCustomerId(rs.getInt("customerId"));
                acc.setAccountNumber(rs.getString("accountNumber"));
                acc.setAccountType(rs.getString("accountType"));
                acc.setStatus(rs.getString("status"));
                acc.setAmountOfMoney(rs.getDouble("amountOfMoney"));
                acc.setLimitOfMoney(rs.getDouble("limitOfMoney"));
                acc.setCreatedAt(rs.getDate("createdAt"));
                acc.setUpdatedAt(rs.getDate("updatedAt"));
                listAcount.add(acc);
            }
            rs.close();
            ps.close();
            if (flag == false) {
                System.out.println("Chưa có dữ liệu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listAcount;
    }

    public int checkAccountType(String accountNumber) {
        List<Account> listAccount = new ArrayList<Account>();
        if (connection == null) {
            System.out.println("Connection failed!");
        }

        String sql = "SELECT accountNumber, accountType, status FROM accounts WHERE accountNumber = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account acc = new Account(rs.getString("accountNumber"), rs.getString("accountType"), rs.getString("status"));
                listAccount.add(acc);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Account acc : listAccount) {
            if (acc.getAccountType().equalsIgnoreCase("Prepay (VISA)")) {
                return 0;
            }
        }
        return 1;
    }

    public boolean checkActive(String accountNumber) {
        List<Account> listAccount = new ArrayList<Account>();
        if (connection == null) {
            System.out.println("Connection failed");
        }
        String sql = "select accountNumber, accountType, status from Accounts where accountNumber = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account acc = new Account(rs.getString("accountNumber"), rs.getString("accountType"), rs.getString("status"));
                listAccount.add(acc);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Account acc : listAccount) {
            if (acc.getStatus().equals("Active")) {
                return true;
            }
        }
        return false;
    }

    public void updateStatus(String accountNumber, String accountType, String status, Date updatedAt) {
        // Connection con = ConnectionDB.getInstance().getConnection();
        if (connection == null) {
            System.out.println("Connection failed");
        }
        String sql = "exec StatusUpdate ?, ?, ?, ?, ?, ?";
        try {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, accountNumber);
            cs.setString(2, accountType);
            cs.setString(3, status);
            java.sql.Date updatedAtSql = new java.sql.Date(updatedAt.getTime());
            cs.setDate(4, updatedAtSql);
            cs.registerOutParameter(5, Types.INTEGER);
            cs.registerOutParameter(6, Types.NVARCHAR);
            cs.executeUpdate();
            int code_res = cs.getInt(5);
            String mess_res = cs.getString(6);
            cs.close();
            if (code_res == 0) {
                System.out.println(mess_res);
            } else {
                System.out.println(mess_res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateLimitByAccountId(String accountNumber, Double limitOfMoney, Date updatedAt) {
        // Connection con = ConnectionDB.getInstance().getConnection();
        if (connection == null) {
            System.out.println("Connection failed");
        }
        String sql = "exec AccountUpdateLimit ?, ?, ?, ?, ?";
        try {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, accountNumber);
            cs.setDouble(2, limitOfMoney);
            java.sql.Date updatedAtSql = new java.sql.Date(updatedAt.getTime());
            cs.setDate(3, updatedAtSql);
            cs.registerOutParameter(4, Types.INTEGER);
            cs.registerOutParameter(5, Types.NVARCHAR);
            cs.executeUpdate();
            int code_res = cs.getInt(4);
            String mess_res = cs.getString(5);
            cs.close();
            if (code_res == 0) {
                System.out.println(mess_res);
            } else {
                System.out.println(mess_res);
                System.out.println("Số tiền còn trong tài khoản: " + (Double) limitOfMoney);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // ConnectionDB.getInstance().closeConnection(con);
    }

    public void UpdateAmountByAccountId(String accountNumber, Double amountOfMoney, Date updatedAt) {
        // Connection con = ConnectionDB.getInstance().getConnection();
        if (connection == null) {
            System.out.println("Connection failed");
        }
        String sql = "exec AccountUpdate ?, ?, ?, ?, ?";
        try {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, accountNumber);
            cs.setDouble(2, amountOfMoney);
            java.sql.Date updatedAtSql = new java.sql.Date(updatedAt.getTime());
            cs.setDate(3, updatedAtSql);
            cs.registerOutParameter(4, Types.INTEGER);
            cs.registerOutParameter(5, Types.NVARCHAR);
            cs.executeUpdate();
            int code_res = cs.getInt(4);
            String mess_res = cs.getString(5);
            cs.close();
            if (code_res == 0) {
                System.out.println(mess_res);
            } else {
                System.out.println(mess_res);
                System.out.println("Số tiền còn trong tài khoản: " + (Double) amountOfMoney);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // ConnectionDB.getInstance().closeConnection(con);
    }

    @Override
    public void payInAccount() {
        TransactionImpl transaction = new TransactionImpl();
        Double recharge;
        String accountNumber;
        Date updatedAt;
        String status = "Active";
        System.out.println("Nhập số tài khoản: ");
        accountNumber = sc.nextLine();
        String choose = "";
        java.time.LocalDate currentDate = java.time.LocalDate.now();
        updatedAt = java.sql.Date.valueOf(currentDate);
        //lấy ra số tiền của tài khoản
        Double amount = queryAmountByAccountId(accountNumber);
        //check tài khoản tồn tại hay ko? true == tồn tại
        if (validateAccount(accountNumber) == true) {
            //check loại tài khoản là thường hay visa? == 0 là tk thường
            if (checkAccountType(accountNumber) == 0) {
                //check xem tài khoản đã actice? == 1 là disable, 0 là active
                if (checkActive(accountNumber) == false) {
                    System.out.println("Tài khoản của bạn đang Disable! Bạn có muốn Active? Chọn 'y' để đồng ý");
                    while (true) {
                        choose = sc.nextLine();
                        if (choose.equals("y")) {
                            System.out.println("Active thành công");
                            updateStatus(choose, "Prepay (VISA)", status, updatedAt);
                            break;
                        } else {
                            break;
                        }
                    }
                } else if (checkActive(accountNumber) == true) {
                    System.out.println("Số tiền trong tài khoản " + accountNumber + " là : " + amount);
                    System.out.println("Nhập số tiền thêm vào: ");
                    try {
                        recharge = Double.parseDouble(sc.nextLine());
                        if (recharge > 0) {
                            amount += (Double) recharge;
                            //sau khi nạp thì update lại số tiền trong database
                            UpdateAmountByAccountId(accountNumber, amount, updatedAt);
                            transaction.createTransaction(accountNumber, "Recharge", recharge);
                        } else {
                            System.out.println("Số tiền bắt buộc phải > 0");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Số tiền phải là kiểu số");
                    }
                }

            } else {
                System.out.println("Bạn chỉ có thể nạp tiền vào tài khoản trả trước!");
            }
        } else {
            System.out.println("Tài khoản không tồn tại!");
        }
    }

    @Override
    public List<Account> showListAccount() {
        List<Account> listAccount = new ArrayList<Account>();
        //   Connection con = ConnectionDB.getInstance().getConnection();
        if (connection == null) {
            System.out.println("Connection failed!");
        }
        String sql = "select id, customerId, accountNumber, accountType, status, amountOfMoney, limitOfMoney, createdAt, updatedAt from Accounts";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            boolean flag = false;
            while (rs.next()) {
                flag = true;
                Account acc = new Account();
                acc.setId(rs.getInt("id"));
                acc.setCustomerId(rs.getInt("customerId"));
                acc.setAccountNumber(rs.getString("accountNumber"));
                acc.setAccountType(rs.getString("accountType"));
                acc.setStatus(rs.getString("status"));
                acc.setAmountOfMoney(rs.getDouble("amountOfMoney"));
                acc.setLimitOfMoney(rs.getDouble("limitOfMoney"));
                acc.setCreatedAt(rs.getDate("createdAt"));
                acc.setUpdatedAt(rs.getDate("updatedAt"));

                listAccount.add(acc);
            }
            rs.close();
            ps.close();
            if (flag == false) {
                System.out.println("Chưa có data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //   ConnectionDB.getInstance().closeConnection(con);
        return listAccount;
    }

    @Override
    public List<Account> showAccountInfoByAccountId() {
        List<Account> listAccount = new ArrayList<Account>();
        System.out.println("Nhập số tài khoản: ");
        String accountNumberFind = sc.nextLine();
        if (validateAccount(accountNumberFind) == true) {
            // Connection con = ConnectionDB.getInstance().getConnection();
            if (connection == null) {
                System.out.println("Connection failed!");
            }
            String sql = "select id, customerId, accountNumber, accountType, status, amountOfMoney, limitOfMoney, createdAt, updatedAt from Accounts where accountNumber = ? ";
            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, accountNumberFind);
                ResultSet rs = ps.executeQuery();
                boolean flag = false;
                System.out.format("%-10s %-10s %-10s %-15s %-15s %-20s %-20s %-20s %-20s\n",
                        "ID",
                        "ID_KH",
                        "Số TK",
                        "Loại TK",
                        "Trạng thái",
                        "Số tiền",
                        "Hạn mức",
                        "Ngày tạo",
                        "Ngày sửa");
                while (rs.next()) {
                    flag = true;
                    Account acc = new Account(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getDouble(6),
                            rs.getDouble(7),
                            rs.getDate(8),
                            rs.getDate(9));
                    listAccount.add(acc);
                }
                for (Account acc : listAccount) {
                    acc.output();
                }
                if (flag == false) {
                    System.out.println("Không tìm thấy data");
                }
                rs.close();
                ps.close();
                listAccount.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // ConnectionDB.getInstance().closeConnection(con);
        } else {
            System.out.println("Số tài khoản không tồn tại!");
        }
        return listAccount;
    }

    @Override
    public List<Account> showAccountInfoByCustomerId() {
        List<Account> listAccount = new ArrayList<Account>();
        System.out.print("Nhập id của khách hàng: ");
        int customer_id = Integer.parseInt(sc.nextLine());
        if (validateCustomer_ID(customer_id) == true) {
            // Connection con = ConnectionDB.getInstance().getConnection();
            if (connection == null) {
                System.out.println("Connection failed!");
            }
            String sql = "select id, customerId, accountNumber, accountType, status, amountOfMoney, limitOfMoney, createdAt, updatedAt from Accounts where customerId = ? ";
            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, customer_id);
                ResultSet rs = ps.executeQuery();
                boolean flag = false;
                System.out.format("%-10s %-10s %-10s %-15s %-15s %-20s %-20s %-20s %20%\n",
                        "ID",
                        "ID_KH",
                        "Số TK",
                        "Loại TK",
                        "Trạng thái",
                        "Số tiền",
                        "Hạn mức",
                        "Ngày tạo",
                        "Ngày sửa");
                while (rs.next()) {
                    flag = true;
                    Account account = new Account(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getDouble(6),
                            rs.getDouble(7),
                            rs.getDate(8),
                            rs.getDate(9)
                    );
                    listAccount.add(account);
                }
                for (Account acc : listAccount) {
                    acc.output();
                }
                if (flag == false) {
                    System.out.println("No data found");
                }
                rs.close();
                ps.close();
                listAccount.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Không tìm thấy id khách hàng");
        }
        return listAccount;
    }

    public boolean validateCustomer_ID(int customerId) {
        List<Account> listAccount = displayListAccount();
        for (Account acc : listAccount) {
            if (customerId == acc.getCustomerId()) {
                return true;
            }
        }
        return false;
    }
}
