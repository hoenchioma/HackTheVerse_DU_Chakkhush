package com.example.binidro.models;

import java.util.ArrayList;

public class Ward {
    private String id;
    private ArrayList<Patient> patients;
    private ArrayList<HealthWorker> healthWorkers;

    public Ward(String id, ArrayList<Patient> patients, ArrayList<HealthWorker> healthWorkers) {
        this.id = id;
        this.patients = patients;
        this.healthWorkers = healthWorkers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }

    public ArrayList<HealthWorker> getHealthWorkers() {
        return healthWorkers;
    }

    public void setHealthWorkers(ArrayList<HealthWorker> healthWorkers) {
        this.healthWorkers = healthWorkers;
    }
}
