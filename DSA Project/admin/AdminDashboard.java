
package admin;

import java.util.Scanner;
import train.TrainManagement;

public class AdminDashboard {

    public void showDashboard(Admin admin) {

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== ADMIN DASHBOARD =====");
            System.out.println("Welcome, " + admin.getAdminName());

            System.out.println("1. Manage Trains");
            System.out.println("2. Manage Routes");
            System.out.println("3. Manage Stations");
            System.out.println("4. Manage Schedules");
            System.out.println("5. Manage Seats");
            System.out.println("6. Manage Tickets");
            System.out.println("7. Register New Admin");
            System.out.println("8. Manage Admin Accounts");
            System.out.println("9. Logout");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // Clear the input buffer

            switch (choice) {

               case 1:
                    TrainManagement trainManagement = new TrainManagement();
                    trainManagement.loadTrains();
                    trainManagement.showTrainMenu();
                    break;

                case 2:
                    System.out.println("Route Management");
                    break;

                case 3:
                    System.out.println("Station Management");
                    break;

                case 4:
                    System.out.println("Schedule Management");
                    break;

                case 5:
                    System.out.println("Seat Management");
                    break;

                case 6:
                    System.out.println("Ticket Management");
                    break;

                case 7:
                    registerNewAdmin(sc);
                    break;
                case 8:
                    System.out.println("7. Manage Admin Accounts");
                    break;

                case 9:
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 8);
    }

    private void registerNewAdmin(Scanner sc) {

        System.out.println("\n===== REGISTER NEW ADMIN =====");

        System.out.print("Enter admin name: ");
        String name = sc.nextLine();

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        System.out.print("Enter phone number: ");
        String phone = sc.nextLine();

        Admin newAdmin = new Admin(
            0,
            name,
            username,
            password,
            email,
            phone,
            "ACTIVE"
        );

        AdminRegistration registration = new AdminRegistration();
        registration.registerAdmin(newAdmin);
    }
}
