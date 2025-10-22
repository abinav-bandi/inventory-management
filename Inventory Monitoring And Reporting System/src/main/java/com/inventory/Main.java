package com.inventory;

import com.inventory.dao.ProductDAOImpl;
import com.inventory.dao.ProductDao;
import com.inventory.model.User;
import com.inventory.service.EmailService;
import com.inventory.service.InventoryManager;
import com.inventory.service.UserService;
import com.inventory.util.CSVHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static final Scanner sc = new Scanner(System.in);
    public static final ProductDao dao = new ProductDAOImpl();
    public static final InventoryManager manager = new InventoryManager();

    public static void main(String[] args) throws SQLException, IOException {
        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("==== Welcome to Inventory Management ====");
        System.out.println("1. Register 🎉");
        System.out.println("2. Login 🔑");
        System.out.println("3. Verify Email 📧");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        User loggedInUser = null;

        switch (choice) {
            case 1:
                // Registration
                System.out.print("📧 Enter new username : ");
                String newUsername = scanner.nextLine().trim();

                System.out.print("🔑 Enter new password: ");
                String newPassword = scanner.nextLine().trim();

                System.out.print("👤 Enter role (Admin/User): ");
                String role = scanner.nextLine().trim().toUpperCase();
                System.out.print("Enter Email Address: ");
                String email = scanner.nextLine().trim();


                boolean registered = userService.register(newUsername, newPassword, role,email);
                if (!registered) {
                    System.out.println("❌ Registration failed. Try again.");
                    return;
                }

                System.out.println("🎉  You can now login.");
                choice = 2;
            case 2:
                // Login
                System.out.print("Enter username : ");
                String username = scanner.nextLine().trim();

                System.out.print("Enter password: ");
                String password = scanner.nextLine().trim();

                loggedInUser = userService.login(username, password);
                if (loggedInUser == null) {
                    System.out.println("❌ Invalid credentials or user not verified.");
                    return;
                }
                break;
            case 3:
                System.out.print("Enter your username to verify: ");
                String emailToVerify = scanner.nextLine().trim();
                boolean verified = userService.verifyEmail(emailToVerify);
                return;
            default:
                System.out.println("❌ Invalid choice!");
                return;
        }

        // If login successful
        System.out.println("✅ Login successful! Welcome, " + loggedInUser.getUsername() +
                " (" + loggedInUser.getRole() + ")");

        if (loggedInUser.getRole().equalsIgnoreCase("ADMIN")) {
            adminMenu(scanner);
        } else {
            userMenu(scanner);
        }
    }

    // ===== ADMIN MENU =====
    public static void adminMenu(Scanner scanner) throws SQLException, IOException {
        while (true) {
            System.out.println("\n==== ADMIN INVENTORY MENU ====");
            System.out.println("1. Add product ➕");
            System.out.println("2. Remove Product ❌");
            System.out.println("3. Update product ✏️");
            System.out.println("4. Search product 🔍");
            System.out.println("5. Display All Products 📋");
            System.out.println("6. Generate Report 📝");
            System.out.println("7. Filter price by range 💰");
            System.out.println("8. Logout 👋");

            System.out.print("Enter choice: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("❌ Choice cannot be empty. Try again.");
                continue;
            }

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> manager.addProduct();
                case 2 -> manager.removeProduct();
                case 3 -> manager.updateProduct();
                case 4 -> manager.searchProduct();
                case 5 -> manager.displayAll();
                case 6 -> {
                    var products = dao.getAllProducts();
                    String filePath = CSVHelper.generateProductReport(products, "Admin");
                    EmailService.sendReport(
                            "admin@company.com",
                            "Daily Inventory Report",
                            "Attached is your latest inventory report",
                            filePath
                    );
                }
                case 7 -> manager.filterByPriceRange();
                case 8 -> {
                    System.out.println("👋 Logged out successfully!");
                    return;
                }
                default -> System.out.println("❌ Invalid choice! Try again.");
            }
        }
    }

    // ===== USER MENU =====
    private static void userMenu(Scanner scanner) throws SQLException {
        while (true) {
            System.out.println("\n==== USER INVENTORY MENU ====");
            System.out.println("1. View all products 📋");
            System.out.println("2. Search product by ID 🔍");
            System.out.println("3. Filter by price range 💰");
            System.out.println("4. Logout 👋");
            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> manager.displayAll();
                case 2 -> manager.searchProduct();
                case 3 -> manager.filterByPriceRange();
                case 4 -> {
                    System.out.println("👋 Logged out successfully!");
                    return;
                }
                default -> System.out.println("❌ Invalid choice! Try again.");
            }
        }
    }
}
//        while (true) {
//            try {
//                System.out.println("\n==== INVENTORY MENU ====");
//                System.out.println("1. Add user");
//                System.out.println("2. get user by name");
//                System.out.println("3. remove user");
//                System.out.println("4. Exit");
//                System.out.print("Enter choice: ");
//                int choice = sc.nextInt();
//                switch (choice) {
//                    case 1:
//                        manager.addUser();
//                        break;
//                    case 2:
//                        manager.getUserByUsername();
//                        break;
//                    case 3:
//                        manager.removeUser();
//                        break;
//                    case 4:
//                        System.out.println("Exiting...");
//                        return;
//                    default:
//                        System.out.println("Invalid choice");
//                }
//            } catch (InputMismatchException e) {
//                System.out.println("invalid input");
//                sc.nextLine();
//            } catch (Exception e) {
//                System.out.println("exception" + e.getMessage());
//                sc.nextLine();
//            }
//        }
//    }
//}
