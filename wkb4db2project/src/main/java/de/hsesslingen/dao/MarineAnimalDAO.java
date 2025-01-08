package de.hsesslingen.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.hsesslingen.connection.DatabaseConnection;
import de.hsesslingen.model.MarineAnimal;

public class MarineAnimalDAO {
    public List<MarineAnimal> getAllMarineAnimals() {
        List<MarineAnimal> marineAnimals = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM leniit01_MarineAnimals")) {
            while (rs.next()) {
                marineAnimals.add(new MarineAnimal(
                    rs.getInt("ID"),
                    rs.getString("Species"),
                    rs.getString("Habitat"),
                    rs.getInt("Size"),
                    rs.getString("ConservationStatus")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marineAnimals;
    }

    public void addMarineAnimal(String species, String habitat, int size, String conservationStatus) {
        try (Connection connection = DatabaseConnection.getConnection();
            CallableStatement stmt = connection.prepareCall("{call leniit01_AddMarineAnimal(?, ?, ?, ?)}")) {
            stmt.setString(1, species);
            stmt.setString(2, habitat);
            stmt.setInt(3, size);
            stmt.setString(4, conservationStatus);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}