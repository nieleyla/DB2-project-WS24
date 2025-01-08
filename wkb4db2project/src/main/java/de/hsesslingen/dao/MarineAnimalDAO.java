package de.hsesslingen.dao;

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
             ResultSet rs = stmt.executeQuery("SELECT * FROM MarineAnimals")) {
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
}
