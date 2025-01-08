package de.hsesslingen.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.hsesslingen.connection.DatabaseConnection;
import de.hsesslingen.model.Coral;

public class CoralDAO {

    // List all Corals
    public List<Coral> getAllCorals() {
        List<Coral> corals = new ArrayList<>();
        String query = "SELECT * FROM dbo.leniit01_Corals";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                corals.add(new Coral(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("Region"),
                        rs.getString("RecoveryStatus"),
                        rs.getTimestamp("LastModified").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return corals;
    }

    // Add a new Coral
    public void addCoral(String name, String region, String recoveryStatus) {
        String query = "INSERT INTO dbo.leniit01_Corals (Name, Region, RecoveryStatus) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, region);
            pstmt.setString(3, recoveryStatus);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

