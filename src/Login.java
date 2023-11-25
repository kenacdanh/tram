import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.SwingUtilities;

public class Login {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Login ===");

        System.out.print("Do you have an account? (y/n): ");
        String response = scanner.next();

        if (response.equalsIgnoreCase("y")) {
            System.out.print("Enter ID: ");
            String id = scanner.next();

            System.out.print("Enter Password: ");
            String password = scanner.next();

            if (login("D:\\watchs\\src\\filetxt\\admin.txt", id, password)) {
                System.out.println("Login successful (Admin)");
                openManagement();
            } else if (login("D:\\watchs\\src\\filetxt\\customer.txt", id, password)) {
                System.out.println("Login successful (Customer)");
                openViewStore();
            } else {
                System.out.println("Invalid ID or password");

            }
        } else if (response.equalsIgnoreCase("n")) {
            register();
            System.out.println("Account registered successfully. Please log in.");
            System.out.println("=== Login ===");
            System.out.print("Enter ID: ");
            String id = scanner.next();

            System.out.print("Enter Password: ");
            String password = scanner.next();

            if (login("D:\\watchs\\src\\filetxt\\customer.txt", id, password)) {
                System.out.println("Login successful (Customer)");
                openViewStore();
            } else {
                System.out.println("Invalid ID or password");
                System.out.print("ReEnter ID: ");
                String reid = scanner.next();

                System.out.print("ReEnter Password: ");
                String repassword = scanner.next();

                if (login("D:\\watchs\\src\\filetxt\\customer.txt", reid, repassword)) {
                    System.out.println("Login successful (Customer)");
                    openViewStore();
                }
            }
        }
    }

    private static void register() {
        System.out.println("=== Register ===");

        String newId;
        do {
            System.out.print("Enter ID for the new account: ");
            newId = scanner.next();

            if (idExists(newId, "D:\\watchs\\src\\filetxt\\customer.txt")) {
                System.out.println("Username already exists. Please choose another.");
            }
        } while (idExists(newId, "D:\\watchs\\src\\filetxt\\customer.txt"));

        System.out.print("Enter Password for the new account: ");
        String newPassword = scanner.next();

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("D:\\watchs\\src\\filetxt\\customer.txt", true))) {
            writer.write(newId + "," + newPassword);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean login(String fileName, String id, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String storedId = parts[0].trim();
                String storedPassword = parts[1].trim();

                if (id.equals(storedId) && password.equals(storedPassword)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean idExists(String id, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String storedId = line.split(",")[0].trim();
                if (id.equals(storedId)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void openManagement() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Management management = new Management();
                management.showAdminMenu();
            }
        });
    }

    private static void openViewStore() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ViewStore view = new ViewStore();
                view.main(null);
            }
        });
    }
}
