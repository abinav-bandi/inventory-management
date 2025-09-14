package com.inventory.dao;
import com.inventory.util.DBConnection;
import com.inventory.model.product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Insert Product
    public void addProduct(product product) throws SQLException {
        String sql = "INSERT INTO products (id, name, category, quantity, price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, product.getId());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getCategory());
            stmt.setInt(4, product.getQuantity());
            stmt.setDouble(5, product.getPrice());
            stmt.executeUpdate();
        }
    }
}

