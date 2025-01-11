package de.hsesslingen.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Establishes a connection to the database
public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://134.108.190.89;databaseName=WKB4_DB2_Projekt;encrypt=true;trustServerCertificate=true";
    private static final String USER = "wkb4";
    private static final String PASSWORD = "wkb4";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
