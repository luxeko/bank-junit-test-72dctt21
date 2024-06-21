package org.app.Services.impl;

import org.app.Configs.ConnectionDB;
import org.app.Entities.Customer;
import org.app.Services.interfaces.ICustomer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

public class CustomerImpl implements ICustomer {
    Connection connection = ConnectionDB.getInstance().getConnection();

    @Override
    public Customer getInfo(int userId) {
        if (connection == null) {
            System.out.println("Connection failed!");
        }
        String query = "Select * from customer where userId = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.format("%-10s %-10s %-10s %-20s %-20s %-20s %-20s %-20s %-15s %-20s %-20s\n",
                    "ID",
                    "USERID",
                    "Mã KH",
                    "Tên khách hàng",
                    "CMT",
                    "SDT",
                    "Email",
                    "Ngày sinh",
                    "Giới tính",
                    "Địa chỉ",
                    "Loại khách hàng");
            if (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getString("customerCode"),
                        rs.getString("customerName"),
                        rs.getString("citizenIdentificationNumber"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getDate("dob"),
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getString("customerType")
                );
                customer.output();
            } else {
                System.out.println("Chưa có data");
            }
            rs.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createCustomer(Customer customer) {
        customer.setId(1);
        customer.setCustomerCode("C001");
        customer.setCustomerName("John Doe");
        customer.setCitizenIdentificationNumber("123456789");
        customer.setPhoneNumber("0123456789");
        customer.setEmail("john.doe@example.com");
        customer.setDob(new Date());
        customer.setGender("Male");
        customer.setAddress("123 Main St");
        customer.setCustomerType("Normal");
        // b1: Kết nối đên database
        if (connection == null) {
            System.out.println("Connection failed!");
        }
        // b2: Thực hiện truy vấn
        String query = "insert into customers (customerCode, customerName, citizenIdentificationNumber, phoneNumber, email, dob, gender, address, customerType) values (?, ?, ?, ?, ?, convert(date, ?, 103), ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customer.getCustomerCode());
            preparedStatement.setString(2, customer.getCustomerName());
            preparedStatement.setString(3, customer.getCitizenIdentificationNumber());
            preparedStatement.setString(4, customer.getPhoneNumber());
            preparedStatement.setString(5, customer.getEmail());
            preparedStatement.setString(6, customer.getDob2());
            preparedStatement.setString(7, customer.getGender());
            preparedStatement.setString(8, customer.getAddress());
            preparedStatement.setString(9, customer.getCustomerType());

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
    public void updateCustomer() {

    }

    @Override
    public void deleteCustomer() {

    }

    @Override
    public List<?> showListCustomer() {
        return null;
    }
}
