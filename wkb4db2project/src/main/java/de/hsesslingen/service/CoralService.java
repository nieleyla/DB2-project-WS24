package de.hsesslingen.service;

import java.util.List;

import de.hsesslingen.dao.CoralDAO;
import de.hsesslingen.model.Coral;

public class CoralService {
    private CoralDAO coralDAO = new CoralDAO();

    public List<Coral> getAllCorals() {
        return coralDAO.getAllCorals();
    }

    public void addNewCoral(String name, String region, String recoveryStatus) {
        coralDAO.addCoral(name, region, recoveryStatus);
        System.out.println("Coral added successfully: " + name);
    }
}
