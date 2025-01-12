package de.hsesslingen.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import de.hsesslingen.dao.MarineAnimalDAO;
import de.hsesslingen.model.MarineAnimal;

public class MarineAnimalService {

    private final MarineAnimalDAO marineAnimalDAO = new MarineAnimalDAO();
    private final WikipediaParseService wikipediaParseService = new WikipediaParseService();

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

    // Import Marine Animals from Wikipedia
    public void importToDatabaseFromWikipedia() {
        List<WikipediaParseService.MarineSpecies> speciesList = wikipediaParseService.parseSpeciesFromWikipedia();

        for (WikipediaParseService.MarineSpecies species : speciesList) {
            marineAnimalDAO.addMarineAnimal(
                    species.getName(),
                    "Unknown Habitat",
                    0,
                    species.getIucnStatus()
            );
            System.out.println("[INFO] Imported: " + species.getName());
        }

        System.out.println("[INFO] Imported " + speciesList.size() + " marine species into the database.");
    }

    // Import Marine Animals from Wikipedia with GUI preview
    public void importToDatabaseFromWikipediaWithPreview() {
        List<WikipediaParseService.MarineSpecies> speciesList = wikipediaParseService.parseSpeciesFromWikipedia();

        String preview = speciesList.stream()
                .map(species -> species.getName() + " (" + species.getScientificName() + ") - " + species.getIucnStatus())
                .collect(Collectors.joining("\n"));

        JTextArea textArea = new JTextArea(preview, 20, 50);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(
                null,
                "Import process might take a while and the program might be unresponsive. Please check the console output for progress.",
                "Import Notice",
                JOptionPane.INFORMATION_MESSAGE
        );

        int confirmation = JOptionPane.showConfirmDialog(
                null,
                scrollPane,
                "Import Preview",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation != JOptionPane.YES_OPTION) {
            System.out.println("[INFO] Import cancelled.");
            return;
        }

        for (WikipediaParseService.MarineSpecies species : speciesList) {
            marineAnimalDAO.addMarineAnimal(
                    species.getName(),
                    "Unknown Habitat",
                    0,
                    species.getIucnStatus()
            );
            System.out.println("[INFO] Imported: " + species.getName());
        }

        JOptionPane.showMessageDialog(null, "Import successful!", "Import Complete", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("[INFO] Imported " + speciesList.size() + " marine species into the database.");
    }
}
