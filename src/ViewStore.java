import java.util.ArrayList;
import java.util.Scanner;

public class ViewStore extends Management {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadWatchData("D:\\watchs\\src\\filetxt\\watchs.txt");

        showCustomerMenu();
    }

    static void showCustomerMenu() {
        while (true) {
            System.out.println("=== Welcome to the Store ===");
            System.out.println("\nCustomer Menu:");
            System.out.println("1. View Products");
            System.out.println("2. Purchase Products");
            System.out.println("3. Exit");

            System.out.print("Select an option (1-3): ");
            int choice = getIntInput("Select an option (1-3): ");

            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    purchaseProducts();
                    break;
                case 3:
                    saveWatchData("D:\\watchs\\src\\filetxt\\watchs.txt");
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }

    private static void purchaseProducts() {
        System.out.println("\n=== Purchase Products ===");

        // Create a shopping cart for the customer
        ArrayList<Watch> shoppingCart = new ArrayList<>();

        while (true) {
            System.out.print("Enter the name of the product you want to purchase (or 'done' to finish): ");
            String productName = scanner.next();

            if (productName.equalsIgnoreCase("done")) {
                break;
            }

            Watch selectedWatch = findWatchByName(productName);
            if (selectedWatch != null) {
                shoppingCart.add(selectedWatch);
                System.out.println("Added to cart: " + selectedWatch.getName());
            } else {
                System.out.println("Product not found. Please enter a valid product name.");
            }
        }

        // Display the bill
        System.out.println("\n========== Bill ============");
        double totalAmount = 0;
        for (Watch watch : shoppingCart) {
            watch.displayInfo();
            totalAmount += watch.getPrice();
            System.out.println("--------------");
        }
        System.out.println("Total Amount: $" + totalAmount);

        if (shouldQuit()) {
            return;
        }
    }

    private static Watch findWatchByName(String name) {
        for (Watch watch : watchList) {
            if (watch.getName().equalsIgnoreCase(name)) {
                return watch;
            }
        }
        return null;
    }
}
