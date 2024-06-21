package org.app.Services.impl;

import org.app.Configs.ConnectionDB;
import org.app.Entities.Customer;
import org.app.Services.interfaces.ICustomer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomerImpl implements ICustomer {
    Connection connection = ConnectionDB.getInstance().getConnection();
    private SimpleDateFormat sdf_sql = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Customer getInfo(int userId) {
        if (connection == null) {
            System.out.println("Connection failed!");
        }
        String query = "Select * from customers where userId = ?";
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
                rs.close();
                preparedStatement.close();
                return customer;
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
    public boolean createCustomer(Customer customer) {
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
            preparedStatement.close();
            // b3: Xử lý kết quả
            if (result == 0) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        // b1: Kết nối đên database
        if (connection == null) {
            System.out.println("Connection failed!");
        }
        String query = "Update customers set customerName = ?, citizenIdentificationNumber = ?, phoneNumber = ?, email = ?, dob = ?, gender = ?, address = ?, customerType = ? where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            java.sql.Date date_sql = java.sql.Date.valueOf(sdf_sql.format(customer.getDob()));
            preparedStatement.setString(1, customer.getCustomerName());
            preparedStatement.setString(2, customer.getCitizenIdentificationNumber());
            preparedStatement.setString(3, customer.getPhoneNumber());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setDate(5, date_sql);
            preparedStatement.setString(6, customer.getGender());
            preparedStatement.setString(7, customer.getAddress());
            preparedStatement.setString(8, customer.getCustomerType());
            preparedStatement.setInt(9, customer.getId());

            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            //B3:XU LY KET QUA
            if (result == 0) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public int getUserIdByCustomerCode(String customerCode) {
        if (connection == null) {
            System.out.println("Connection failed!");
        }
        String query = "Select userId from customers where customerCode = ?";
        try {
            int userId = 0;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customerCode);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("userId");
                preparedStatement.close();
                rs.close();
                return userId;
            }
            preparedStatement.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void deleteCustomer() {

    }

    @Override
    public List<?> showListCustomer() {
        return null;
    }

    @Override
    public boolean checkExistType(String value, String dataType, int id) {
        if (connection == null) {
            System.out.println("Connection failed!");
        }
        String query = "Select * from customers where id != ? and " + dataType + " = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, value);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                preparedStatement.close();
                rs.close();
                return true;
            }
            preparedStatement.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
