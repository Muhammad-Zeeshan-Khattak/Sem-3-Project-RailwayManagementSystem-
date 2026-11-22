package Station;

import java.util.Scanner;

public class StationManagement {

    private StationDoublyLinkedList stationList;
    private Scanner sc;

    public StationManagement() {
        stationList = new StationDoublyLinkedList();
        sc = new Scanner(System.in);

        stationList.loadStations();
    }

    public void stationMenu() {

    int choice;

    do {
        System.out.println("\n========== STATION MANAGEMENT ==========");
        System.out.println("1. View All Stations");
        System.out.println("2. Search Station");
        System.out.println("3. Add New Station");
        System.out.println("4. Update Station");
        System.out.println("5. Delete Station");
        System.out.println("6. Back to Admin Dashboard");

        System.out.print("Enter your choice: ");
        choice = sc.nextInt();

        switch (choice) {

            case 1:
                stationList.displayAllStations();
                break;

            case 2:
                System.out.print("Enter Station ID: ");
                int searchId = sc.nextInt();

                Station station = stationList.searchStation(searchId);

                if (station != null) {
                    station.displayStation();
                }
                break;

            case 3:
                 sc.nextLine();

                 System.out.print("Enter Station Name: ");
                 String name = sc.nextLine();

                 System.out.print("Enter City: ");
                 String city = sc.nextLine();

                 stationList.addStation(name, city);
                 break;

            case 4:
                 System.out.print("Enter Station ID to update: ");
                 int updateId = sc.nextInt();

                  sc.nextLine();

                  System.out.print("Enter New Station Name: ");
                  String newName = sc.nextLine();

                  System.out.print("Enter New City: ");
                  String newCity = sc.nextLine();

                  stationList.updateStation(updateId, newName, newCity);
                  break;

            case 5:
                  System.out.print("Enter Station ID to delete: ");
                  int deleteId = sc.nextInt();

                  stationList.deleteStation(deleteId);
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