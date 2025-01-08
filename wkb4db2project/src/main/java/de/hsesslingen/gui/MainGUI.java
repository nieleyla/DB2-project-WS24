package de.hsesslingen.gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MainGUI extends JFrame {
    public MainGUI() {
        setTitle("Marine Database Manager");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set Look and Feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Tabbed layout
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Marine Animals", new MarineAnimalPanel());
        tabbedPane.addTab("Corals", new CoralPanel());

        add(tabbedPane);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
