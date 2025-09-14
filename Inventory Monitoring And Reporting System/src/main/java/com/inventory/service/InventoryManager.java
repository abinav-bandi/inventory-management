package com.inventory.service;

import com.inventory.model.product;
import com.inventory.dao.ProductDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.sql.SQLException;

public class InventoryManager {

    List<product> products = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    private ProductDAO productDAO = new ProductDAO(); // Add this line

    public void addProduct() {
        try {
            System.out.print("Enter ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Category: ");
            String category = sc.nextLine();

            System.out.print("Enter Quantity: ");
            int qty = sc.nextInt();
            if (qty < 0) throw new IllegalArgumentException("Quantity cannot be negative");

            System.out.print("Enter Price: ");
            double price = sc.nextDouble();
            if (price < 0) throw new IllegalArgumentException("Price cannot be negative");

            product p = new product(id, name, qty, price, category);

            // Save to database
            productDAO.addProduct(p);

            // Also add to local list for current session
            products.add(p);

            System.out.println("✅ Product Added Successfully to database!");

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Illegal argument: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
            e.printStackTrace(); // This will show detailed error
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    public void removeProduct() {
        try {
            System.out.print("Enter ID to remove: ");
            int id = sc.nextInt();

            // Remove from database
            boolean removed = productDAO.deleteProduct(id);

            if (removed) {
                // Also remove from local list
                products.removeIf(p -> p.getId() == id);
                System.out.println("✅ Product Removed from database!");
            } else {
                System.out.println("❌ Product with ID " + id + " not found in database.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    public void updateProduct() {
        try {
            System.out.print("Enter ID to update: ");
            int id = Integer.parseInt(sc.nextLine());

            // Find product in database first
            product p = productDAO.findById(id);

            if (p != null) {
                System.out.print("Enter new Quantity: ");
                int qty = Integer.parseInt(sc.nextLine());
                if (qty < 0) throw new IllegalArgumentException("Quantity cannot be negative");

                System.out.print("Enter new Price: ");
                double price = Double.parseDouble(sc.nextLine());
                if (price < 0) throw new IllegalArgumentException("Price cannot be negative");

                p.setQuantity(qty);
                p.setPrice(price);

                // Update in database
                boolean updated = productDAO.updateProduct(p);

                if (updated) {
                    System.out.println("✅ Product Updated in database!");
                } else {
                    System.out.println("❌ Failed to update product.");
                }
            } else {
                System.out.println("❌ Product not found in database.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    public void searchProduct() {
        try {
            System.out.print("Enter Name to search: ");
            String name = sc.nextLine();

            // Search in database
            List<product> found = productDAO.searchByName(name);

            if (!found.isEmpty()) {
                System.out.println("🔍 Found products:");
                for (product p : found) {
                    p.display();
                }
            } else {
                System.out.println("❌ No products found with name containing: " + name);
            }

        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    public void displayAll() {
        try {
            // Get all products from database
            List<product> allProducts = productDAO.getAllProducts();

            if (allProducts.isEmpty()) {
                System.out.println("📋 No products available in database");
            } else {
                System.out.println("📋 All Products from Database:");
                for (product p : allProducts) {
                    p.display();
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}