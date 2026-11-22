
package admin;

import java.util.Scanner;
import train.TrainManagement;
import Route.RouteManagement;
import Station.StationManagement;
import Schedule.ScheduleManagement;
import Seat.SeatManagement;
import Ticket.TicketManagement;

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
            System.out.println("8. Logout");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    TrainManagement trainManagement =
                            new TrainManagement();
                    trainManagement.loadTrains();
                    trainManagement.showTrainMenu();
                    break;

                case 2:
                    RouteManagement routeManagement =
                            new RouteManagement();
                    routeManagement.routeMenu();
                    break;

                case 3:
                    StationManagement stationManagement =
                            new StationManagement();
                    stationManagement.stationMenu();
                    break;

                case 4:
                    ScheduleManagement scheduleManagement =
                            new ScheduleManagement();
                    scheduleManagement.loadSchedules();
                    scheduleManagement.showScheduleMenu();
                    break;

                case 5:
                    SeatManagement seatManagement =
                            new SeatManagement();
                    seatManagement.loadSeats();
                    seatManagement.seatMenu();
                    break;

                case 6:
                TicketManagement ticketManagement = new TicketManagement();

                ticketManagement.loadTickets();

                ticketManagement.showTicketMenu();

                break;

                case 7:
                    boolean registered = registerNewAdmin(sc, admin);

                    if (registered) {
                        System.out.println("Your account has been deactivated.");
                        System.out.println("Please login using the new admin account.");
                        return;
                    }
                    break;

                case 8:
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 8);
    }

    private boolean registerNewAdmin(Scanner sc, Admin currentAdmin) {

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
                0, name, username, password, email, phone, "ACTIVE"
        );

        AdminRegistration registration = new AdminRegistration();

        return registration.registerAdmin(
                newAdmin, currentAdmin.getAdminId()
        );
    }
}
