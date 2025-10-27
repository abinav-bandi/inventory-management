package com.inventory.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = System.getenv("DBURL");
    private static final String USER = System.getenv("USERNAME");
    private static final String PASSWORD = System.getenv("PASSWORD");
    private static boolean testMode = false;
    public static void enableTestMode() {
        testMode = true;
    }

    public static Connection getConnection()  {
        if(URL == null || USER == null || PASSWORD == null){
            throw new RuntimeException("Database environment variables are not set");
        }
        try {
            return DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Data base connection failed"+e);
        }
    }
}
