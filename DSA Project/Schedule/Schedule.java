
package Schedule;

import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule {

    private int scheduleId;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private LocalDate travelDate;
    private int trainId;
    private int routeId;

    // Constructor
    public Schedule(int scheduleId, LocalTime departureTime,
                    LocalTime arrivalTime, LocalDate travelDate,
                    int trainId, int routeId) {

        this.scheduleId = scheduleId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.travelDate = travelDate;
        this.trainId = trainId;
        this.routeId = routeId;
    }

    // Getters
    public int getScheduleId() {
        return scheduleId;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public int getTrainId() {
        return trainId;
    }

    public int getRouteId() {
        return routeId;
    }

    // Setters
    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    // Display schedule
    public void displaySchedule() {
        System.out.println("Schedule ID: " + scheduleId);
        System.out.println("Train ID: " + trainId);
        System.out.println("Route ID: " + routeId);
        System.out.println("Travel Date: " + travelDate);
        System.out.println("Departure Time: " + departureTime);
        System.out.println("Arrival Time: " + arrivalTime);
    }
}
