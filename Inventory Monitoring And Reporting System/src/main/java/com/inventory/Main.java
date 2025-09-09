package com.inventory;
import com.inventory.model.product;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<product> products = new ArrayList<product>();

        System.out.print("Enter number of products: ");
        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Enter details for Product " + (i + 1) + " ---");

            System.out.print("ID: ");
            String id = sc.nextLine();

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Quantity: ");
            int quantity = Integer.parseInt(sc.nextLine());

            System.out.print("Price: ");
            double price = Double.parseDouble(sc.nextLine());
            product p=new product(id,name,quantity,price);
            products.add(p);
        }


        System.out.println("\n---- Product List----");
        double totalValue = 0.0;
        for (product p : products) {
            p.display();
            totalValue += p.stockValue();
        }


        System.out.printf("TOTAL STOCK VALUE: %.2f", totalValue);



    }
}