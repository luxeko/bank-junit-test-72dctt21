package org.app.Entities;

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
    public String getDob2(){
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

    public void output(){
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
