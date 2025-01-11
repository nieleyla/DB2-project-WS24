package de.hsesslingen.service;

import java.util.List;

import de.hsesslingen.dao.CoralDAO;
import de.hsesslingen.model.Coral;

public class CoralService {
    private final CoralDAO coralDAO = new CoralDAO();

    // List all Corals
    public List<Coral> getAllCorals() {
        return coralDAO.getAllCorals();
    }

    // Add a new Coral
    public void addNewCoral(String name, String region, String recoveryStatus) {
        coralDAO.addCoral(name, region, recoveryStatus);
        System.out.println("Coral added successfully: " + name);
    }

    // Update a Coral
    public void updateCoral(int id, String name, String region, String recoveryStatus) {
        coralDAO.updateCoral(id, name, region, recoveryStatus);
        System.out.println("Coral updated successfully: " + name);
    }

    // Delete a Coral
    public void deleteCoral(int id) {
        coralDAO.deleteCoral(id);
        System.out.println("Coral deleted successfully: " + id);
    }
}
