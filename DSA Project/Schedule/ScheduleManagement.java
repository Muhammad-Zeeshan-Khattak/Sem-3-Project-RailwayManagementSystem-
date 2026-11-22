
package Schedule;

import db.ConnectionDb;
import java.sql.*;
import java.time.*;
import java.util.Scanner;

public class ScheduleManagement {

    private ScheduleMinHeap heap;

    // Constructor
    public ScheduleManagement() {
        heap = new ScheduleMinHeap(100);
    }

    // Load schedules from MySQL into Min-Heap  O(logn)  --> Time Complexity
    public void loadSchedules() {

        Connection con = ConnectionDb.getConnection();

        String query = "SELECT * FROM schedule";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Schedule schedule = new Schedule(
                    rs.getInt("schedule_id"),
                    rs.getTime("departure_time").toLocalTime(),
                    rs.getTime("arrival_time").toLocalTime(),
                    rs.getDate("travel_date").toLocalDate(),
                    rs.getInt("train_id"),
                    rs.getInt("route_id")
                );

                heap.insertSchedule(schedule);
            }

            rs.close();
            ps.close();

            System.out.println("Schedules loaded successfully!");

        } catch (SQLException e) {
            System.out.println("Error loading schedules: " + e.getMessage());
        }
    }


    // Add new schedule to MySQL and Min-Heap
    public void addSchedule(Schedule schedule) {

        Connection con = ConnectionDb.getConnection();

        String query = "INSERT INTO schedule "
                + "(departure_time, arrival_time, travel_date, train_id, route_id) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {

            PreparedStatement ps = con.prepareStatement(
                query, Statement.RETURN_GENERATED_KEYS
            );

            ps.setTime(1, java.sql.Time.valueOf(schedule.getDepartureTime()));
            ps.setTime(2, java.sql.Time.valueOf(schedule.getArrivalTime()));
            ps.setDate(3, java.sql.Date.valueOf(schedule.getTravelDate()));
            ps.setInt(4, schedule.getTrainId());
            ps.setInt(5, schedule.getRouteId());

            int result = ps.executeUpdate();

            if (result > 0) {

                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {

                    int generatedId = rs.getInt(1);

                    Schedule newSchedule = new Schedule(
                        generatedId,
                        schedule.getDepartureTime(),
                        schedule.getArrivalTime(),
                        schedule.getTravelDate(),
                        schedule.getTrainId(),
                        schedule.getRouteId()
                    );

                    heap.insertSchedule(newSchedule);

                    System.out.println("Schedule added successfully!");
                    System.out.println("Generated Schedule ID: " + generatedId);
                }

                rs.close();
            }

            ps.close();

        } catch (SQLException e) {
            System.out.println("Error adding schedule: " + e.getMessage());
        }
    }


    // Search schedule by ID     O(n) ---> Time Complexity
    public Schedule searchSchedule(int scheduleId) {

        // Search through the Min-Heap array
        for (int i = 0; i < heap.getSize(); i++) {

            Schedule schedule = heap.getScheduleAt(i);

            if (schedule.getScheduleId() == scheduleId) {

                return schedule;
            }
        }

        return null;
    }


    // Update an existing schedule
    public void updateSchedule(
            int scheduleId,
            LocalDate newDate,
            LocalTime newDeparture,
            LocalTime newArrival,
            int newTrainId,
            int newRouteId) {

        // Find schedule in Min-Heap
        Schedule schedule = searchSchedule(scheduleId);

        if (schedule == null) {
            System.out.println("Schedule not found!");
            return;
        }

        Connection con = ConnectionDb.getConnection();

        String query = "UPDATE schedule SET "
                + "travel_date = ?, departure_time = ?, arrival_time = ?, "
                + "train_id = ?, route_id = ? "
                + "WHERE schedule_id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(query);

            ps.setDate(1, java.sql.Date.valueOf(newDate));
            ps.setTime(2, java.sql.Time.valueOf(newDeparture));
            ps.setTime(3, java.sql.Time.valueOf(newArrival));
            ps.setInt(4, newTrainId);
            ps.setInt(5, newRouteId);
            ps.setInt(6, scheduleId);

            int result = ps.executeUpdate();
            ps.close();

            if (result >= 0) {

                // Save all existing schedules
                Schedule[] existingSchedules =
                        new Schedule[heap.getSize()];

                for (int i = 0; i < heap.getSize(); i++) {
                    existingSchedules[i] = heap.getScheduleAt(i);
                }

                // Update the selected schedule object
                schedule.setTravelDate(newDate);
                schedule.setDepartureTime(newDeparture);
                schedule.setArrivalTime(newArrival);
                schedule.setTrainId(newTrainId);
                schedule.setRouteId(newRouteId);

                // Rebuild the Min-Heap
                heap.clearHeap();

                for (Schedule s : existingSchedules) {
                    heap.insertSchedule(s);
                }

                System.out.println("Schedule updated successfully!");

            } else {
                System.out.println("Schedule update failed!");
            }

        } catch (SQLException e) {
            System.out.println("Error updating schedule: " + e.getMessage());
        }
    }


    // Delete data from Database   O(nlogn) --> Time Complexity
    public void deleteSchedule(int scheduleId) {

    Schedule schedule = searchSchedule(scheduleId);

    if (schedule == null) {
        System.out.println("Schedule not found!");
        return;
    }

    Connection con = ConnectionDb.getConnection();

    String query = "DELETE FROM schedule WHERE schedule_id = ?";

    try {
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, scheduleId);

        int result = ps.executeUpdate();

        ps.close();

        if (result > 0) {

            // Step 1: Save all schedules except the one being deleted
            Schedule[] existingSchedules =
                    new Schedule[heap.getSize() - 1];

            int index = 0;

            for (int i = 0; i < heap.getSize(); i++) {

                Schedule current = heap.getScheduleAt(i);

                if (current.getScheduleId() != scheduleId) {
                    existingSchedules[index] = current;
                    index++;
                }
            }

            // Step 2: Clear the old heap
            heap.clearHeap();

            // Step 3: Insert the remaining schedules again
            for (Schedule s : existingSchedules) {
                heap.insertSchedule(s);
            }

            System.out.println("Schedule deleted successfully!");

        } else {
            System.out.println("Schedule deletion failed!");
        }

    } catch (SQLException e) {
        System.out.println("Error deleting schedule: " + e.getMessage());
    }
}

