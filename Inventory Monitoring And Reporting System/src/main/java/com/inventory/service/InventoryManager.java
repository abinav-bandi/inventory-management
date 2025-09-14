package com.inventory.service;

import com.inventory.model.product;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class InventoryManager {

    List<product> products = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

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
            products.add(p);
            System.out.println("Product Added Successfully");

        } catch (IllegalArgumentException e) {
            System.out.println("illegal argument " + e.getMessage());
        }
    }

    public void removeProduct() {
        try {
            System.out.print("Enter ID to remove: ");
            int id = sc.nextInt();

            product toRemove = null;
            for (product p : products) {
                if (p.getId() == id) {
                    toRemove = p;
                    break;
                }
            }

            if (toRemove != null) {
                products.remove(toRemove);
                System.out.println(" Product Removed");
            } else {
                throw new NoSuchElementException("Product with ID " + id + " not found.");
            }

        } catch (NoSuchElementException e) {
            System.out.println("error " + e.getMessage());
        }
    }

    public void updateProduct() {
        try {
            System.out.print("Enter ID to update: ");
            int id = Integer.parseInt(sc.nextLine());

            for (product p : products) {
                if (p.getId() == id) {
                    System.out.print("Enter new Quantity: ");
                    int qty = Integer.parseInt(sc.nextLine());
                    if (qty < 0) throw new IllegalArgumentException("Quantity cannot be negative");

                    System.out.print("Enter new Price: ");
                    double price = Double.parseDouble(sc.nextLine());
                    if (price < 0) throw new IllegalArgumentException("Price cannot be negative");

                    p.setQuantity(qty);
                    p.setPrice(price);

                    System.out.println(" Product Updated");
                    return;
                }
            }
            System.out.println("Product not found");

        } catch (IllegalArgumentException e) {
            System.out.println("error" + e.getMessage());
        }
    }

    public void searchProduct() {
        try {
            System.out.print("Enter Name to search: ");
            String name = sc.nextLine();

            for (product p : products) {
                if (p.getName().equalsIgnoreCase(name)) {
                    p.display();
                    return;
                }
            }
            System.out.println(" Product not found");

        } catch (NoSuchElementException e) {
            System.out.println(" No such element provided."+e.getMessage());
        }
    }

    public void displayAll() {
        if (products.isEmpty()) {
            System.out.println("️ No products available");
        } else {
            for (product p : products) {
                p.display();
            }
        }
    }
}

