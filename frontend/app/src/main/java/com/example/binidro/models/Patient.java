package com.example.binidro.models;

import java.util.ArrayList;

public class Patient {
    private String id;
    private String name;
    private String disease;
    private String imageUrl;
    private ArrayList<Sensor> sensors;

    public Patient(String id, String name, String disease, String imageUrl, ArrayList<Sensor> sensors) {
        this.id = id;
        this.name = name;
        this.disease = disease;
        this.imageUrl = imageUrl;
        this.sensors = sensors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(ArrayList<Sensor> sensors) {
        this.sensors = sensors;
    }
}
