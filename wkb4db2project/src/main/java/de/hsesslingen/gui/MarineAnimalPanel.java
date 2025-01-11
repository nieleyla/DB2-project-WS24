package de.hsesslingen.gui;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import de.hsesslingen.model.MarineAnimal;
import de.hsesslingen.service.MarineAnimalService;

public class MarineAnimalPanel extends JPanel {
    private MarineAnimalService marineAnimalService = new MarineAnimalService();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public MarineAnimalPanel() {
        setLayout(new BorderLayout());

        // Table section
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new TitledBorder("Marine Animals Database"));
        String[] columns = {"ID", "Species", "Habitat", "Size", "Conservation Status"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true); // Enable sorting
        JScrollPane tableScrollPane = new JScrollPane(table); // Add scroll bar
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Load data
        loadMarineAnimals();

        // Search and form panel
        JPanel searchFormPanel = new JPanel(new BorderLayout());
        searchFormPanel.add(createSearchPanel(), BorderLayout.NORTH);
        searchFormPanel.add(createFormPanel(), BorderLayout.CENTER);

        // Add panels to main panel
        add(tablePanel, BorderLayout.CENTER);
        add(searchFormPanel, BorderLayout.SOUTH);
    }

    // Search panel
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(new TitledBorder("Search Marine Animals"));

        // Search components
        JLabel searchLabel = new JLabel("Search:");
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");

        // Search button action
        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText().toLowerCase();
            List<MarineAnimal> filteredAnimals = marineAnimalService.getAllMarineAnimals().stream()
                .filter(animal -> animal.getSpecies().toLowerCase().contains(searchTerm) ||
                                 animal.getHabitat().toLowerCase().contains(searchTerm) ||
                                 animal.getConservationStatus().toLowerCase().contains(searchTerm))
                .toList();

            tableModel.setRowCount(0);
            for (MarineAnimal animal : filteredAnimals) {
                tableModel.addRow(new Object[]{
                    animal.getId(),
                    animal.getSpecies(),
                    animal.getHabitat(),
                    animal.getSize(),
                    animal.getConservationStatus()
                });
            }
        });

        // Add components to search panel
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        return searchPanel;
    }

    // Form panel
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new TitledBorder("Add New Marine Animal"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Form components
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(10);
        JLabel speciesLabel = new JLabel("Species:");
        JTextField speciesField = new JTextField(15);
        JLabel habitatLabel = new JLabel("Habitat:");
        JTextField habitatField = new JTextField(15);
        JLabel sizeLabel = new JLabel("Size:");
        JTextField sizeField = new JTextField(15);
        JLabel conservationLabel = new JLabel("Conservation Status:");
        JTextField conservationField = new JTextField(15);
        
        // Buttons
        JButton addButton = new JButton("Add Marine Animal");
        JButton updateButton = new JButton("Update Marine Animal");
        JButton deleteButton = new JButton("Delete Marine Animal");

        // Layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(speciesLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(speciesField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(habitatLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(habitatField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(sizeLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(sizeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(conservationLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(conservationField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(addButton, gbc);
        gbc.gridy = 6;
        formPanel.add(updateButton, gbc);
        gbc.gridy = 7;
        formPanel.add(deleteButton, gbc);

        // Add button action
        addButton.addActionListener(e -> {
            String species = speciesField.getText();
            String habitat = habitatField.getText();
            int size;
            try {
                size = Integer.parseInt(sizeField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Size must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String conservationStatus = conservationField.getText();

            marineAnimalService.addNewMarineAnimal(species, habitat, size, conservationStatus);

            loadMarineAnimals();

            speciesField.setText("");
            habitatField.setText("");
            sizeField.setText("");
            conservationField.setText("");
        });

        // Update Button Action
        updateButton.addActionListener(e -> {
            int id;
            try {
                id = Integer.parseInt(idField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String species = speciesField.getText();
            String habitat = habitatField.getText();
            int size;
            try {
                size = Integer.parseInt(sizeField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Size must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String conservationStatus = conservationField.getText();

            marineAnimalService.updateMarineAnimal(id, species, habitat, size, conservationStatus);

            loadMarineAnimals();

            idField.setText("");
            speciesField.setText("");
            habitatField.setText("");
            sizeField.setText("");
            conservationField.setText("");
        });

        // Delete Button Action
        deleteButton.addActionListener(e -> {
            int id;
            try {
                id = Integer.parseInt(idField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            marineAnimalService.deleteMarineAnimal(id);

            loadMarineAnimals();

            idField.setText("");
        });

        return formPanel;
    }

    // Load marine animals
    private void loadMarineAnimals() {
        List<MarineAnimal> marineAnimals = marineAnimalService.getAllMarineAnimals();
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