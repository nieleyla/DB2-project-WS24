package de.hsesslingen.gui;

import de.hsesslingen.service.MarineAnimalService;

import javax.swing.*;
import java.awt.*;

public class MarineAnimalPanel extends JPanel {
    private MarineAnimalService marineAnimalService = new MarineAnimalService();

    public MarineAnimalPanel() {
        setLayout(new BorderLayout());

        // Tabelle für Marine Animals
        String[] columns = {"ID", "Species", "Habitat", "Size", "Conservation Status"};
        JTable table = new JTable(new Object[][]{}, columns);
        JScrollPane tableScrollPane = new JScrollPane(table);

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

        // Add action listener
        addButton.addActionListener(e -> {
            String species = speciesField.getText();
            String habitat = habitatField.getText();
            int size = Integer.parseInt(sizeField.getText());
            String conservationStatus = conservationField.getText();

            marineAnimalService.addNewMarineAnimal(species, habitat, size, conservationStatus);
            JOptionPane.showMessageDialog(this, "Marine animal added successfully!");
            speciesField.setText("");
            habitatField.setText("");
            sizeField.setText("");
            conservationField.setText("");
        });

        add(tableScrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
    }
}