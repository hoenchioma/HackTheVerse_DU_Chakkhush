package com.example.binidro.models;

public class Ward {
    private String id;
    private Patient patients[];
    private HealthWorker healthWorkers[];

    public Ward(String id, Patient[] patients, HealthWorker[] healthWorkers) {
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

    public Patient[] getPatients() {
        return patients;
    }

    public void setPatients(Patient[] patients) {
        this.patients = patients;
    }

    public HealthWorker[] getHealthWorkers() {
        return healthWorkers;
    }

    public void setHealthWorkers(HealthWorker[] healthWorkers) {
        this.healthWorkers = healthWorkers;
    }
}
