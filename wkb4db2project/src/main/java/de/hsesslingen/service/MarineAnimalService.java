package de.hsesslingen.service;

import java.util.List;

import de.hsesslingen.dao.MarineAnimalDAO;
import de.hsesslingen.model.MarineAnimal;

public class MarineAnimalService {
    private final MarineAnimalDAO marineAnimalDAO = new MarineAnimalDAO();

    // List all Marine Animals
    public List<MarineAnimal> getAllMarineAnimals() {
        return marineAnimalDAO.getAllMarineAnimals();
    }

    // Add a new MarineAnimal
    public void addNewMarineAnimal(String species, String habitat, int size, String conservationStatus) {
        marineAnimalDAO.addMarineAnimal(species, habitat, size, conservationStatus);
        System.out.println("Marine animal added successfully: " + species);
    }

    // Update a MarineAnimal
    public void updateMarineAnimal(int id, String species, String habitat, int size, String conservationStatus) {
        marineAnimalDAO.updateMarineAnimal(id, species, habitat, size, conservationStatus);
        System.out.println("Marine animal updated successfully: " + species);
    }

    // Delete a MarineAnimal
    public void deleteMarineAnimal(int id) {
        marineAnimalDAO.deleteMarineAnimal(id);
        System.out.println("Marine animal deleted successfully: " + id);
    }
}
