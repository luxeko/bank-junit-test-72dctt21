package org.app.Services.impl;

import org.app.Configs.ConnectionDB;
import org.app.Entities.User;
import org.app.Services.interfaces.IAuth;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthImpl implements IAuth {
    private final Connection connection = ConnectionDB.getInstance().getConnection();

    @Override
    public User login(String username, String password) {
        System.out.println("test");
        if (connection == null) {
            System.out.println("Connection failed!");
        }
        String query = "Select * from users where username = ? and password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
                rs.close();
                preparedStatement.close();
                return user;
            }
            rs.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
