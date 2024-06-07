package org.app.Configs;

import java.util.ResourceBundle;

public class ConstraintVariable {
    public static String getDatabaseUrl() {
        return DATABASE_URL;
    }

    public static String getDatabaseUsername() {
        return DATABASE_USERNAME;
    }

    public static String getDatabasePassword() {
        return DATABASE_PASSWORD;
    }

    private static String DATABASE_URL;
    private static String DATABASE_USERNAME;
    private static String DATABASE_PASSWORD;

    public static void loadConfigDB() {
        ResourceBundle rs = ResourceBundle.getBundle("bank-test-72dctt21.main.java.org.app.application");
        if (rs.containsKey("database_Url")) {
            DATABASE_URL = rs.getString("database_Url");
        } else {
            DATABASE_URL = "jdbc:sqlserver://127.0.0.1:1433;databaseName=atm_manager";
        }
        if (rs.containsKey("database_UserName")) {
            DATABASE_USERNAME = rs.getString("DATABASE_USERNAME");
        } else {
            DATABASE_USERNAME = "root";
        }
        if (rs.containsKey("database_PassWord")) {
            DATABASE_PASSWORD = rs.getString("DATABASE_PASSWORD");
        } else {
            DATABASE_PASSWORD = "";
        }

    }
}
