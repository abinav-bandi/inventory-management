package com.inventory.dao;

import com.inventory.model.Product;
import com.inventory.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void addUser(User user) throws SQLException ;
    User getUserByUsername(String username) throws SQLException;
    boolean deleteUser(String username) throws SQLException;

}