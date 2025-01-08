package de.hsesslingen.service;

import java.util.List;

import de.hsesslingen.dao.MarineAnimalDAO;
import de.hsesslingen.model.MarineAnimal;

public class MarineAnimalService {
    private MarineAnimalDAO marineAnimalDAO = new MarineAnimalDAO();

    public void printAllMarineAnimals() {
        List<MarineAnimal> marineAnimals = marineAnimalDAO.getAllMarineAnimals();
        for (MarineAnimal animal : marineAnimals) {
            System.out.println("Species: " + animal.getSpecies() + ", Habitat: " + animal.getHabitat());
        }
    }
}
