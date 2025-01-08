package de.hsesslingen.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.hsesslingen.connection.DatabaseConnection;
import de.hsesslingen.model.MarineAnimal;

public class MarineAnimalDAO {

    // List all Marine Animals
    public List<MarineAnimal> getAllMarineAnimals() {
        List<MarineAnimal> marineAnimals = new ArrayList<>();
        String query = "SELECT * FROM dbo.leniit01_MarineAnimals";

        try (Connection connection = DatabaseConnection.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {
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

   // Add a new MarineAnimal
   public void addMarineAnimal(String species, String habitat, int size, String conservationStatus) {
    String query = "INSERT INTO dbo.leniit01_MarineAnimals (Species, Habitat, Size, ConservationStatus) VALUES (?, ?, ?, ?)";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement pstmt = connection.prepareStatement(query)) {
        pstmt.setString(1, species); // Insert species into Species column
        pstmt.setString(2, habitat); // Insert habitat into Habitat column
        pstmt.setInt(3, size); // Insert size into Size column
        pstmt.setString(4, conservationStatus); // Insert conservationStatus into ConservationStatus column
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
  }
}
