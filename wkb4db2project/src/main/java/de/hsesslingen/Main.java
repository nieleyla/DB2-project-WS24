package de.hsesslingen;

import java.time.LocalDateTime;
import java.util.Scanner;

import de.hsesslingen.connection.DatabaseConnection;
import de.hsesslingen.gui.MainGUI;
import de.hsesslingen.service.MarineAnimalService;

public class Main {

    public static void main(String[] args) {
        System.out.println("[INFO] Starting Marine Life Database Manager...");
        System.out.println("[INFO] Current time: " + LocalDateTime.now());

        // Test database connection
        try {
            DatabaseConnection.getConnection().close();
            System.out.println("[INFO] Database connection successful!");
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to connect to the database. Exiting...");
            e.printStackTrace();
            return;
        }

        // Start in console mode if the first argument is "console"
        if (args.length > 0 && args[0].equalsIgnoreCase("console")) {
            System.out.println("[INFO] Starting in console mode...");
            runConsoleMode();
        } else {
            System.out.println("[INFO] Starting GUI mode...");
            new MainGUI();
        }
    }

    // Interactive Console Mode
    private static void runConsoleMode() {
        MarineAnimalService animalService = new MarineAnimalService();
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Welcome to the Marine Database Console!");
        System.out.println("Type 'help' for a list of commands.");
        System.out.println("------------------------------------");

        while (true) {
            System.out.print(">> ");
            command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "help":
                    System.out.println("Available commands:");
                    System.out.println(" - list: List all marine animals");
                    System.out.println(" - add: Add a new marine animal");
                    System.out.println(" - import: Import marine animals from Wikipedia");
                    System.out.println(" - delete: Delete marine animal(s)");
                    System.out.println(" - help: Display this help message");
                    System.out.println(" - exit: Exit the console mode");
                    break;

                case "list":
                    System.out.println("[INFO] Listing all marine animals:");
                    animalService.getAllMarineAnimals().forEach(animal
                            -> System.out.println(animal.getId() + ": " + animal.getSpecies() + " in " + animal.getHabitat())
                    );
                    break;

                case "add":
                    try {
                        System.out.print("Enter species: ");
                        String species = scanner.nextLine();

                        System.out.print("Enter habitat: ");
                        String habitat = scanner.nextLine();

                        System.out.print("Enter size: ");
                        int size = Integer.parseInt(scanner.nextLine());

                        System.out.print("Enter conservation status: ");
                        String conservationStatus = scanner.nextLine();

                        animalService.addNewMarineAnimal(species, habitat, size, conservationStatus);
                        System.out.println("[INFO] Marine animal added successfully!");
                    } catch (Exception e) {
                        System.err.println("[ERROR] Failed to add marine animal. Please check your input.");
                    }
                    break;

                case "import":
                    System.out.println("[INFO] Importing marine animals from Wikipedia...");
                    animalService.importToDatabaseFromWikipedia();
                    break;

                case "delete":
                    try {
                        System.out.print("Enter ID of the marine animal or ID range (x-y) to delete: ");
                        String input = scanner.nextLine();
                        String[] parts = input.split("-");
                        if (parts.length == 1) {
                            int id = Integer.parseInt(parts[0]);
                            animalService.deleteMarineAnimal(id);
                            System.out.println("[INFO] Marine animal deleted successfully!");
                        } else if (parts.length == 2) {
                            int start = Integer.parseInt(parts[0]);
                            int end = Integer.parseInt(parts[1]);
                            for (int i = start; i <= end; i++) {
                                animalService.deleteMarineAnimal(i);
                            }
                            System.out.println("[INFO] Marine animals deleted successfully!");
                        } else {
                            System.err.println("[ERROR] Invalid input. Please enter a single ID or a range.");
                        }
                    } catch (Exception e) {
                        System.err.println("[ERROR] Failed to delete marine animal(s). Please check your input.");
                    }
                    break;

                case "exit":
                    System.out.println("[INFO] Exiting console mode. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("[ERROR] Unknown command. Type 'help' for a list of commands.");
            }
        }
    }
}
