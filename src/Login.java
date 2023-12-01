import javax.swing.SwingUtilities;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;

public class Login {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Login ===");

        System.out.print("Do you have an account? (y/n): ");
        String response = scanner.next();

        LoginController loginController = new LoginController();

        if (response.equalsIgnoreCase("y")) {
            loginController.loginProcess();
        } else if (response.equalsIgnoreCase("n")) {
            loginController.registerAndLogin();
        }
    }
}

class User {
    private String id;
    private String password;

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}

class FileHandler {
    private String fileName;

    public FileHandler(String fileName) {
        this.fileName = fileName;
    }

    public boolean idExists(String id) {
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

    public void writeToFile(String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean login(User user) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String storedId = parts[0].trim();
                String storedPassword = parts[1].trim();

                if (user.getId().equals(storedId) && user.getPassword().equals(storedPassword)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

class LoginController {
    private Scanner scanner = new Scanner(System.in);
    private FileHandler customerFileHandler = new FileHandler("D:\\watchs\\src\\filetxt\\customer.txt");

    public void loginProcess() {
        System.out.print("Enter ID: ");
        String id = scanner.next();

        System.out.print("Enter Password: ");
        String password = scanner.next();

        User user = new User(id, password);

        FileHandler adminFileHandler = new FileHandler("D:\\watchs\\src\\filetxt\\admin.txt");
        FileHandler customerFileHandler = new FileHandler("D:\\watchs\\src\\filetxt\\customer.txt");

        if (adminFileHandler.login(user)) {
            System.out.println("Login successful (Admin)");
            openManagement();
        } else if (customerFileHandler.login(user)) {
            System.out.println("Login successful (Customer)");
            openViewStore();
        } else {
            System.out.println("Invalid ID or password");
        }
    }

    public void registerAndLogin() {
        User newUser = register();

        if (newUser != null) {
            System.out.println("Account registered successfully. Please log in.");
            loginProcess();
        }
    }

    private User register() {
        System.out.println("=== Register ===");

        String newId;
        do {
            System.out.print("Enter ID for the new account: ");
            newId = scanner.next();

            FileHandler customerFileHandler = new FileHandler("D:\\watchs\\src\\filetxt\\customer.txt");
            if (customerFileHandler.idExists(newId)) {
                System.out.println("Username already exists. Please choose another.");
            }
        } while (customerFileHandler.idExists(newId));

        System.out.print("Enter Password for the new account: ");
        String newPassword = scanner.next();

        User newUser = new User(newId, newPassword);

        customerFileHandler.writeToFile(newUser.getId() + "," + newUser.getPassword());

        return newUser;
    }

    private void openManagement() {
        SwingUtilities.invokeLater(() -> {
            Management management = new Management();
            management.main(null);
        });
    }

    private void openViewStore() {
        SwingUtilities.invokeLater(() -> {
            ViewStore view = new ViewStore();
            view.main(null);
            ;
        });
    }
}
