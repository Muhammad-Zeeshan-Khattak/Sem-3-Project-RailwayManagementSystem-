
package admin;

import db.ConnectionDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminAuthentication {

    public Admin login(String username, String password) {

        Connection con = ConnectionDb.getConnection();

        String query = "SELECT * FROM admin WHERE username = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    String storedPassword = rs.getString("password_hash");
                    String status = rs.getString("account_status");

                    if (!status.equals("ACTIVE")) {
                        System.out.println("Your account is inactive.");
                        return null;
                    }

                    if (password.equals(storedPassword)) {

                        Admin admin = new Admin(
                            rs.getInt("admin_id"),
                            rs.getString("admin_name"),
                            rs.getString("username"),
                            storedPassword,
                            rs.getString("email"),
                            rs.getString("phone_number"),
                            status
                        );

                        System.out.println("Login successful!");
                        return admin;

                    } else {
                        System.out.println("Invalid username or password.");
                        return null;
                    }

                } else {
                    System.out.println("Invalid username or password.");
                    return null;
                }
            }

        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            return null;
        }
    }
}
