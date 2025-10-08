package com.inventory.dao;

import com.inventory.model.User;
import com.inventory.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAOImpl {


    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (id, username, password, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User added to database successfully: " + user.getUsername());
            } else {
                System.out.println("Failed to add user: " + user.getUsername());
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("User already exists in the database: " + user.getUsername());
        }
    }

    public List<User> getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username LIKE ?";
        List<User> users = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + username + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                );
                users.add(user);
            }

            if (!users.isEmpty()) {
                System.out.println("🔎 Found " + users.size() + " users matching: " + username);
            } else {
                System.out.println("❌ No users found with username like: " + username);
            }
        }
        return users;
    }

}
