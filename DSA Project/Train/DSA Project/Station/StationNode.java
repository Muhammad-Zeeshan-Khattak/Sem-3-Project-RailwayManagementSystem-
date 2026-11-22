package Station;
import java.sql.*;
import db.ConnectionDb;

class StationNode {

    Station data;
    StationNode prev;
    StationNode next;

    public StationNode(Station data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

class StationDoublyLinkedList {

    private StationNode head;
    private StationNode tail;

    public StationDoublyLinkedList() {
        head = null;
        tail = null;
    }


    // O(n)  --> Time complexity
    public void loadStations() {

    String query = "SELECT * FROM station";

    try {
        Connection conn = ConnectionDb.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            int id = rs.getInt("station_id");
            String name = rs.getString("station_name");
            String city = rs.getString("city");

            Station station = new Station(id, name, city);

            insertIntoList(station);
        }

        System.out.println("Stations loaded successfully!");

    } catch (SQLException e) {
        System.out.println("Error loading stations!");
        e.printStackTrace();
    }
}


//  O(1)--> Time complexity
private void insertIntoList(Station station) {
    StationNode newNode = new StationNode(station);

    if (head == null) {
        head = newNode;
        tail = newNode;
    } else {
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
    }
}

//   O(n) --> Time Complexity
public void displayAllStations() {

    if (head == null) {
        System.out.println("No stations available!");
        return;
    }

    StationNode current = head;

    while (current != null) {

        current.data.displayStation();

        System.out.println("----------------------");

        current = current.next;
    }
}

// O(n) --> Time Complexity
public Station searchStation(int stationId) {

    StationNode current = head;

    while (current != null) {

        if (current.data.getStationId() == stationId) {
            System.out.println("Station found!");
            return current.data;
        }

        current = current.next;
    }

    System.out.println("Station not found!");
    return null;
}


// O(1) --> Time complexity
public void addStation(String stationName, String city) {

    String query = "INSERT INTO station (station_name, city) VALUES (?, ?)";

    try {
        Connection conn = ConnectionDb.getConnection();

        PreparedStatement ps = conn.prepareStatement(
            query, java.sql.Statement.RETURN_GENERATED_KEYS
        );

        ps.setString(1, stationName);
        ps.setString(2, city);

        int rows = ps.executeUpdate();

        if (rows > 0) {

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int stationId = rs.getInt(1);

                Station newStation = new Station(
                    stationId, stationName, city
                );

                insertIntoList(newStation);

                System.out.println("Station added successfully!");
                System.out.println("Generated Station ID: " + stationId);
            }
        }

    } catch (SQLException e) {
        System.out.println("Error adding station!");
        e.printStackTrace();
    }
}

// O(n) --> Time Complexity
public void updateStation(int stationId, String newName, String newCity) {

    String query = "UPDATE station SET station_name = ?, city = ? WHERE station_id = ?";

    try {
        Connection conn = ConnectionDb.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setString(1, newName);
        ps.setString(2, newCity);
        ps.setInt(3, stationId);

        int rows = ps.executeUpdate();

        if (rows > 0) {

            StationNode current = head;

            while (current != null) {

                if (current.data.getStationId() == stationId) {
                    current.data.setStationName(newName);
                    current.data.setCity(newCity);
                    break;
                }

                current = current.next;
            }

            System.out.println("Station updated successfully!");

        } else {
            System.out.println("Station not found!");
        }

    } catch (SQLException e) {
        System.out.println("Error updating station!");
        e.printStackTrace();
    }
}

// O(n)  --> Time Complexity
public void deleteStation(int stationId) {

    String query = "DELETE FROM station WHERE station_id = ?";
    try {
        Connection conn = ConnectionDb.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setInt(1, stationId);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            StationNode current = head;

            while (current != null) {
                if (current.data.getStationId() == stationId) {

                    if (current == head) {
                        head = current.next;
                    }

                    if (current == tail) {
                        tail = current.prev;
                    }

                    if (current.prev != null) {
                        current.prev.next = current.next;
                    }

                    if (current.next != null) {
                        current.next.prev = current.prev;
                    }

                    break;
                }

                current = current.next;
            }

            System.out.println("Station deleted successfully!");

        } else {
            System.out.println("Station not found!");
        }

    } catch (SQLException e) {
        System.out.println("Error deleting station!");
        System.out.println("This station may be used in an existing route or schedule.");
    }
}
}