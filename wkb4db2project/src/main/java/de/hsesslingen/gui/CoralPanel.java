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

import de.hsesslingen.model.Coral;
import de.hsesslingen.service.CoralService;
import de.hsesslingen.service.WikipediaPreviewService;

public class CoralPanel extends JPanel {

    private final CoralService coralService = new CoralService();
    private final WikipediaPreviewService wikipediaPreviewService = new WikipediaPreviewService();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public CoralPanel() {
        setLayout(new BorderLayout());

        // Table section
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new TitledBorder("Coral Database"));
        String[] columns = {"ID", "Name", "Region", "Recovery Status"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true); // Enable sorting
        JScrollPane tableScrollPane = new JScrollPane(table); // Add scroll bar
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Wikipedia button
        JButton wikipediaButton = new JButton("Open Wikipedia Article");
        wikipediaButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String name = table.getValueAt(selectedRow, 1).toString(); // Spalte für 'Name'
                String articleUrl = wikipediaPreviewService.getWikipediaArticle(name);

                if (articleUrl != null) {
                    try {
                        java.awt.Desktop.getDesktop().browse(new java.net.URI(articleUrl));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Could not open Wikipedia article.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No Wikipedia article found for: " + name, "Not Found", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a coral first.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Button panel directly below the table
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(wikipediaButton);
        tablePanel.add(buttonPanel, BorderLayout.SOUTH);

        // Load data
        loadCorals();

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
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Layout: Left align
        searchPanel.setBorder(new TitledBorder("Search Corals"));

        JLabel searchLabel = new JLabel("Search:");
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");

        // Search button action
        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText().toLowerCase(); // Case insensitive search
            // Filter corals based on search term
            List<Coral> filteredCorals = coralService.getAllCorals().stream()
                    .filter(coral -> coral.getName().toLowerCase().contains(searchTerm)
                    || coral.getRegion().toLowerCase().contains(searchTerm)
                    || coral.getRecoveryStatus().toLowerCase().contains(searchTerm))
                    .toList();

            // Update table
            tableModel.setRowCount(0);
            for (Coral coral : filteredCorals) {
                tableModel.addRow(new Object[]{
                    coral.getId(),
                    coral.getName(),
                    coral.getRegion(),
                    coral.getRecoveryStatus()
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
        JPanel formPanel = new JPanel(new GridBagLayout()); // Layout: Grid
        formPanel.setBorder(new TitledBorder("Manage Corals"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Form fields
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(10);
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(15);
        JLabel regionLabel = new JLabel("Region:");
        JTextField regionField = new JTextField(15);
        JLabel recoveryLabel = new JLabel("Recovery Status:");
        JTextField recoveryField = new JTextField(15);

        // Buttons
        JButton addButton = new JButton("Add Coral");
        JButton updateButton = new JButton("Update Coral");
        JButton deleteButton = new JButton("Delete Coral");

        // Layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(regionLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(regionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(recoveryLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(recoveryField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(addButton, gbc);
        gbc.gridy = 5;
        formPanel.add(updateButton, gbc);
        gbc.gridy = 6;
        formPanel.add(deleteButton, gbc);

        // Add button action
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String region = regionField.getText();
            String recoveryStatus = recoveryField.getText();

            coralService.addNewCoral(name, region, recoveryStatus);

            loadCorals();

            nameField.setText("");
            regionField.setText("");
            recoveryField.setText("");
        });

        // Update Button Action
        updateButton.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String region = regionField.getText();
            String recoveryStatus = recoveryField.getText();

            coralService.updateCoral(id, name, region, recoveryStatus);

            loadCorals();

            idField.setText("");
            nameField.setText("");
            regionField.setText("");
            recoveryField.setText("");
        });

        // Delete Button Action
        deleteButton.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());

            coralService.deleteCoral(id);

            loadCorals();

            idField.setText("");
        });

        return formPanel;
    }

    // Load all corals
    private void loadCorals() {
        List<Coral> corals = coralService.getAllCorals();
        tableModel.setRowCount(0);
        for (Coral coral : corals) {
            tableModel.addRow(new Object[]{
                coral.getId(),
                coral.getName(),
                coral.getRegion(),
                coral.getRecoveryStatus()
            });
        }
    }
}
