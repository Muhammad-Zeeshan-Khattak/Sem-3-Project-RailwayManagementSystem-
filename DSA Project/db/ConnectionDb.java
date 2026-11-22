package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDb {

    private static ConnectionDb instance = null;
    private static Connection conn       = null;

    private String url      = "jdbc:mysql://localhost:3306/Railway";
    private String user     = "root";
    private String password = "mysql2004";

    private ConnectionDb() {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println(" Connected Successfully!");
        } catch (Exception e) {
            System.out.println(" Connection Failed!");
            e.printStackTrace();
        }
    }

    // Step 3 — single access point
    public static Connection getConnection() {
        if (instance == null) {
            instance = new ConnectionDb();   
        }
        return conn;
    }
}