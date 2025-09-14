package com.inventory.service;

import com.inventory.dao.ProductDAO;
import com.inventory.model.product;
import java.util.List;
import java.util.Scanner;

public class InventoryManager {
    Scanner sc = new Scanner(System.in);
    ProductDAO dao = new ProductDAO();

    // Add product
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

            dao.addProduct(p); // Save to DB
            System.out.println(" Product added to database!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Remove product
    public void removeProduct() {
        try {
            System.out.print("Enter ID to remove: ");
            int id = sc.nextInt();

            boolean deleted = dao.deleteProduct(id);
            if (deleted) {
                System.out.println(" Product deleted from database!");
            } else {
                System.out.println(" Product with ID " + id + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update product
    public void updateProduct() {
        try {
            System.out.print("Enter ID to update: ");
            int id = sc.nextInt();

            product existing = dao.findById(id);
            if (existing == null) {
                System.out.println(" Product not found in DB");
                return;
            }

            System.out.print("Enter new Quantity: ");
            int qty = sc.nextInt();
            if (qty < 0) throw new IllegalArgumentException("Quantity cannot be negative");

            System.out.print("Enter new Price: ");
            double price = sc.nextDouble();
            if (price < 0) throw new IllegalArgumentException("Price cannot be negative");

            existing.setQuantity(qty);
            existing.setPrice(price);

            boolean updated = dao.updateProduct(existing);
            if (updated) {
                System.out.println("Product updated in DB!");
            } else {
                System.out.println(" Update failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Search product
    public void searchProduct() {
        try {
            System.out.print("Enter Name to search: ");
            String name = sc.nextLine();

            List<product> results = dao.searchByName(name);
            if (results.isEmpty()) {
                System.out.println(" No products found with name: " + name);
            } else {
                for (product p : results) {
                    p.display();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Display all products
    public void displayAll() {
        try {
            List<product> products = dao.getAllProducts();
            if (products.isEmpty()) {
                System.out.println("️ No products in DB.");
            } else {
                for (product p : products) {
                    p.display();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
