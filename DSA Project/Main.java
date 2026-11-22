import admin.Admin;
import admin.AdminAuthentication;
import admin.AdminDashboard;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        AdminAuthentication auth = new AdminAuthentication();
        AdminDashboard dashboard = new AdminDashboard();

        System.out.println("===== TRAIN TICKET RESERVATION SYSTEM =====");

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        Admin admin = auth.login(username, password);

        if (admin != null) {
            dashboard.showDashboard(admin);
        } else {
            System.out.println("Login failed. Please try again.");
        }
    }
}