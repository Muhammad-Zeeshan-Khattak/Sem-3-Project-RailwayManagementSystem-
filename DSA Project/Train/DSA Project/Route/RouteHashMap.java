
package Route;

import java.util.HashMap;
import java.sql.*;
import db.ConnectionDb;;

public class RouteHashMap {

    private HashMap<Integer, Route> routes;

    public RouteHashMap() {
        routes = new HashMap<>();
    }

    // O(n) --> Time Complexity
public void loadRoutes() {

    String query = "SELECT * FROM route";

    try {
        Connection conn = ConnectionDb.getConnection();

        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            int routeId = rs.getInt("route_id");
            String routeName = rs.getString("route_name");

            Route route = new Route(routeId, routeName);

            routes.put(routeId, route);
        }

        System.out.println("Routes loaded successfully!");

    } catch (SQLException e) {
        System.out.println("Error loading routes!");
        e.printStackTrace();
    }
}


// Method to add route  O(1)--> Time complexity
public void addRoute(Route route) {

    String query = "INSERT INTO route (route_id, route_name) VALUES (?, ?)";

    try {
        Connection conn = ConnectionDb.getConnection();

        PreparedStatement ps = conn.prepareStatement(query);

        ps.setInt(1, route.getRouteId());
        ps.setString(2, route.getRouteName());

        int rows = ps.executeUpdate();


        // To insert into Hashmap
        if (rows > 0) {
            routes.put(route.getRouteId(), route);
            System.out.println("Route added successfully!");
        }

    } catch (SQLException e) {
        System.out.println("Error adding route!");
        e.printStackTrace();
    }
}

// Method to serach route  O(1) --> Time Complexity
public Route searchRoute(int routeId) {

    Route route = routes.get(routeId);

    if (route == null) {
        System.out.println("Route not found!");
        return null;
    }

    System.out.println("Route found successfully!");
    return route;
}



// Method to display all the routes   O(n) --> Time Complexity
public void displayAllRoutes() {

    if (routes.isEmpty()) {
        System.out.println("No routes available!");
        return;
    }

    for (Route route : routes.values()) {
        route.displayRoute();
        System.out.println("--------------------");
    }
}


// Method to update route      O(1) --> Time complexity
public boolean updateRoute(int routeId, String newRouteName) {

    String query = "UPDATE route SET route_name = ? WHERE route_id = ?";

    try {
        Connection conn = ConnectionDb.getConnection();

        PreparedStatement ps = conn.prepareStatement(query);

        ps.setString(1, newRouteName);
        ps.setInt(2, routeId);

        int rows = ps.executeUpdate();

        if (rows > 0) {

            Route route = routes.get(routeId);

            if (route != null) {
                route.setRouteName(newRouteName);
            }

            System.out.println("Route updated successfully!");
            return true;
        } else {
            System.out.println("Route not found!");
            return false;
        }

    } catch (SQLException e) {
        System.out.println("Error updating route!");
        e.printStackTrace();
        return false;
    }
}


// Delete Route Method  O(1) --> Time Complexity

public boolean deleteRoute(int routeId) {

    String query = "DELETE FROM route WHERE route_id = ?";

    try {
        Connection conn = ConnectionDb.getConnection();

        PreparedStatement ps = conn.prepareStatement(query);

        ps.setInt(1, routeId);

        int rows = ps.executeUpdate();

        if (rows > 0) {

            routes.remove(routeId);

            System.out.println("Route deleted successfully!");
            return true;

        } else {

            System.out.println("Route not found!");
            return false;
        }

    } catch (SQLException e) {

        System.out.println("Error deleting route!");
        e.printStackTrace();
        return false;
    }
}

// Method to deal with junction table
public void displayStationsInRoute(int routeId) {

    String query = "SELECT s.station_id, s.station_name, s.city, j.station_order "
                 + "FROM Junction j "
                 + "JOIN station s ON j.station_id = s.station_id "
                 + "WHERE j.route_id = ? "
                 + "ORDER BY j.station_order";

    try {
        Connection conn = ConnectionDb.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setInt(1, routeId);

        ResultSet rs = ps.executeQuery();

        System.out.println("\nStations in Route " + routeId);

        boolean found = false;

        while (rs.next()) {
            found = true;

            System.out.println(
                rs.getInt("station_order") + ". "
                + rs.getString("station_name")
                + " (" + rs.getString("city") + ")"
            );
        }

        if (!found) {
            System.out.println("No stations found for this route!");
        }

    } catch (SQLException e) {
        System.out.println("Error displaying route stations!");
        e.printStackTrace();
    }
}





}
