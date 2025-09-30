package com.inventory.service;

import com.inventory.dao.ProductDAOImpl;
import com.inventory.model.Product;
import com.inventory.exception.NoProductFoundException;
import com.inventory.exception.InvalidQuantityException;
import com.inventory.util.CSVHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InventoryManager {
    private Scanner sc = new Scanner(System.in);
    private ProductDAOImpl dao = new ProductDAOImpl();

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

            Product p = new Product(id, name, category,qty, price);
            dao.addProduct(p); // Save to DB
            System.out.println(" Product added succesfully ");

        } catch (NumberFormatException e) {
            System.out.println(" Invalid input! Please enter numbers for ID, Quantity, and Price.");
        } catch (InvalidQuantityException e) {
            System.out.println(" " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Update product
    public void updateProduct() {
        try {
            System.out.print("Enter ID to update: ");
            int id = Integer.parseInt(sc.nextLine());

            Product existing = dao.getProductById(id);
            if (existing == null) throw new NoProductFoundException("Product with ID " + id + " not found.");
            System.out.print("Enter new Name: ");
            String name = sc.nextLine();
            System.out.print("Enter new Category: ");
            String category = sc.nextLine();
            System.out.print("Enter new Quantity: ");
            int qty = Integer.parseInt(sc.nextLine());
            if (qty < 0) throw new InvalidQuantityException("Quantity cannot be negative");

            System.out.print("Enter new Price: ");
            double price = Double.parseDouble(sc.nextLine());
            if (price < 0) throw new InvalidQuantityException("Price cannot be negative");
            existing.setName(name);
            existing.setCategory(category);
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Search product
    public void searchProduct() {
        try {
            System.out.println("\n=== Search Product ===");
            System.out.println("1. Search by ID");
            System.out.println("2. Search by Name");
            System.out.println("3. Search by Category");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter ID to search: ");
                    int id = Integer.parseInt(sc.nextLine());
                    Product p = dao.getProductById(id);
                    if(p!=null) {
                        p.display();
                    }
                    break;

                case 2:
                    System.out.print("Enter product name to search: ");
                    String name = sc.nextLine();
                    List<Product> nameResults = dao.searchByName(name);
                    nameResults.forEach(Product::display);
                    break;

                case 3:
                    System.out.print("Enter category to search: ");
                    String category = sc.nextLine();
                    List<Product> categoryResults = dao.searchByCategory(category);

                    categoryResults.forEach(Product::display);
                    break;

                default:
                    System.out.println("Invalid choice! Please select 1, 2, or 3.");
            }

        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input! Please enter a number.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateReport() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // consume newline

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Category: ");
        String category = scanner.nextLine();

        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();

        Product p = new Product(id, name, category, quantity, price);
        List<Product> list = new ArrayList<>();
        list.add(p);
        CSVHelper.saveProducts(list);
    }

    // Display all products
    public void displayAll() {
        try {
            List<Product> Products = dao.getAllProducts();
            if (Products.isEmpty()) {
                System.out.println("️ No products Found");
                return;
            }
            System.out.printf("%-5s %-15s %-15s %-10s %-10s%n ", "ID", "Name", "Category", "Quantity", "Price");
            System.out.println("------------------------------------------------------");
            Products.forEach(p ->
                    System.out.printf("%-5s %-15s %-15s %-10s %-10s%n ",p.getId(),p.getName(),p.getCategory(),p.getQuantity(),p.getPrice()));
        } catch (Exception e) {
            System.out.println(" Error while fetching products: " + e.getMessage());
        }
    }
}