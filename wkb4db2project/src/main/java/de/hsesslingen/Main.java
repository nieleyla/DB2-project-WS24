package de.hsesslingen;

import de.hsesslingen.gui.MainWindow;
import de.hsesslingen.service.MarineAnimalService;

public class Main {
    public static void main(String[] args) {
        // Start GUI
        MainWindow mainWindow = new MainWindow();
        mainWindow.createAndShowGUI();

        // Example Service Usage
        MarineAnimalService marineAnimalService = new MarineAnimalService();
        marineAnimalService.printAllMarineAnimals();
    }
}