// To display all objects O(n)---> Time Complexity
public void displayAllSchedules() {

    if (heap.getSize() == 0) {
        System.out.println("No schedules available!");
        return;
    }

    System.out.println("\n===== ALL SCHEDULES =====");

    for (int i = 0; i < heap.getSize(); i++) {

        Schedule schedule = heap.getScheduleAt(i);

        schedule.displaySchedule();

        System.out.println("-------------------------");
    }
}

// display Earliest Schedule  
public void displayEarliestSchedule() {

    Schedule schedule = heap.getEarliestSchedule();

    if (schedule == null) {
        System.out.println("No schedules available!");
        return;
    }

    System.out.println("\n===== EARLIEST DEPARTURE =====");

    schedule.displaySchedule();
}

public void showScheduleMenu() {

    Scanner sc = new Scanner(System.in);
    int choice;

    do {
        System.out.println("\n========== SCHEDULE MANAGEMENT ==========");
        System.out.println("1. View All Schedules");
        System.out.println("2. Search Schedule");
        System.out.println("3. Add New Schedule");
        System.out.println("4. Update Schedule");
        System.out.println("5. Delete Schedule");
        System.out.println("6. View Earliest Schedule");
        System.out.println("7. Back to Admin Dashboard");

        System.out.print("Enter your choice: ");
        choice = sc.nextInt();

        switch (choice) {

            case 1:
                displayAllSchedules();
                break;

            case 2:
                System.out.print("Enter Schedule ID: ");
                int searchId = sc.nextInt();

                Schedule schedule = searchSchedule(searchId);

                if (schedule != null) {
                    schedule.displaySchedule();
                } else {
                    System.out.println("Schedule not found!");
                }
                break;

           case 3:
                System.out.print("Enter Train ID: ");
                int trainId = sc.nextInt();

                System.out.print("Enter Route ID: ");
                int routeId = sc.nextInt();

                sc.nextLine();

                System.out.print("Enter Travel Date (YYYY-MM-DD): ");
                LocalDate travelDate = LocalDate.parse(sc.nextLine());

                System.out.print("Enter Departure Time (HH:MM): ");
                LocalTime departureTime = LocalTime.parse(sc.nextLine());

                System.out.print("Enter Arrival Time (HH:MM): ");
                LocalTime arrivalTime = LocalTime.parse(sc.nextLine());

                Schedule newSchedule = new Schedule(
                0,
                departureTime,
                arrivalTime,
                travelDate,
                trainId,
                routeId
                );

                addSchedule(newSchedule);
                break;

           case 4:
                System.out.print("Enter Schedule ID to update: ");
                int scheduleId = sc.nextInt();

                sc.nextLine();
 
                System.out.print("Enter New Travel Date (YYYY-MM-DD): ");
                LocalDate newDate = LocalDate.parse(sc.nextLine());

                System.out.print("Enter New Departure Time (HH:MM): ");
                LocalTime newDeparture = LocalTime.parse(sc.nextLine());

                System.out.print("Enter New Arrival Time (HH:MM): ");
                LocalTime newArrival = LocalTime.parse(sc.nextLine());

                System.out.print("Enter New Train ID: ");
                int newTrainId = sc.nextInt();

                System.out.print("Enter New Route ID: ");
                int newRouteId = sc.nextInt();

                updateSchedule(
                            scheduleId,
                            newDate,
                            newDeparture,
                            newArrival,
                            newTrainId,
                            newRouteId
                             );
                break;

            case 5:
                System.out.print("Enter Schedule ID to delete: ");
                int deleteId = sc.nextInt();
                deleteSchedule(deleteId);
                break;

            case 6:
                Schedule earliest = heap.getEarliestSchedule();

                if (earliest != null) {
                    earliest.displaySchedule();
                }
                break;

            case 7:
                System.out.println("Returning to Admin Dashboard...");
                break;

            default:
                System.out.println("Invalid choice!");
        }

    } while (choice != 7);
}

}
