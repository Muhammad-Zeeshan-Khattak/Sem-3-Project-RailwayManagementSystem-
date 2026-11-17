
package admin;

import db.ConnectionDb;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AdminRegistration {

    public void registerAdmin(Admin admin) {

        Connection con = ConnectionDb.getConnection();

        try {

            String query = "INSERT INTO admin "
                    + "(admin_name, username, password_hash, email, phone_number) "
                    + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, admin.getAdminName());
            ps.setString(2, admin.getUsername());
            ps.setString(3, admin.getPasswordHash());
            ps.setString(4, admin.getEmail());
            ps.setString(5, admin.getPhoneNumber());

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Admin registered successfully!");
            } else {
                System.out.println("Admin registration failed.");
            }

            ps.close();

        } catch (Exception e) {
            System.out.println("Registration error: " + e.getMessage());
        }
    }
}
