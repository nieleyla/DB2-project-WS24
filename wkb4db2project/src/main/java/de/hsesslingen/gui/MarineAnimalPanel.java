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
        JScrollPane tableScrollPane = new JScrollPane(table);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Load data
        loadMarineAnimals();

        // Search and form panel
        JPanel searchFormPanel = new JPanel(new BorderLayout());
        searchFormPanel.add(createSearchPanel(), BorderLayout.NORTH);
        searchFormPanel.add(createFormPanel(), BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);
        add(searchFormPanel, BorderLayout.SOUTH);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(new TitledBorder("Search Marine Animals"));

        JLabel searchLabel = new JLabel("Search:");
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");

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

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        return searchPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new TitledBorder("Add New Marine Animal"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel speciesLabel = new JLabel("Species:");
        JTextField speciesField = new JTextField(15);
        JLabel habitatLabel = new JLabel("Habitat:");
        JTextField habitatField = new JTextField(15);
        JLabel sizeLabel = new JLabel("Size:");
        JTextField sizeField = new JTextField(15);
        JLabel conservationLabel = new JLabel("Conservation Status:");
        JTextField conservationField = new JTextField(15);
        JButton addButton = new JButton("Add Marine Animal");

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(speciesLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(speciesField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(habitatLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(habitatField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(sizeLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(sizeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(conservationLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(conservationField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(addButton, gbc);

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

        return formPanel;
    }

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