package com.inventory;
import com.inventory.model.product;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class InventoryManager {

    List<product> products = new ArrayList<product>();
    Scanner sc = new Scanner(System.in);

    public void addProduct() {
        System.out.print("Enter ID: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Category: ");
        String category = sc.nextLine();
        System.out.print("Enter Quantity: ");
        int qty = Integer.parseInt(sc.nextLine());
        System.out.print("Enter Price: ");
        double price = Double.parseDouble(sc.nextLine());

        product p = new product(id, name, qty, price, category);
        products.add(p);
        System.out.println("Product Added");
    }

    public void removeProduct() {
        System.out.print("Enter ID to remove: ");
        int id = Integer.parseInt(sc.nextLine());

        product toRemove = null;
        for (product p : products) {
            if (p.getId() == id) {
                toRemove = p;
                break;
            }
        }

        if (toRemove != null) {
            products.remove(toRemove);
            System.out.println("Product Removed");
        } else {
            System.out.println("Product not found");
        }
    }

    public void updateProduct() {
        System.out.print("Enter ID to update: ");
        int id = Integer.parseInt(sc.nextLine());

        for (product p : products) {
            if (p.getId() == id) {
                System.out.print("Enter new Quantity: ");
                int qty = Integer.parseInt(sc.nextLine());
                System.out.print("Enter new Price: ");
                double price = Double.parseDouble(sc.nextLine());

                p.setQuantity(qty);
                p.setPrice(price);

                System.out.println("Product Updated");
                return;
            }
        }
        System.out.println("Product not found");
    }

    public void searchProduct() {
        System.out.print("Enter Name to search: ");
        String name = sc.nextLine();

        for (product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                p.display();
                return;
            }
        }
        System.out.println("Product not found");
    }

    public void displayAll() {
        if (products.isEmpty()) {
            System.out.println("No products available");
        } else {
            for (product p : products) {
                p.display();
            }
        }
    }

}
