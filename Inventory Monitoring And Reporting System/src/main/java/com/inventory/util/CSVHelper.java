package com.inventory.util;
import com.inventory.model.Product;
import java.io.*;
import java.util.*;

public class CSVHelper {
    private static final String FILE_NAME = "products.csv";
    public static void saveProducts(List<Product> Products) throws IOException {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME));
            for (Product p : Products) {
                writer.println(p.getId() + "," +
                        p.getName() + "," +
                        p.getCategory() + "," +
                        p.getQuantity() + "," +
                        p.getPrice());
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
