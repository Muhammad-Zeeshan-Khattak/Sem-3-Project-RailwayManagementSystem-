package Ticket;

import db.ConnectionDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class TicketManagement {

    private TicketQueue ticketQueue;
    private Scanner sc;

    public TicketManagement() {
        ticketQueue = new TicketQueue(100);
        sc = new Scanner(System.in);
    }

    // Load tickets from MySQL into Queue
    public void loadTickets() {

        ticketQueue.clearQueue();

        String query = "SELECT * FROM ticket";

        try {
            Connection con = ConnectionDb.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int ticketId = rs.getInt("ticket_id");
                LocalDate bookingDate =
                        rs.getDate("booking_date").toLocalDate();

                boolean status = rs.getBoolean("status");
                double ticketPrice = rs.getDouble("ticket_price");

                int scheduleId = rs.getInt("schedule_id");
                int seatId = rs.getInt("seat_id");
                int userId = rs.getInt("user_id");

                Ticket ticket = new Ticket(
                        ticketId,
                        bookingDate,
                        status,
                        ticketPrice,
                        scheduleId,
                        seatId,
                        userId
                );

                ticketQueue.enqueue(ticket);
            }

            System.out.println("Tickets loaded successfully!");

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error loading tickets: " + e.getMessage());
        }
    }

    // Display all tickets - O(n)
 public void displayAllTickets() {

    if (ticketQueue.isEmpty()) {
        System.out.println("No tickets found!");
        return;
    }

    ticketQueue.displayQueue();
   }

   // Search ticket - O(n)
public void searchTicket() {

    System.out.print("Enter Ticket ID: ");
    int ticketId = sc.nextInt();

    for (int i = 0; i < ticketQueue.getSize(); i++) {

        Ticket ticket = ticketQueue.getTicketAt(i);

        if (ticket.getTicketId() == ticketId) {
            System.out.println("Ticket Found!");
            ticket.displayTicket();
            return;
        }
    }

    System.out.println("Ticket not found!");
}



// View tickets by User ID - O(n)
public void viewTicketsByUser() {

    System.out.print("Enter User ID: ");
    int userId = sc.nextInt();

    boolean found = false;

    System.out.println("\n===== USER TICKETS =====");

    for (int i = 0; i < ticketQueue.getSize(); i++) {

        Ticket ticket = ticketQueue.getTicketAt(i);

        if (ticket.getUserId() == userId) {

            ticket.displayTicket();
            found = true;
        }
    }

    if (!found) {
        System.out.println("No tickets found for this user!");
    }
}


// Book a new ticket
public void bookTicket() {

    if (ticketQueue.isFull()) {
        System.out.println("Ticket Queue is full!");
        return;
    }

    System.out.print("Enter User ID: ");
    int userId = sc.nextInt();

    System.out.print("Enter Schedule ID: ");
    int scheduleId = sc.nextInt();

    System.out.print("Enter Seat ID: ");
    int seatId = sc.nextInt();

    System.out.print("Enter Ticket Price: ");
    double ticketPrice = sc.nextDouble();

    String checkQuery =
        "SELECT s.seat_id " +
        "FROM seat s " +
        "JOIN schedule sch ON s.train_id = sch.train_id " +
        "WHERE sch.schedule_id = ? " +
        "AND s.seat_id = ? " +
        "AND NOT EXISTS (" +
        "    SELECT 1 FROM ticket t " +
        "    WHERE t.schedule_id = sch.schedule_id " +
        "    AND t.seat_id = s.seat_id " +
        "    AND t.status = TRUE" +
        ")";

    String insertQuery =
        "INSERT INTO ticket " +
        "(booking_date, status, ticket_price, schedule_id, seat_id, user_id) " +
        "VALUES (?, TRUE, ?, ?, ?, ?)";

    try {
        Connection con = ConnectionDb.getConnection();

        // Step 1: Check seat availability for this schedule
        try (PreparedStatement checkPs = con.prepareStatement(checkQuery)) {

            checkPs.setInt(1, scheduleId);
            checkPs.setInt(2, seatId);

            try (ResultSet rs = checkPs.executeQuery()) {

                if (!rs.next()) {
                    System.out.println(
                        "Seat is unavailable or does not belong to this train!"
                    );
                    return;
                }
            }
        }

        // Step 2: Insert ticket into MySQL
        try (PreparedStatement ps = con.prepareStatement(
                insertQuery, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            ps.setDouble(2, ticketPrice);
            ps.setInt(3, scheduleId);
            ps.setInt(4, seatId);
            ps.setInt(5, userId);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                try (ResultSet keys = ps.getGeneratedKeys()) {

                    if (keys.next()) {

                        int ticketId = keys.getInt(1);

                        Ticket newTicket = new Ticket(
                            ticketId,
                            LocalDate.now(),
                            true,
                            ticketPrice,
                            scheduleId,
                            seatId,
                            userId
                        );

                        ticketQueue.enqueue(newTicket);

                        System.out.println(
                            "Ticket booked successfully! Ticket ID: " + ticketId
                        );
                    }
                }
            }
        }

    } catch (SQLException e) {
        System.out.println("Error booking ticket: " + e.getMessage());
    }
}



// Cancel ticket - O(n)
public void cancelTicket() {

    System.out.print("Enter Ticket ID to cancel: ");
    int ticketId = sc.nextInt();

    Ticket selectedTicket = null;

    // Step 1: Search ticket in Queue
    for (int i = 0; i < ticketQueue.getSize(); i++) {

        Ticket ticket = ticketQueue.getTicketAt(i);

        if (ticket.getTicketId() == ticketId) {
            selectedTicket = ticket;
            break;
        }
    }

    if (selectedTicket == null) {
        System.out.println("Ticket not found!");
        return;
    }

    if (!selectedTicket.getStatus()) {
        System.out.println("This ticket is already cancelled!");
        return;
    }

    // Step 2: Update ticket status in MySQL
    String query =
        "UPDATE ticket SET status = FALSE " +
        "WHERE ticket_id = ? AND status = TRUE";

    try {
        Connection con = ConnectionDb.getConnection();

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, ticketId);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                // Step 3: Update Queue object
                selectedTicket.setStatus(false);

                System.out.println("Ticket cancelled successfully!");
            } else {
                System.out.println("Ticket cancellation failed!");
            }
        }

    } catch (SQLException e) {
        System.out.println("Error cancelling ticket: " + e.getMessage());
    }
}



public void showTicketMenu() {

    int choice;

    do {
        System.out.println("\n========== TICKET MANAGEMENT ==========");
        System.out.println("1. View All Tickets");
        System.out.println("2. Search Ticket");
        System.out.println("3. View Tickets by User");
        System.out.println("4. Book New Ticket");
        System.out.println("5. Cancel Ticket");
        System.out.println("6. Back to Admin Dashboard");
        System.out.println("=======================================");

        System.out.print("Enter your choice: ");
        choice = sc.nextInt();

        switch (choice) {

            case 1:
                displayAllTickets();
                break;

            case 2:
                searchTicket();
                break;

            case 3:
                viewTicketsByUser();
                break;

            case 4:
                bookTicket();
                break;

            case 5:
                cancelTicket();
                break;

            case 6:
                System.out.println("Returning to Admin Dashboard...");
                break;

            default:
                System.out.println("Invalid choice! Try again.");
        }

    } while (choice != 6);
}

}
