package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// This class is responsible for establishing connection with the database
public class DatabaseConnection {

    // Database connection parameters
    private static final String URL = "jdbc:sqlserver://NITRO5\\MSSQLSERVERDEV;databaseName=UserInfo;integratedSecurity=false;encrypt=false;trustServerCertificate=true;";
    private static final String USER = "JavaDBConnection";
    private static final String PASSWORD = "root";

    // Method to establish and return database connection
    public static Connection getConnection() {
        Connection connection = null;

        try {
            // Loading the SQL Server JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Establishing connection with the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            // Handling exceptions
            ex.printStackTrace();
        }

        return connection;
    }
}
