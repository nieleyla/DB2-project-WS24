package de.hsesslingen.service;

import java.util.List;

import de.hsesslingen.dao.MarineAnimalDAO;
import de.hsesslingen.model.MarineAnimal;

public class MarineAnimalService {
    private MarineAnimalDAO marineAnimalDAO = new MarineAnimalDAO();

    public List<MarineAnimal> getAllMarineAnimals() {
        return marineAnimalDAO.getAllMarineAnimals();
    }

    public void addNewMarineAnimal(String species, String habitat, int size, String conservationStatus) {
        marineAnimalDAO.addMarineAnimal(species, habitat, size, conservationStatus);
        System.out.println("Marine animal added successfully: " + species);
    }
}
