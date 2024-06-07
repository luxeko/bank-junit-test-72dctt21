package org.app.Configs;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    private static Connection instance;

    private Connection() {
    }

    public static synchronized Connection getInstance() {
        if(instance == null) {
            ConstraintVariable.loadConfigDB();
            instance = new Connection();
        }
        return instance;
    }
    public java.sql.Connection getConnection() {
        java.sql.Connection con = null;
        try {
            con = DriverManager.getConnection(ConstraintVariable.getDatabaseUrl(), ConstraintVariable.getDatabaseUsername(), ConstraintVariable.getDatabasePassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(" -- Connect Successfully -- ");
        return con;
    }
    //Close Connection
    public void closeConnection(java.sql.Connection con) {
        if (con != null) {
            try {
                con.close();
                System.out.println("Connect close");
            } catch (SQLException e) {
                System.out.println("Connect Found "+ e.getMessage());
            }
        }
    }
}
