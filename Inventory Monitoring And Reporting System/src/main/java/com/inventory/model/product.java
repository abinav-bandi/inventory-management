package com.inventory.model;

public class product {
    private String id;
    private String name;
    private int quantity;
    private double price;


    public product(String id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    public double stockValue() {
        return quantity * price;
    }
    public void display() {
        System.out.println("Product ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Quantity: " + quantity);
        System.out.println("Price: " + price);
        System.out.println("Stock Value: " + stockValue());
        System.out.println();
        }

}
