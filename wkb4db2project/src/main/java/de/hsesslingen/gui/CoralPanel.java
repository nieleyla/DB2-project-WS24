package de.hsesslingen.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import de.hsesslingen.model.Coral;
import de.hsesslingen.service.CoralService;

public class CoralPanel extends JPanel {
    private CoralService coralService = new CoralService();
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
        JScrollPane tableScrollPane = new JScrollPane(table);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Load data
        loadCorals();

        // Search and form panel
        JPanel searchFormPanel = new JPanel(new BorderLayout());
        searchFormPanel.add(createSearchPanel(), BorderLayout.NORTH);
        searchFormPanel.add(createFormPanel(), BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);
        add(searchFormPanel, BorderLayout.SOUTH);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(new TitledBorder("Search Corals"));

        JLabel searchLabel = new JLabel("Search:");
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText().toLowerCase();
            List<Coral> filteredCorals = coralService.getAllCorals().stream()
                .filter(coral -> coral.getName().toLowerCase().contains(searchTerm) ||
                                 coral.getRegion().toLowerCase().contains(searchTerm) ||
                                 coral.getRecoveryStatus().toLowerCase().contains(searchTerm))
                .toList();

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

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        return searchPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new TitledBorder("Add New Coral"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(15);
        JLabel regionLabel = new JLabel("Region:");
        JTextField regionField = new JTextField(15);
        JLabel recoveryLabel = new JLabel("Recovery Status:");
        JTextField recoveryField = new JTextField(15);
        JButton addButton = new JButton("Add Coral");

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(regionLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(regionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(recoveryLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(recoveryField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(addButton, gbc);

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

        return formPanel;
    }

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
