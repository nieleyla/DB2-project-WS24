package de.hsesslingen.model;

public class Coral {
    private int id;
    private String name;
    private String region;
    private String recoveryStatus;

    public Coral(int id, String name, String region, String recoveryStatus) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.recoveryStatus = recoveryStatus;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getRecoveryStatus() { return recoveryStatus; }
    public void setRecoveryStatus(String recoveryStatus) { this.recoveryStatus = recoveryStatus; }
}
