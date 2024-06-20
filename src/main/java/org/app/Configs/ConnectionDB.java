package org.app.Configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static ConnectionDB instance;

    private ConnectionDB() {
    }

    public static synchronized ConnectionDB getInstance() {
        if(instance == null) {
            ConstraintVariable.loadConfigDB();
            instance = new ConnectionDB();
        }
        return instance;
    }
    public Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(ConstraintVariable.getDatabaseUrl(), ConstraintVariable.getDatabaseUsername(), ConstraintVariable.getDatabasePassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
    //Close Connection
    public void closeConnection(Connection con) {
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
