package org.app.Configs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

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
        try (InputStream input = ConstraintVariable.class.getClassLoader().getResourceAsStream("application.properties")) {

            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            prop.load(input);
            if (prop.getProperty("DATABASE_URL") != null) {
                DATABASE_URL = prop.getProperty("DATABASE_URL");
            } else {
                DATABASE_URL = "jdbc:sqlserver://127.0.0.1:1433;databaseName=bank_tester";
            }
            if (prop.getProperty("DATABASE_USERNAME") != null) {
                DATABASE_USERNAME = prop.getProperty("DATABASE_USERNAME");
            } else {
                DATABASE_USERNAME = "bank";
            }
            if (prop.getProperty("DATABASE_PASSWORD") != null) {
                DATABASE_PASSWORD = prop.getProperty("DATABASE_PASSWORD");
            } else {
                DATABASE_PASSWORD = "123456";
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
