package de.hsesslingen.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import de.hsesslingen.model.MarineAnimal;
import de.hsesslingen.service.MarineAnimalService;

public class MarineAnimalPanel extends JPanel {
    private MarineAnimalService marineAnimalService = new MarineAnimalService();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public MarineAnimalPanel() {
        setLayout(new BorderLayout());

        // Tabelle erstellen
        String[] columns = {"ID", "Species", "Habitat", "Size", "Conservation Status"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Daten aus der DB laden
        loadMarineAnimals();

        // Formular für neue Einträge
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField speciesField = new JTextField();
        JTextField habitatField = new JTextField();
        JTextField sizeField = new JTextField();
        JTextField conservationField = new JTextField();
        JButton addButton = new JButton("Add Marine Animal");

        formPanel.add(new JLabel("Species:"));
        formPanel.add(speciesField);
        formPanel.add(new JLabel("Habitat:"));
        formPanel.add(habitatField);
        formPanel.add(new JLabel("Size:"));
        formPanel.add(sizeField);
        formPanel.add(new JLabel("Conservation Status:"));
        formPanel.add(conservationField);
        formPanel.add(new JLabel(""));
        formPanel.add(addButton);

        // Hinzufügen-Button Aktion
        addButton.addActionListener(e -> {
            String species = speciesField.getText();
            String habitat = habitatField.getText();
            int size = Integer.parseInt(sizeField.getText());
            String conservationStatus = conservationField.getText();

            // Neuen Eintrag in die DB einfügen
            marineAnimalService.addNewMarineAnimal(species, habitat, size, conservationStatus);

            // Tabelle aktualisieren
            loadMarineAnimals();

            // Felder leeren
            speciesField.setText("");
            habitatField.setText("");
            sizeField.setText("");
            conservationField.setText("");
        });

        add(tableScrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
    }

    private void loadMarineAnimals() {
        // Daten aus der DB laden
        List<MarineAnimal> marineAnimals = marineAnimalService.getAllMarineAnimals();

        // Debug: Print data to console
        System.out.println("Marine Animals Retrieved: " + marineAnimals.size());
        for (MarineAnimal animal : marineAnimals) {
            System.out.println("ID: " + animal.getId() + 
                ", Species: " + animal.getSpecies() +
                ", Habitat: " + animal.getHabitat() +
                ", Size: " + animal.getSize() +
                ", Conservation Status: " + animal.getConservationStatus());
        }   

        // Tabelle leeren und neu befüllen
        tableModel.setRowCount(0);
        for (MarineAnimal animal : marineAnimals) {
            tableModel.addRow(new Object[]{
                    animal.getId(),
                    animal.getSpecies(),
                    animal.getHabitat(),
                    animal.getSize(),
                    animal.getConservationStatus()
            });
        }
    }
}
