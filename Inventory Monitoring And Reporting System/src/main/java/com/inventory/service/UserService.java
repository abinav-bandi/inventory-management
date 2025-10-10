package com.inventory.service;

import com.inventory.dao.UserDAOImpl;
import com.inventory.dao.UserDao;
import com.inventory.model.User;

import java.sql.SQLException;

public class UserService {
    private final UserDao userDAO;

    public UserService() throws SQLException {
        this.userDAO = new UserDAOImpl();
    }
    public boolean register(String username, String password, String role) throws SQLException {
        try {
            // Check if username already exists
            User existingUser = userDAO.getUserByUsername(username);
            if (existingUser != null) {
                System.out.println("❌ Username already exists. Please try a different one.");
                return false;
            }

            // Create user object
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setRole(role.toUpperCase());

            // Save to database
            userDAO.addUser(newUser);
            System.out.println("✅ Registered successfully!");
            return true;

        } catch (SQLException e) {
            System.out.println("⚠️ Database error during registration: " + e.getMessage());
            return false;
        }
    }

    public User login(String username, String password) throws SQLException {
        try {
            User user = userDAO.getUserByUsername(username);
            if (user == null) {
                // no "user found" or "user not found" duplicate prints
                return null;
            }

            if (user.getPassword().equals(password)) {
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("⚠️ Database error during login: " + e.getMessage());
            return null;
        }
    }
}
