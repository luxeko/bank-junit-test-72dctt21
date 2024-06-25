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
    private Date updatedAt;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private final Scanner sc = new Scanner(System.in);
    private Scanner scTest;
    private CustomerImpl customerImpl;
    private AccountImpl accountImpl;

    public Account() {
    }

    public Account(int id, int customerId, String accountNumber, String accountType, String status, Double amountOfMoney, Double limitOfMoney, Date updatedAt, Date createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.status = status;
        this.amountOfMoney = amountOfMoney;
        this.limitOfMoney = limitOfMoney;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public Account(Scanner scTest, CustomerImpl customerImpl, AccountImpl accountImpl) {
        this.scTest = scTest;
        this.customerImpl = customerImpl;
        this.accountImpl = accountImpl;
    }

    public Account(String accountNumber, String accountType, String status) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.status = status;
    }

    public Account(String accountNumber, Double amountOfMoney, Double limitOfMoney) {
        this.accountNumber = accountNumber;
        if (amountOfMoney == null) {
            this.limitOfMoney = limitOfMoney;
        } else {
            this.amountOfMoney = amountOfMoney;
        }
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

    public String getUpdatedAt2() {
        if (updatedAt != null) {
            return simpleDateFormat.format(this.updatedAt);
        }
        return null;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updateddAt) {
        this.updatedAt = updateddAt;
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

    public boolean input() {
        CustomerImpl customerImpl = new CustomerImpl();
        AccountImpl accountImpl = new AccountImpl();
        List<Customer> listCustomer = (List<Customer>) customerImpl.showListCustomer();
        List<Account> listAccount = accountImpl.showListAccount();
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
                for (Account acc : listAccount) {
                    if (this.customerId == acc.getCustomerId()) {
                        checkAccount++;
                    }
                }
                //Chưa có tk
                if (checkAccount == 0) {
                    inputGeneral();
                    this.accountType = "Prepay (VISA)";
                    type = 0;
                    if (type == 0) {
                        this.amountOfMoney = 0.0;
                        this.limitOfMoney = 1000000d;
                    }
                } else if (checkAccount == 1) {
                    String chooseVisa;
                    System.out.println("Bạn đã có tài khoản trả trước");
                    System.out.println("Tạo tài khoản trả sau (ViSA)");
                    System.out.println("Đồng ý. Chọn 'y' ");
                    System.out.println("Không đồng ý. Chọn 'n' ");
                    chooseVisa = sc.nextLine();
                    if (chooseVisa.equals("y")) {
                        inputGeneral();
                        this.accountType = "Postpaid (VISA)";
                        type = 1;
                        if (type == 1) {
                            this.limitOfMoney = 1000000d;
                            this.amountOfMoney = 3000000d;
                        }
                    } else {
                        System.out.println("Tạo thất bại");
                        System.exit(1);
                        return false;
                    }
                } else {
                    System.out.println("Bạn đã có 2 tài khoản! Không thể tạo thêm");
                    return false;
                }
                return true;
            } else if (flag1 == false) {
                System.out.println("Mã khách hàng không tồn tại!");
                return false;
            }
            return true;
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
                System.out.println("Ngày tạo ko hợp lệ!");
            }
        }
    }

    public boolean checkNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
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
