package org.app.Entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import org.app.Services.impl.CustomerImpl;
import org.app.Services.impl.AccountImpl;

public class Account {

    private int id;
    private int customerId;
    private String accountNumber;
    private String accountType;
    private String status;
    private Double amountOfMoney;
    private Double limitOfMoney;
    private Date createdAt;
    private Date updateddAt;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private final Scanner sc = new Scanner(System.in);

    public Account() {
    }

    public Account(int id, int customerId, String accountNumber, String accountType, String status, Double amountOfMoney, Double limitOfMoney, Date updateddAt, Date createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.status = status;
        this.amountOfMoney = amountOfMoney;
        this.limitOfMoney = limitOfMoney;
        this.createdAt = createdAt;
        this.updateddAt = updateddAt;
    }

    public Account(String accountNumber, String accountType, String status) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.status = status;
    }

    public Account(String accountNumber, Double amountOfMoney) {
        this.accountNumber = accountNumber;
        this.amountOfMoney = amountOfMoney;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getCreatedAt2() {
        return simpleDateFormat.format(this.createdAt);
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateddAt() {
        return updateddAt;
    }

    public void setUpdateddAt(Date updateddAt) {
        this.updateddAt = updateddAt;
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

    public void input() {
        CustomerImpl customerImpl = new CustomerImpl();
        AccountImpl accountImpl = new AccountImpl();
        List<Customer> listCustomer = (List<Customer>) customerImpl.showListCustomer();
        List<Account> lissAccount = accountImpl.showListAccount();
        String customerCode;
        boolean flag1;
        while (true) {
            flag1 = false;
            System.out.println("Nhập mã khách hàng: ");
            customerCode = sc.nextLine();
            for (Customer customer : listCustomer) {
                //Kiểm tra có tồn tại mã khách hàng
                if (customerCode.equals(customer.getCustomerCode())) {
                    this.customerId = customer.getId();
                    flag1 = true;
                }
            }
            if (flag1 == true) {
                int type;
                int checkAccount = 0;
                //check kh_id đã tồn tại chưa
                for (Account acc : lissAccount) {
                    if (this.customerId == acc.getCustomerId()) {
                        checkAccount++;
                    }
                }
                //Chưa có tk
                if (checkAccount == 0) {
                    inputGeneral();
                    this.accountType = "Prepay";
                    type = 0;
                    if (type == 0) {
                        this.amountOfMoney = 0d;
                        this.limitOfMoney = 1000000d;
                    }
                } else if (checkAccount == 1) {
                    String chonVisa;
                    System.out.println("Bạn đã có tài khoản trả trước");
                    System.out.println("Tạo tài khoản trả sau (ViSA)");
                    System.out.println("Đồng ý. Chọn 'y' ");
                    chonVisa = sc.nextLine();
                    if (chonVisa.equals("y")) {
                        inputGeneral();
                        this.accountType = "TraSau (VISA)";
                        type = 1;
                        if (type == 1) {
                            this.limitOfMoney = 30000000d;
                            this.amountOfMoney = 30000000d;
                        }
                    } else {
                        System.out.println("Tạo thất bại");
                        System.exit(1);
                        break;
                    }
                } else {
                    System.out.println("Bạn đã có 2 tài khoản! Không thể tạo thêm");
                }
                break;
            }
            if (flag1 == false) {
                System.out.println("Mã khách hàng không tồn tại!");
            }
        }
    }
    public void inputGeneral() {
        AccountImpl accountImpl = new AccountImpl();
        List<Account> listAccount = accountImpl.showListAccount();
        // ACCOUNT ID
        while (true) {
            int count = 0;
            System.out.print("Nhập số tài khoản: ");
            this.accountNumber = sc.nextLine();
            if (this.accountNumber.length() == 6 && !this.accountNumber.trim().equals("")) {
                for (Account acc : listAccount) {
                    if (this.accountNumber.equals(acc.getAccountNumber())) {
                        System.out.println("Số tài khoản đã tồn tại");
                        count++;
                        break;
                    }
                }
                if (count == 0) {
                    break;
                }
            } else {
                System.out.println("Số tài khoản bắt buộc có 6 chữ số");
            }
        }
        while (true) {
            int status;
            System.out.println("Trạng thái");
            System.out.println("0. Disable");
            System.out.println("1. Active");
            try {
                status = Integer.parseInt(sc.nextLine());
                if (status == 0) {
                    this.status = "Disable";
                    break;
                } else if (status == 1) {
                    this.status = "Active";
                    break;
                } else {
                    System.out.println("Chọn sai! Vui lòng chọn lại");
                }
            } catch (NumberFormatException e) {
                System.out.println("Bạn phải chọn số!");
            }
        }
        while (true) {
            System.out.println("Nhập ngày tạo: ");
            String sdate = sc.nextLine();
            try {
                if (sdate.trim().equals("")) {
                    System.out.println("Bạn chưa nhập ngày tạo!");
                } else {
                    this.createdAt = simpleDateFormat.parse(sdate);
                    break;
                }
            } catch (Exception e) {
                System.out.println("Ngày tạo ko hợp lệ!");;
            }
        }
    }

    public void output() {
        System.out.format("%-10s %-10s %-10s %-15s %-15s %-20s %-20s %-20s\n",
                this.id,
                this.customerId,
                this.accountNumber,
                this.accountType,
                this.status,
                this.createdAt,
                this.amountOfMoney,
                this.limitOfMoney);
    }
}
