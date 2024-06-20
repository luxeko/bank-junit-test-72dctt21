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
        ResourceBundle rs = ResourceBundle.getBundle("org.app.application");
        if (rs.containsKey("DATABASE_URL")) {
            DATABASE_URL = rs.getString("DATABASE_URL");
        } else {
            DATABASE_URL = "jdbc:sqlserver://127.0.0.1:1433;databaseName=bank_tester";
        }
        if (rs.containsKey("DATABASE_USERNAME")) {
            DATABASE_USERNAME = rs.getString("DATABASE_USERNAME");
        } else {
            DATABASE_USERNAME = "bank";
        }
        if (rs.containsKey("DATABASE_PASSWORD")) {
            DATABASE_PASSWORD = rs.getString("DATABASE_PASSWORD");
        } else {
            DATABASE_PASSWORD = "123456";
        }

    }
}
