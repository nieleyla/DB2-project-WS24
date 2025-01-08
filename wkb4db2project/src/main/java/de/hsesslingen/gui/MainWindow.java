package de.hsesslingen.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainWindow {
    public void createAndShowGUI() {
        JFrame frame = new JFrame("Marine World");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Welcome to the Marine World!");
        frame.add(label);

        frame.setVisible(true);
    }
}
