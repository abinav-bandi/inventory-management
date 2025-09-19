package com.inventory.util;
import com.inventory.model.product;
import java.io.*;
import java.util.*;

public class CSVHelper {
    private static final String FILE_NAME = "products.csv";
    public static void saveProducts(List<product> products) throws IOException {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME));
            for (product p : products) {
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
