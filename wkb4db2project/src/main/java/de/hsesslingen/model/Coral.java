package de.hsesslingen.model;

import java.time.LocalDateTime;

public class Coral {
    private int id;
    private String name;
    private String region;
    private String recoveryStatus;
    private LocalDateTime lastModified;

    public Coral(int id, String name, String region, String recoveryStatus, LocalDateTime lastModified) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.recoveryStatus = recoveryStatus;
        this.lastModified = lastModified;
    }

    // Getter and Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getRecoveryStatus() { return recoveryStatus; }
    public void setRecoveryStatus(String recoveryStatus) { this.recoveryStatus = recoveryStatus; }

    public LocalDateTime getLastModified() { return lastModified; }
    public void setLastModified(LocalDateTime lastModified) { this.lastModified = lastModified; }
}

