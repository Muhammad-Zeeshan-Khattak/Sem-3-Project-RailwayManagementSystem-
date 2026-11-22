package Seat;

import db.ConnectionDb;
import java.sql.*;
import java.util.Scanner;

public class SeatManagement {

    private SeatCircularQueue seatQueue;
    private Scanner sc;

    // Constructor
    public SeatManagement() {
        seatQueue = new SeatCircularQueue(100);
        sc = new Scanner(System.in);
    }

    public void loadSeats() {

    Connection con = ConnectionDb.getConnection();

    String query = "SELECT * FROM seat";

    try {
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Seat seat = new Seat(
                rs.getInt("seat_id"),
                rs.getInt("seat_no"),
                rs.getString("class"),
                rs.getInt("train_id")
            );

            seatQueue.enqueue(seat);
        }

        rs.close();
        ps.close();

        System.out.println("Seats loaded successfully!");

    } catch (SQLException e) {
        System.out.println("Error loading seats: " + e.getMessage());
    }
}

// O(n) --> Time Complexity
  public void displayAllSeats() {
    if (seatQueue.isEmpty()) {
        System.out.println("No seats available!");
        return;
    }

    System.out.println("\n===== ALL SEATS =====");

    seatQueue.displayQueue();
}


// O(n) --> Time Complexity
  public Seat searchSeat(int seatId) {

    for (int i = 0; i < seatQueue.getSize(); i++) {

        Seat seat = seatQueue.getSeatAt(i);

        if (seat.getSeatId() == seatId) {
            return seat;
        }
    }

    return null;
}  

// O(n) --> Time Complexity
  public void searchSeatMenu() {

    System.out.print("Enter Seat ID: ");
    int seatId = sc.nextInt();

    Seat seat = searchSeat(seatId);

    if (seat != null) {
        System.out.println("\nSeat Found!");
        seat.displaySeat();
    } else {
        System.out.println("Seat not found!");
    }
}


// O(1) --> Time Complexity
    public void addSeat() {

    System.out.print("Enter Seat Number: ");
    int seatNo = sc.nextInt();
    sc.nextLine();

    System.out.print("Enter Seat Class: ");
    String seatClass = sc.nextLine();

    System.out.print("Enter Train ID: ");
    int trainId = sc.nextInt();

    Connection con = ConnectionDb.getConnection();

    String query = "INSERT INTO seat (seat_no, class, train_id) VALUES (?, ?, ?)";

    try {
        PreparedStatement ps = con.prepareStatement(
            query, java.sql.Statement.RETURN_GENERATED_KEYS
        );

        ps.setInt(1, seatNo);
        ps.setString(2, seatClass);
        ps.setInt(3, trainId);

        int result = ps.executeUpdate();

        if (result > 0) {

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {

                int seatId = rs.getInt(1);

                Seat newSeat = new Seat(
                    seatId, seatNo, seatClass, trainId
                );

                seatQueue.enqueue(newSeat);

                System.out.println("Seat added successfully!");
            }

            rs.close();
        }

        ps.close();

    } catch (SQLException e) {
        System.out.println("Error adding seat: " + e.getMessage());
    }
}


    // Update seat O(n) -->Time Complexity
    public void updateSeat() {

    System.out.print("Enter Seat ID to update: ");
    int seatId = sc.nextInt();

    Seat seat = searchSeat(seatId);

    if (seat == null) {
        System.out.println("Seat not found!");
        return;
    }

    sc.nextLine();

    System.out.print("Enter New Seat Number: ");
    int newSeatNo = sc.nextInt();
    sc.nextLine();

    System.out.print("Enter New Seat Class: ");
    String newClass = sc.nextLine();

    Connection con = ConnectionDb.getConnection();

    String query = "UPDATE seat SET seat_no = ?, class = ? WHERE seat_id = ?";

    try {
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, newSeatNo);
        ps.setString(2, newClass);
        ps.setInt(3, seatId);

        int result = ps.executeUpdate();

        if (result > 0) {

            // Update the existing object in the Circular Queue
            seat.setSeatNo(newSeatNo);
            seat.setSeatClass(newClass);

            System.out.println("Seat updated successfully!");

        } else {
            System.out.println("Seat update failed!");
        }

        ps.close();

    } catch (SQLException e) {
        System.out.println("Error updating seat: " + e.getMessage());
    }
}



   // Delete Seat O(n) --> Time Cpmlexity
   public void deleteSeat() {

    System.out.print("Enter Seat ID to delete: ");
    int seatId = sc.nextInt();

    Seat seat = searchSeat(seatId);

    if (seat == null) {
        System.out.println("Seat not found!");
        return;
    }

    Connection con = ConnectionDb.getConnection();

    String query = "DELETE FROM seat WHERE seat_id = ?";

    try {
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, seatId);

        int result = ps.executeUpdate();

        if (result > 0) {

            seatQueue.deleteSeat(seatId);

            System.out.println("Seat deleted successfully!");

        } else {
            System.out.println("Seat deletion failed!");
        }

        ps.close();

    } catch (SQLException e) {
        System.out.println("Error deleting seat: " + e.getMessage());
        System.out.println("The seat may be referenced by an existing booking.");
    }
}





public void viewAvailableSeats() {

    System.out.print("Enter Schedule ID: ");
    int scheduleId = sc.nextInt();

    Connection con = ConnectionDb.getConnection();

    String query =
        "SELECT s.seat_id, s.seat_no, s.class, s.train_id " +
        "FROM seat s " +
        "JOIN schedule sch ON s.train_id = sch.train_id " +
        "WHERE sch.schedule_id = ? " +
        "AND NOT EXISTS (" +
        "    SELECT 1 FROM ticket t " +
        "    WHERE t.schedule_id = sch.schedule_id " +
        "    AND t.seat_id = s.seat_id " +
        "    AND t.status = TRUE" +
        ")";

    try {
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, scheduleId);

        ResultSet rs = ps.executeQuery();

        boolean found = false;

        System.out.println("\n===== AVAILABLE SEATS =====");

        while (rs.next()) {

            found = true;

            Seat seat = new Seat(
                rs.getInt("seat_id"),
                rs.getInt("seat_no"),
                rs.getString("class"),
                rs.getInt("train_id")
            );

            seat.displaySeat();
            System.out.println("--------------------");
        }

        if (!found) {
            System.out.println("No available seats found!");
        }

        rs.close();
        ps.close();

    } catch (SQLException e) {
        System.out.println("Error viewing available seats: " + e.getMessage());
    }
}

public void seatMenu() {

    int choice;

    do {

        System.out.println("\n========== SEAT MANAGEMENT ==========");
        System.out.println("1. View All Seats");
        System.out.println("2. Search Seat");
        System.out.println("3. Add New Seat");
        System.out.println("4. Update Seat");
        System.out.println("5. Delete Seat");
        System.out.println("6. View Available Seats");
        System.out.println("7. Back to Admin Dashboard");

        System.out.print("Enter your choice: ");
        choice = sc.nextInt();

        switch (choice) {

            case 1:
                displayAllSeats();
                break;

            case 2:
                searchSeatMenu();
                break;

            case 3:
                addSeat();
                break;

            case 4:
                updateSeat();
                break;

            case 5:
                deleteSeat();
                break;

            case 6:
                viewAvailableSeats();
                break;

            case 7:
                System.out.println("Returning to Admin Dashboard...");
                break;

            default:
                System.out.println("Invalid choice! Try again.");
        }

    } while (choice != 7);
}

      
}