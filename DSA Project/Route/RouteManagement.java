
package Route;

import java.util.Scanner;

public class RouteManagement {

    private RouteHashMap routeMap;
    private Scanner sc;

    public RouteManagement() {
        routeMap = new RouteHashMap();
        sc = new Scanner(System.in);

        routeMap.loadRoutes();
    }

// Menu method
public void routeMenu() {

    int choice;

    do {

        System.out.println("\n========== ROUTE MANAGEMENT ==========");
        System.out.println("1. View All Routes");
        System.out.println("2. Search Route");
        System.out.println("3. Add New Route");
        System.out.println("4. Update Route");
        System.out.println("5. view Stations in Route");
        System.out.println("6. Delete Route");
        System.out.println("7. Back to Admin Dashboard");

        System.out.print("Enter your choice: ");
        choice = sc.nextInt();

        switch (choice) {

            case 1:
                    System.out.println("\n========== ALL ROUTES ==========");
                    routeMap.displayAllRoutes();
                    break;

            
            case 2:
                    System.out.print("Enter Route ID to search: ");
                    int routeId = sc.nextInt();

                    Route route = routeMap.searchRoute(routeId);

                 if (route != null) {
                    route.displayRoute();
            }

                    break;


           
            case 3:
                    System.out.print("Enter Route ID: ");
                    int newRouteId = sc.nextInt();

                    sc.nextLine();

                    System.out.print("Enter Route Name: ");
                    String routeName = sc.nextLine();

                    Route newRoute = new Route(newRouteId, routeName);

                    routeMap.addRoute(newRoute);

                    break;


           
            case 4:
                    System.out.print("Enter Route ID to update: ");
                    int updateId = sc.nextInt();

                    sc.nextLine();

                    System.out.print("Enter New Route Name: ");
                    String newRouteName = sc.nextLine();

                    routeMap.updateRoute(updateId, newRouteName);

                    break;


          case 5:
                    System.out.print("Enter Route ID: ");
                    int stationRouteId = sc.nextInt();

                    Route selectedRoute = routeMap.searchRoute(stationRouteId);

                    if (selectedRoute != null) {
                    routeMap.displayStationsInRoute(stationRouteId);
         }

                    break;

           case 6:
                    System.out.print("Enter Route ID to delete: ");
                    int deleteId = sc.nextInt();

                    routeMap.deleteRoute(deleteId);
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
