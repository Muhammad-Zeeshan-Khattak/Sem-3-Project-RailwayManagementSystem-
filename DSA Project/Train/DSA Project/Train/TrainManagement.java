
package train;
import db.ConnectionDb;
import java.sql.*;
import java.util.Scanner;

public class TrainManagement {

    private TrainBST bst = new TrainBST();

    // Load existing trains from MySQL into BST
    public void loadTrains() {

        String query = "SELECT * FROM train";

        try {
            Connection con = ConnectionDb.getConnection();

            if (con == null) {
                System.out.println("Database connection unavailable.");
                return;
            }

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("train_id");
                String name = rs.getString("train_name");
                String type = rs.getString("train_type");

                Train train = new Train(id, name, type);

                bst.insert(train);
            }

            rs.close();
            ps.close();

            System.out.println("Train records loaded successfully!");

        } catch (Exception e) {
            System.out.println("Error loading trains: " + e.getMessage());
        }
    }


   // Method used to update train
   public void updateTrain() {
      Scanner sc = new Scanner(System.in);

    System.out.print("Enter Train ID to update: ");
    int id = sc.nextInt();
    sc.nextLine();

    Train train = bst.search(id);

    if (train == null) {
        System.out.println("Train not found!");
        return;
    }

    System.out.println("Current Train Details:");
        train.displayTrain();

    System.out.print("Enter New Train Name: ");
        String name = sc.nextLine();

    System.out.print("Enter New Train Type: ");
        String type = sc.nextLine();

    String query = "UPDATE train SET train_name = ?, train_type = ? WHERE train_id = ?";

    try {
        Connection con = ConnectionDb.getConnection();

        if (con == null) {
            System.out.println("Database connection unavailable.");
            return;
        }

        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, name);
        ps.setString(2, type);
        ps.setInt(3, id);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            // Update the BST only after database success
            train.setTrainName(name);
            train.setTrainType(type);

            System.out.println("Train updated successfully!");
        } else {
            System.out.println("Train update failed!");
        }

        ps.close();

    } catch (Exception e) {
        System.out.println("Error updating train: " + e.getMessage());
    }
}


    // Add new Train data into Database
    public void addTrain() {

         Scanner sc = new Scanner(System.in);

    System.out.print("Enter Train ID: ");
    int id = sc.nextInt();
    sc.nextLine();

    if (bst.contains(id)) {
        System.out.println("Train ID already exists!");
        return;
    }

    System.out.print("Enter Train Name: ");
    String name = sc.nextLine();

    System.out.print("Enter Train Type: ");
    String type = sc.nextLine();

    String query = "INSERT INTO train (train_id, train_name, train_type) VALUES (?, ?, ?)";

    try {
        Connection con = ConnectionDb.getConnection();

        if (con == null) {
            System.out.println("Database connection unavailable.");
            return;
        }

        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, type);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            Train train = new Train(id, name, type);
            bst.insert(train);

            System.out.println("Train added successfully!");
        }

        ps.close();

    } catch (Exception e) {
        System.out.println("Error adding train: " + e.getMessage());
    }
}


// Delete Tain data from database
public void deleteTrain() {
   Scanner sc = new Scanner(System.in);
   
    System.out.print("Enter Train ID to delete: ");
    int id = sc.nextInt();

    Train train = bst.search(id);

    if (train == null) {
        System.out.println("Train not found!");
        return;
    }

    System.out.println("Train Details:");
    train.displayTrain();

    System.out.print("Are you sure you want to delete? (yes/no): ");
    sc.nextLine();
    String confirm = sc.nextLine();

    if (!confirm.equalsIgnoreCase("yes")) {
        System.out.println("Deletion cancelled.");
        return;
    }

    String query = "DELETE FROM train WHERE train_id = ?";

    try {
        Connection con = ConnectionDb.getConnection();

        if (con == null) {
            System.out.println("Database connection unavailable.");
            return;
        }

        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);

        int rows = ps.executeUpdate();

        if (rows > 0) {

            // Update BST only after successful database deletion
            bst.delete(id);

            System.out.println("Train deleted successfully!");

        } else {
            System.out.println("Train deletion failed!");
        }

        ps.close();

    } catch (java.sql.SQLIntegrityConstraintViolationException e) {

        System.out.println(
            "Cannot delete this train because it is referenced by another table."
        );

    } catch (Exception e) {
        System.out.println("Error deleting train: " + e.getMessage());
    }
}



    // Train Management Menu
    public void showTrainMenu() {

        Scanner sc = new Scanner(System.in);

        int choice;

        do {
            System.out.println("\n===== TRAIN MANAGEMENT =====");
            System.out.println("1. Add New Train");
            System.out.println("2. View All Trains");
            System.out.println("3. Search Train");
            System.out.println("4. Update Train");
            System.out.println("5. Delete Train");
            System.out.println("6. Back to Admin Dashboard");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                
                case 1:
                    addTrain();
                    break;               

                case 2:
                    bst.displayAll();
                    break;

                case 3:
                    System.out.print("Enter Train ID to search: ");
                    int searchId = sc.nextInt();

                    Train foundTrain = bst.search(searchId);

                    if (foundTrain != null) {
                        System.out.println("Train Found!");
                        foundTrain.displayTrain();
                    } 
                    else {
                        System.out.println("Train not found!");
                    }
                    break;

                case 4:
                    updateTrain();
                    break;

               case 5:
                   deleteTrain();
                   break;

                case 6:
                    System.out.println("Returning to Admin Dashboard...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);
    }
}
