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

import de.hsesslingen.model.Coral;
import de.hsesslingen.service.CoralService;

public class CoralPanel extends JPanel {
    private CoralService coralService = new CoralService();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public CoralPanel() {
        setLayout(new BorderLayout());

        // Tabelle erstellen
        String[] columns = {"ID", "Name", "Region", "Recovery Status"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Daten aus der DB laden
        loadCorals();

        // Formular für neue Einträge
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField nameField = new JTextField();
        JTextField regionField = new JTextField();
        JTextField recoveryField = new JTextField();
        JButton addButton = new JButton("Add Coral");

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Region:"));
        formPanel.add(regionField);
        formPanel.add(new JLabel("Recovery Status:"));
        formPanel.add(recoveryField);
        formPanel.add(new JLabel(""));
        formPanel.add(addButton);

        // Hinzufügen-Button Aktion
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String region = regionField.getText();
            String recoveryStatus = recoveryField.getText();

            // Neuen Eintrag in die DB einfügen
            coralService.addNewCoral(name, region, recoveryStatus);

            // Tabelle aktualisieren
            loadCorals();

            // Felder leeren
            nameField.setText("");
            regionField.setText("");
            recoveryField.setText("");
        });

        add(tableScrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
    }

    private void loadCorals() {
        // Daten aus der DB laden
        List<Coral> corals = coralService.getAllCorals();

        // Tabelle leeren und neu befüllen
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