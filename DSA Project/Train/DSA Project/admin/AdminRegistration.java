
package admin;

import db.ConnectionDb;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AdminRegistration {

    public boolean registerAdmin(Admin admin, int currentAdminId) {

        Connection con = ConnectionDb.getConnection();

        try {

            // Start transaction
            con.setAutoCommit(false);

            // Step 1: Register new admin
            String insertQuery = "INSERT INTO admin "
                    + "(admin_name, username, password_hash, email, phone_number) "
                    + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(insertQuery);

            ps.setString(1, admin.getAdminName());
            ps.setString(2, admin.getUsername());
            ps.setString(3, admin.getPasswordHash());
            ps.setString(4, admin.getEmail());
            ps.setString(5, admin.getPhoneNumber());

            int result = ps.executeUpdate();
            ps.close();

            if (result > 0) {

                // Step 2: Deactivate old admin
                String updateQuery = "UPDATE admin "
                        + "SET account_status = 'INACTIVE' "
                        + "WHERE admin_id = ? AND account_status = 'ACTIVE'";

                PreparedStatement updatePs = con.prepareStatement(updateQuery);

                updatePs.setInt(1, currentAdminId);

                int updated = updatePs.executeUpdate();
                updatePs.close();

                if (updated == 1) {

                    con.commit();

                    System.out.println("New admin registered successfully!");
                    System.out.println("Previous admin account deactivated.");

                    return true;

                } else {

                    con.rollback();

                    System.out.println("Old admin account could not be deactivated.");
                    System.out.println("Registration cancelled.");

                    return false;
                }

            } else {

                con.rollback();

                System.out.println("Admin registration failed.");

                return false;
            }

        } catch (Exception e) {

            try {
                con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println("Registration error: " + e.getMessage());

            return false;

        } finally {

            try {
                con.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
