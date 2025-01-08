package de.hsesslingen;

import java.util.List;

import de.hsesslingen.dao.CoralDAO;
import de.hsesslingen.model.Coral;

public class Main {
    public static void main(String[] args) {
        CoralDAO coralDAO = new CoralDAO();
    
        // Beispiel: Neue Koralle hinzufügen
        coralDAO.addCoral("Lettuce Coral", "Pacific Ocean", "Endangered");
    
        // Beispiel: Alle Korallen abrufen
        List<Coral> corals = coralDAO.getAllCorals();
        for (Coral coral : corals) {
            System.out.println("Name: " + coral.getName() + ", Region: " + coral.getRegion());
        }
    }
}
