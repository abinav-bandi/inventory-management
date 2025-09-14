package com.inventory;

import com.inventory.util.DBConnection;
import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Connection successful!");
            } else {
                System.out.println("Failed to connect!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
        }
    }
}
