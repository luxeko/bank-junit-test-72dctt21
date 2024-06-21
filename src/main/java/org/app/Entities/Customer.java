package org.app.Entities;

import org.app.Services.impl.CustomerImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Customer {
    private int id;
    private int userId;
    private String customerCode;
    private String customerName;
    private String citizenIdentificationNumber;
    private String phoneNumber;
    private String email;
    private Date dob;
    private String gender;
    private String address;
    private String customerType;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final Scanner sc = new Scanner(System.in);

    public Customer() {
    }

    public Customer(int id, int userId, String customerCode, String customerName, String citizenIdentificationNumber, String phoneNumber, String email, Date dob, String gender, String address, String customerType) {
        this.id = id;
        this.userId = userId;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.citizenIdentificationNumber = citizenIdentificationNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.customerType = customerType;
    }

    public String getDob2() {
        return sdf.format(this.dob);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCitizenIdentificationNumber() {
        return citizenIdentificationNumber;
    }

    public void setCitizenIdentificationNumber(String citizenIdentificationNumber) {
        this.citizenIdentificationNumber = citizenIdentificationNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Customer create() {
        Customer customer = new Customer();
        String maKH;
        boolean flag1;
        while (true) {
            System.out.print("Nhập mã khách hàng");
        }
    }

    public Customer update(int id) {
        CustomerImpl customerImpl = new CustomerImpl();
        while (true) {
            System.out.print("Nhập tên khách hàng: ");
            this.customerName = sc.nextLine();
            if (!this.customerName.trim().isEmpty()) {
                break;
            }
            System.out.println("Vui lòng nhập tên!");
        }
        while (true) {
            System.out.print("Nhập căn cước công dân: ");
            this.citizenIdentificationNumber = sc.nextLine();
            if (this.citizenIdentificationNumber.trim().isEmpty()) {
                System.out.println("Vui lòng nhập căn cước công dân!");
            } else if (customerImpl.checkExistType(this.citizenIdentificationNumber, "citizenIdentificationNumber", id)) {
                System.out.println("Căn cước công dân đã tồn tại!");
            } else {
                break;
            }
        }
        while (true) {
            System.out.print("Nhập số điện thoại: ");
            this.phoneNumber = sc.nextLine();
            if (this.phoneNumber.trim().isEmpty()) {
                System.out.println("Vui lòng nhập số điện thoại!");
            } else if (customerImpl.checkExistType(this.phoneNumber, "phoneNumber", id)) {
                System.out.println("Số điện thoại đã tồn tại!");
            } else {
                break;
            }
        }
        while (true) {
            System.out.print("Nhập email: ");
            this.email = sc.nextLine();
            if (this.email.trim().isEmpty()) {
                System.out.println("Vui lòng nhập email!");
            } else if (customerImpl.checkExistType(this.email, "email", id)) {
                System.out.println("Email đã tồn tại!");
            } else {
                break;
            }
        }
        while (true) {
            System.out.print("Nhập ngày sinh: ");
            String sdate = sc.nextLine();
            try {
                if (sdate.trim().isEmpty()) {
                    System.out.println("Vui lòng nhập ngày sinh!");
                } else {
                    this.dob = sdf.parse(sdate);
                    break;
                }
            } catch (Exception e) {
                System.out.println("Ngày sinh ko hợp lệ!");
                ;
            }
        }
        while (true) {
            int sex;
            System.out.println("Giới tính");
            System.out.println("1. Nam");
            System.out.println("0. Nữ");
            System.out.println("2. Khác");
            System.out.print("Chọn: ");
            try {
                sex = Integer.parseInt(sc.nextLine());
                if (sex == 1) {
                    this.gender = "Nam";
                    break;
                } else if (sex == 0) {
                    this.gender = "Nữ";
                    break;
                } else if (sex == 2) {
                    this.gender = "Khác";
                    break;
                } else {
                    System.out.println("Lựa chọn không hợp lệ!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng chọn số!");
            }
        }
        while (true) {
            System.out.print("Nhập địa chỉ: ");
            this.address = sc.nextLine();
            if (this.address.trim().isEmpty()) {
                System.out.println("Vui lòng nhập địa chỉ");
            } else {
                break;
            }
        }
        while(true) {
            int type;
            System.out.println("Loại khách hàng");
            System.out.println("0. Thường");
            System.out.println("1. VIP");
            System.out.print("Chọn: ");
            try {
                type = Integer.parseInt(sc.nextLine());
                if(type == 0){
                    this.customerType = "Thường";
                    break;
                }else if(type == 1){
                    this.customerType = "VIP";
                    break;
                }else{
                    System.out.println("Lựa chọn không hợp lệ!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng chọn số!");
            }
        }
        return this;
    }

    public void output() {
        System.out.format("%-10s %-10s %-10s %-20s %-20s %-20s %-20s %-20s %-15s %-20s %-20s\n",
                this.id,
                this.userId,
                this.customerCode,
                this.customerName,
                this.citizenIdentificationNumber,
                this.phoneNumber,
                this.email,
                sdf.format(this.dob),
                this.gender,
                this.address,
                this.customerType);
    }
}
