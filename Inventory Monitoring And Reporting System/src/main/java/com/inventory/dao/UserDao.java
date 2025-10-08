package com.inventory.dao;

import com.inventory.model.Product;
import com.inventory.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void addUser(User user) throws SQLException ;
    List<User> getUserByUsername(String username) throws SQLException;

}