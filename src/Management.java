import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Management {
    protected static ArrayList<Watch> watchList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadWatchData("D:\\watchs\\src\\filetxt\\watchs.txt");

        showAdminMenu();
    }

    static void showAdminMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Product Management ===");
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View Products");
            System.out.println("2. Add Product");
            System.out.println("3. Remove Product");
            System.out.println("4. Update Product");
            System.out.println("5. Logout");

            System.out.print("Select an option (1-5): ");
            int choice = getIntInput("Select an option (1-5): ");

            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    addProduct();
                    saveWatchData("D:\\watchs\\src\\filetxt\\watchs.txt");
                    break;
                case 3:
                    removeProduct();
                    saveWatchData("D:\\watchs\\src\\filetxt\\watchs.txt");
                    break;
                case 4:
                    updateProduct();
                    saveWatchData("D:\\watchs\\src\\filetxt\\watchs.txt");
                    break;
                case 5:
                    saveWatchData("D:\\watchs\\src\\filetxt\\watchs.txt");
                    System.out.println("Logged out. Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }

    protected static int getIntInput(String prompt) {
        int input = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print(prompt);
                input = scanner.nextInt();
                validInput = true;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Clear the invalid input from the scanner
            }
        }

        return input;
    }

    public static void viewProducts() {
        System.out.println("\n=== Product List ===");
        for (Watch watch : watchList) {
            watch.displayInfo();
            System.out.println("--------------");

        }
        if (shouldQuit()) {
            return;
        }

    }

    private static void addProduct() {
        System.out.println("\n=== Add Product ===");

        System.out.print("Enter Product Name: ");
        String name = scanner.next();

        System.out.print("Enter Product Price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter Product Colors (space-separated): ");
        scanner.nextLine(); // Consume the newline character
        String colors = scanner.nextLine();

        Watch newWatch = new Watch(name, price, colors);
        watchList.add(newWatch);

        System.out.println("Product added successfully.");
        if (shouldQuit()) {
            return;
        }
    }

    private static void removeProduct() {
        System.out.println("\n=== Remove Product ===");

        System.out.print("Enter Product Name to Remove: ");
        String nameToRemove = scanner.next();

        for (Watch watch : watchList) {
            if (watch.getName().equalsIgnoreCase(nameToRemove)) {
                watchList.remove(watch);
                System.out.println("Product removed successfully.");
                if (shouldQuit()) {
                    return;
                }
            }
        }

        System.out.println("Product not found.");
        if (shouldQuit()) {
            return;
        }
    }

    private static void updateProduct() {
        System.out.println("\n=== Update Product ===");

        System.out.print("Enter Product Name to Update: ");
        String nameToUpdate = scanner.next();

        for (Watch watch : watchList) {
            if (watch.getName().equalsIgnoreCase(nameToUpdate)) {
                System.out.print("Enter New Product Name: ");
                String newName = scanner.next();
                watch.setName(newName);

                System.out.print("Enter New Product Price: ");
                double newPrice = scanner.nextDouble();
                watch.setPrice(newPrice);

                System.out.print("Enter New Product Colors (space-separated): ");
                scanner.nextLine(); // Consume the newline character
                String newColors = scanner.nextLine();
                watch.setColors(newColors);

                System.out.println("Product updated successfully.");
                return;
            }
        }

        System.out.println("Product not found.");
        if (shouldQuit()) {
            return;
        }
    }

    protected static void loadWatchData(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String name = parts[0].trim();
                double price = Double.parseDouble(parts[1].trim());
                String colors = parts[2].trim();

                Watch watch = new Watch(name, price, colors);
                watchList.add(watch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveWatchData(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Watch watch : watchList) {
                writer.write(watch.getName() + " " + watch.getPrice() + " " + watch.getColors());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean shouldQuit() {
        System.out.print("Press 'q' to quit, or any key to continue: ");
        String userInput = scanner.next();
        return userInput.equalsIgnoreCase("q");
    }
}
