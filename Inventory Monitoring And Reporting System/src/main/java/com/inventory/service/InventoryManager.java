package com.inventory.service;

import com.inventory.dao.ProductDAO;
import com.inventory.model.product;
import com.inventory.exception.InvalidInputException;
import com.inventory.exception.NoProductFoundException;
import com.inventory.exception.InvalidQuantityException;

import java.util.List;
import java.util.Scanner;

public class InventoryManager {
    private Scanner sc = new Scanner(System.in);
    private ProductDAO dao = new ProductDAO();

    // Add product
    public void addProduct() {
        try {
            System.out.print("Enter ID: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Category: ");
            String category = sc.nextLine();

            System.out.print("Enter Quantity: ");
            int qty = Integer.parseInt(sc.nextLine());
            if (qty < 0) throw new InvalidQuantityException("Quantity cannot be negative");

            System.out.print("Enter Price: ");
            double price = Double.parseDouble(sc.nextLine());
            if (price < 0) throw new InvalidQuantityException("Price cannot be negative");

            product p = new product(id, name, qty, price, category);
            dao.addProduct(p); // Save to DB
            System.out.println(" Product added succesfully ");

        } catch (NumberFormatException e) {
            System.out.println(" Invalid input! Please enter numbers for ID, Quantity, and Price.");
        } catch (InvalidQuantityException e) {
            System.out.println(" " + e.getMessage());
        }
    }

    // Remove product
    public void removeProduct() {
        try {
            System.out.print("Enter ID to remove: ");
            int id = Integer.parseInt(sc.nextLine());

            boolean deleted = dao.deleteProduct(id);
            if (!deleted) throw new NoProductFoundException("Product with ID " + id + " not found.");
            System.out.println("Product deleted Succesfully");

        } catch (NumberFormatException e) {
            System.out.println(" Invalid input! Please enter a number.");
        } catch (NoProductFoundException e) {
            System.out.println(" " + e.getMessage());
        }
    }

    // Update product
    public void updateProduct() {
        try {
            System.out.print("Enter ID to update: ");
            int id = Integer.parseInt(sc.nextLine());

            product existing = dao.findById(id);
            if (existing == null) throw new NoProductFoundException("Product with ID " + id + " not found.");

            System.out.print("Enter new Quantity: ");
            int qty = Integer.parseInt(sc.nextLine());
            if (qty < 0) throw new InvalidQuantityException("Quantity cannot be negative");

            System.out.print("Enter new Price: ");
            double price = Double.parseDouble(sc.nextLine());
            if (price < 0) throw new InvalidQuantityException("Price cannot be negative");

            existing.setQuantity(qty);
            existing.setPrice(price);

            boolean updated = dao.updateProduct(existing);
            if (updated) {
                System.out.println("Product updated Succesfully");
            } else {
                System.out.println(" Update failed.");
            }

        } catch (NumberFormatException e) {
            System.out.println(" Invalid input! Please enter numbers for ID, Quantity, and Price.");
        } catch (NoProductFoundException | InvalidQuantityException e) {
            System.out.println(" " + e.getMessage());
        }
    }

    // Search product
    public void searchProduct() {
        try {
            System.out.print("Enter ID to search: ");
            int id = Integer.parseInt(sc.nextLine());

            product p = dao.findById(id);
            if (p == null) throw new NoProductFoundException("Product with ID " + id + " not found.");
            p.display();

        } catch (NumberFormatException e) {
            System.out.println(" Invalid input! Please enter a number.");
        } catch (NoProductFoundException e) {
            System.out.println(" " + e.getMessage());
        }
    }

    // Display all products
    public void displayAll() {
        try {
            List<product> products = dao.getAllProducts();
            if (products.isEmpty()) {
                System.out.println("️ No products Found");
                return;
            }
            for (product p : products) {
                p.display();
            }
        } catch (Exception e) {
            System.out.println(" Error while fetching products: " + e.getMessage());
        }
    }
}
