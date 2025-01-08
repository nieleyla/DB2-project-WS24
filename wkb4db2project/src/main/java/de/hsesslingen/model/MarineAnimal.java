package de.hsesslingen.model;

public class MarineAnimal {
    private int id;
    private String species;
    private String habitat;
    private int size;
    private String conservationStatus;

    public MarineAnimal(int id, String species, String habitat, int size, String conservationStatus) {
        this.id = id;
        this.species = species;
        this.habitat = habitat;
        this.size = size;
        this.conservationStatus = conservationStatus;
    }

    // Getter and Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public String getHabitat() { return habitat; }
    public void setHabitat(String habitat) { this.habitat = habitat; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public String getConservationStatus() { return conservationStatus; }
    public void setConservationStatus(String conservationStatus) { this.conservationStatus = conservationStatus; }
}

