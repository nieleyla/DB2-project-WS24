package de.hsesslingen.gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class MainGUI extends JFrame {
    public MainGUI() {
        setTitle("Marine Database Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
