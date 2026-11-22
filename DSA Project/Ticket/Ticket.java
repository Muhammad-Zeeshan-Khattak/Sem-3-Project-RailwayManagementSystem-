package Ticket;

import java.time.LocalDate;

public class Ticket {

    private int ticketId;
    private LocalDate bookingDate;
    private boolean status;
    private double ticketPrice;
    private int scheduleId;
    private int seatId;
    private int userId;

    // Constructor
    public Ticket(int ticketId, LocalDate bookingDate,
                  boolean status, double ticketPrice,
                  int scheduleId, int seatId, int userId) {

        this.ticketId = ticketId;
        this.bookingDate = bookingDate;
        this.status = status;
        this.ticketPrice = ticketPrice;
        this.scheduleId = scheduleId;
        this.seatId = seatId;
        this.userId = userId;
    }

    // Getters
    public int getTicketId() {
        return ticketId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public boolean getStatus() {
        return status;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public int getSeatId() {
        return seatId;
    }

    public int getUserId() {
        return userId;
    }

    // Setters
    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    // Display ticket
    public void displayTicket() {
        System.out.println("\n========== TICKET DETAILS ==========");
        System.out.println("Ticket ID: " + ticketId);
        System.out.println("Booking Date: " + bookingDate);
        System.out.println("Status: " + (status ? "Booked" : "Cancelled"));
        System.out.println("Ticket Price: " + ticketPrice);
        System.out.println("Schedule ID: " + scheduleId);
        System.out.println("Seat ID: " + seatId);
        System.out.println("User ID: " + userId);
        System.out.println("====================================");
    }
}