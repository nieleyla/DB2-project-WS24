package de.hsesslingen.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.hsesslingen.connection.DatabaseConnection;
import de.hsesslingen.model.Coral;

public class CoralDAO {
    public List<Coral> getAllCorals() {
        List<Coral> corals = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Corals")) {
            while (rs.next()) {
                corals.add(new Coral(
                    rs.getInt("ID"),
                    rs.getString("Name"),
                    rs.getString("Region"),
                    rs.getString("RecoveryStatus")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return corals;
    }
}
