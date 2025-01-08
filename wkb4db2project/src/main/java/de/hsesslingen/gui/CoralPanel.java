package de.hsesslingen.gui;

import de.hsesslingen.service.CoralService;

import javax.swing.*;
import java.awt.*;

public class CoralPanel extends JPanel {
    private CoralService coralService = new CoralService();

    public CoralPanel() {
        setLayout(new BorderLayout());

        // Tabelle für Corals
        String[] columns = {"ID", "Name", "Region", "Recovery Status"};
        JTable table = new JTable(new Object[][]{}, columns);
        JScrollPane tableScrollPane = new JScrollPane(table);

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

        // Add action listener
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String region = regionField.getText();
            String recoveryStatus = recoveryField.getText();

            coralService.addNewCoral(name, region, recoveryStatus);
            JOptionPane.showMessageDialog(this, "Coral added successfully!");
            nameField.setText("");
            regionField.setText("");
            recoveryField.setText("");
        });

        add(tableScrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
    }
}